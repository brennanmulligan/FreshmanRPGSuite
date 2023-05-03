package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.RestfulLoginInitiatedReport;

/**
 * @author Derek
 */
public final class CommandRestfulLogin extends Command
{
    private final String playerName;
    private final String password;

    public CommandRestfulLogin(String playerName, String password)
    {
        this.playerName = playerName;
        this.password = password;
    }

    @Override
    void execute()
    {
        RestfulLoginInitiatedReport report = new RestfulLoginInitiatedReport(
                playerName, password);
        ReportObserverConnector.getSingleton().sendReport(report);
    }
}
