package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.RestfulLoginInitiatedReport;

import java.io.IOException;

/**
 * @author Derek
 */
public final class CommandRestfulLogin extends Command
{
    private final String username;
    private final String password;

    public CommandRestfulLogin(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    @Override
    boolean execute() throws IOException
    {
        RestfulLoginInitiatedReport report = new RestfulLoginInitiatedReport(username, password);

        ReportObserverConnector.getSingleton().sendReport(report);

        return true;
    }
}
