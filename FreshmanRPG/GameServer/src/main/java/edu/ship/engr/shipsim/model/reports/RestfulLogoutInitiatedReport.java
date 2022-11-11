package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.QualifiedObservableReport;

/**
 * @author Derek
 */
public final class RestfulLogoutInitiatedReport implements QualifiedObservableReport
{
    private final Long playerID;

    public RestfulLogoutInitiatedReport(Long playerID)
    {
        this.playerID = playerID;
    }

    public Long getPlayerID()
    {
        return playerID;
    }
}
