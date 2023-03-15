package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.PlayerLoginRowDataGateway;

/**
 * Command that takes a player's ID and the new
 * password and changes the password in the database.
 */
public class CommandChangePassword extends Command
{
    private final String playerName;
    private final String newPassword;

    /**
     *
     * @param newPassword the new password overwriting old one
     */
    public CommandChangePassword(String playerName, String newPassword)
    {
        this.playerName = playerName;
        this.newPassword = newPassword;
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
                    PlayerLoginRowDataGateway(playerName);
            gw.setPassword(newPassword);
            gw.persist();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }
}
