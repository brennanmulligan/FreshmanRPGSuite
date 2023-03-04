package edu.ship.engr.shipsim.model.reports;

import java.util.Objects;

/**
 * @author Derek
 */
public class RestfulLoginServerSuccessfulReport extends SendMessageReport
{
    private final int playerID;

    public RestfulLoginServerSuccessfulReport(int playerID)
    {
        super(playerID, true);
        this.playerID = playerID;
    }

    public int getPlayerID()
    {
        return playerID;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null)
        {
            return false;
        }

        if (getClass() != o.getClass())
        {
            return false;
        }

        RestfulLoginServerSuccessfulReport
                otherReport = (RestfulLoginServerSuccessfulReport) o;

        return Objects.equals(playerID, otherReport.getPlayerID());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerID);
    }
}
