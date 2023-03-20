package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.PlayerLoginRowDataGateway;
import edu.ship.engr.shipsim.model.reports.ChangePlayerReport;

/**
 * Command that takes a player's ID and the new
 * password and changes the password in the database.
 */
public class CommandChangePlayer extends Command
{
    private final String username;
    private final String password;

    /**
     *
     * @param username the username of the player
     * @param password the new password overwriting old one
     */
    public CommandChangePlayer(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    /**
     * Uses PlayerLoginRowDataGateway to set the new password
     * and then change the database
     */
    @Override
    void execute()
    {
        try
        {
            PlayerLoginRowDataGateway gw = new
                    PlayerLoginRowDataGateway(username);
            gw.setPassword(password);
            gw.persist();

        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        ReportObserverConnector.getSingleton().sendReport(new ChangePlayerReport(true));
    }
}