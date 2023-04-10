package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.TimerDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.TimerTableDataGateway;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class TimerManager
{
    private static TimerManager singleton;

    private final HashMap<Integer, ArrayList<ScheduledCommand>> timers;

    private  TimerManager()
    {
        timers = new HashMap<>();
    }

    public synchronized static TimerManager getSingleton()
    {
        if (singleton == null)
        {
            singleton = new TimerManager();
        }
        return singleton;
    }

    public static void resetSingleton()
    {
        if (singleton != null)
        {
            singleton.timers.clear();
            singleton = null;
        }
    }

    public void loadUserTimers(int playerID) throws DatabaseException
    {
        for (TimerDTO currentTimer : TimerTableDataGateway.getAllPlayerTimers(playerID))
        {
            this.scheduleCommand(currentTimer.getEndsAt(),
                    currentTimer.getCommand(),
                    currentTimer.getPlayerID(),
                    false);
        }
    }

    protected void scheduleCommand(Date endsAt, Command command,
                                   Integer playerID, boolean shouldPersist)
            throws DatabaseException
    {
        if (shouldPersist)
        {
            TimerTableDataGateway.createRow(endsAt, command, playerID);
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                ModelFacade.getSingleton().queueCommand(command);
                TimerManager.getSingleton().removeTimer(playerID);
                timer.cancel();
                //delete from DB
                if (playerID != null)
                {
                    TimerTableDataGateway.deleteExpiredTimers(playerID);
                }
            }
        };
        timer.schedule(task, endsAt);
        this.addTimer(playerID, new ScheduledCommand(endsAt, command));
    }

    public void scheduleCommand(Date endsAt, Command command, Integer playerID)
            throws DatabaseException
    {
        boolean shouldPersist = false;
        TimerDTO check = TimerTableDataGateway.getPlayerTimer(playerID, endsAt);
        if (check == null)
        {
            shouldPersist = true;
        }
        this.scheduleCommand(endsAt, command, playerID, shouldPersist);
    }

    public void scheduleCommand(Date endsAt, Command command)
            throws DatabaseException
    {
        this.scheduleCommand(endsAt, command, null, false);
    }

    private HashMap<Integer, ArrayList<ScheduledCommand>> getTimers()
    {
        return timers;
    }

    public int getNumCurrentTimers(int playerID)
    {
        if (timers.get(playerID) != null)
        {
            return timers.get(playerID).size();
        }
        return 0;
    }

    public int getNumPlayers()
    {
        return timers.size();
    }
    private void addTimer(Integer playerID, ScheduledCommand scheduledCommand)
    {
        ArrayList<ScheduledCommand> commandList;
        if (!timers.containsKey(playerID))
        {
            commandList = new ArrayList<>();
            timers.put(playerID, commandList);
        }
        else
        {
            commandList = timers.get(playerID);
        }
        commandList.add(scheduledCommand);
    }

    private void removeTimer(Integer playerID)
    {
        if (playerID != null)
        {
            timers.remove(playerID);
        }
    }
}


class ScheduledCommand
{
    private Date endsAt;

    private Command command;

    public ScheduledCommand(Date endsAt, Command command)
    {
        this.endsAt = endsAt;
        this.command = command;
    }

    public Date getEndsAt()
    {
        return endsAt;
    }

    public Command getCommand()
    {
        return command;
    }

}
