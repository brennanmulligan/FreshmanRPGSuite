package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.QualifiedObservableReport;

/**
 * @author Derek
 */
public final class RestfulLoginSuccessReport implements QualifiedObservableReport
{
    private final int playerID;

    public RestfulLoginSuccessReport(int playerID)
    {
        this.playerID = playerID;
    }

    public int getPlayerID()
    {
        return playerID;
    }
}
