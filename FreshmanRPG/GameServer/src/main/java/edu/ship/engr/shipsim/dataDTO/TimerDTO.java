package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.model.Command;

import java.util.Date;

public final class TimerDTO
{
    private int timerID;
    private int playerID;

    private Date endsAt;

    private Command command;

    public TimerDTO(int timerID, Date endsAt, Command command)
    {
        this.timerID = timerID;
        this.endsAt = endsAt;
        this.command = command;
    }

    public TimerDTO(int timerID, Date endsAt, Command command, int playerID)
    {
        this(timerID, endsAt, command);
        this.playerID = playerID;
    }


    public int getTimerID()
    {
        return timerID;
    }

    public int getPlayerID()
    {
        return playerID;
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
