package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

import java.util.Objects;

/**
 * @author Derek
 */
public class RestfulLoginSuccessfulReport implements Report
{
    private final int playerID;

    public RestfulLoginSuccessfulReport(int playerID)
    {
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

        RestfulLoginSuccessfulReport otherReport = (RestfulLoginSuccessfulReport) o;

        return Objects.equals(playerID, otherReport.getPlayerID());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerID);
    }
}
