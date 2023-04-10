package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.model.Command;

import java.util.Date;

public final class TimerDTO
{
    private int playerID;
    private Date endsAt;
    private Command command;

    public TimerDTO(Date endsAt, Command command, int playerID)
    {
        this.playerID = playerID;
        this.endsAt = endsAt;
        this.command = command;
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
