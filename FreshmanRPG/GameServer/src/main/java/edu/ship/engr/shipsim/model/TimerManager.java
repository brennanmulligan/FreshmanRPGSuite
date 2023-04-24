package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.TimerDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.TimerTableDataGateway;
import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Travis Ritter, Evan Paules, Seth Miller
 * Class that Manages Timers in the game and allows the scheduling of certain commands.
 */
public class TimerManager
{
    private static TimerManager singleton;

    private final HashMap<Integer, ArrayList<ScheduledCommand>> timers;

    /**
     * Private constructor for singleton
     */
    private TimerManager()
    {
        timers = new HashMap<>();
    }

    /**
     * Singleton method
     * @return a new instance if it is null, else we return the already created instance.
     */
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

    /**
     * Loads all user timers for a particular player from the DB.
     * @param playerID of the player we want to load from.
     * @throws DatabaseException should not
     */
    public void loadUserTimers(int playerID) throws DatabaseException
    {
        for (TimerDTO currentTimer : TimerTableDataGateway.getAllPlayerTimers(playerID))
        {
            this.scheduleCommand(currentTimer.getEndsAt(),
                    currentTimer.getCommand(),
                    currentTimer.getPlayerID());
        }
    }

    public void persistPlayerTimers(int playerID) throws DatabaseException
    {
        if (timers.containsKey(playerID))
        {
            for (ScheduledCommand currentCommand : timers.get(playerID))
            {
                TimerTableDataGateway.createRow(currentCommand.getEndsAt(),
                        currentCommand.getCommand(), playerID);
            }
        }
    }

    /**
     * The method that actually schedules a command
     * @param endsAt the time the timer will run the command.
     * @param command the command to be ran after the timer runs out
     * @param playerID of the player we are attaching it to.
     */
    public void scheduleCommand(Date endsAt, Command command,
                                   Integer playerID)
    {
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

        if (playerID != null)
        {
            this.addTimer(endsAt, command, playerID);
        }

    }

    /**
     * Schedules a general command, and does not persist.
     * @param endsAt when the timer should run the task
     * @param command the command to be run at the end of the timer
     */
    public void scheduleCommand(Date endsAt, Command command)
    {
        this.scheduleCommand(endsAt, command, null);
    }

    /**
     * Gets the number of scheduled commands for a given player
     * @param playerID of the player to check
     * @return 0, if there are none, the # of scheduledcommands if there are some.
     */
    public int getNumCurrentTimers(int playerID)
    {
        if (timers.get(playerID) != null)
        {
            return timers.get(playerID).size();
        }
        return 0;
    }

    /**
     * Gets the number of entries in the hashmap
     * @return the number of entries in the hashmap
     */
    public int getNumPlayers()
    {
        return timers.size();
    }

    /**
     * Adds a new entry to hashmap if there is not one already. If there is one already, we add the scheduled command
     * to that player's array list
     * @param playerID of the player to add
     */
    protected void addTimer(Date endsAt, Command command, int playerID)
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
        commandList.add(new ScheduledCommand(endsAt, command));
    }

    /**
     * Removes the timer from the Hashmap
     * @param playerID the player's timers to remove
     */
    private void removeTimer(Integer playerID)
    {
        if (playerID != null)
        {
            timers.remove(playerID);
        }
    }
}

/**
 * Class that holds a Date and a Command for the hash map
 */
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
