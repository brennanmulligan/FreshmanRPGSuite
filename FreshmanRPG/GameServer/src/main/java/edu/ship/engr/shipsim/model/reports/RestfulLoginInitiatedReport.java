package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.QualifiedObservableReport;

/**
 * @author Derek
 */
public final class RestfulLoginInitiatedReport implements QualifiedObservableReport
{
    private final String username;
    private final String password;

    public RestfulLoginInitiatedReport(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
