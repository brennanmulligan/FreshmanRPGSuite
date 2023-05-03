package edu.ship.engr.shipsim.model.reports;

/**
 * @author Derek
 */
public final class RestfulLoginInitiatedReport extends SendMessageReport
{
    private final String playerName;
    private final String password;

    public RestfulLoginInitiatedReport(String playerName, String password)
    {
        super(0, true);
        this.playerName = playerName;
        this.password = password;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public String getPassword()
    {
        return password;
    }
}
