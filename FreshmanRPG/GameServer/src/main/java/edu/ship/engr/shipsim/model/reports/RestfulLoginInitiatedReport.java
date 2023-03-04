package edu.ship.engr.shipsim.model.reports;

/**
 * @author Derek
 */
public final class RestfulLoginInitiatedReport extends SendMessageReport
{
    private final String username;
    private final String password;

    public RestfulLoginInitiatedReport(String username, String password)
    {
        super(0, true);
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
