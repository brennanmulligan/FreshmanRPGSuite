package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.reports.RestfulLogoutFailedReport;
import edu.ship.engr.shipsim.model.reports.RestfulLogoutSuccessReport;
import edu.ship.engr.shipsim.restfulcommunication.RestfulPlayerManager;

/**
 * @author Derek
 */
public final class CommandRestfulLogout extends Command
{
    private final String authKey;

    public CommandRestfulLogout(String authKey)
    {
        this.authKey = authKey;
    }

    @Override
    void execute()
    {
        try
        {
            // Remove the player matching the given auth key from the player manager
            RestfulPlayerManager.getSingleton().removePlayer(authKey);

            RestfulLogoutSuccessReport report = new RestfulLogoutSuccessReport();

            // Send a report saying that we logged out successfully
            ReportObserverConnector.getSingleton().sendReport(report);
        }
        catch (DatabaseException e)
        {
            RestfulLogoutFailedReport report = new RestfulLogoutFailedReport(e.getMessage());

            // Send a report saying that something went wrong while logging out
            ReportObserverConnector.getSingleton().sendReport(report);
        }
    }
}
