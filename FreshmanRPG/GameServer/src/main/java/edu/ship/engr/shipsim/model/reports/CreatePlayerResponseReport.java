package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.CommandCreatePlayer;
import edu.ship.engr.shipsim.model.Report;

public class CreatePlayerResponseReport implements Report
{
    private final CommandCreatePlayer.CreatePlayerResponseType responseType;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        CreatePlayerResponseReport that = (CreatePlayerResponseReport) o;

        return responseType == that.responseType;
    }

    @Override
    public int hashCode()
    {
        return responseType != null ? responseType.hashCode() : 0;
    }

    public CreatePlayerResponseReport(CommandCreatePlayer.CreatePlayerResponseType success)
    {
        this.responseType = success;
    }

    public CommandCreatePlayer.CreatePlayerResponseType getResponse()
    {
        return responseType;
    }
}
