package model;

import datasource.*;

/**
 * @author Merlin
 */
public class PlayerLogin
{

    private final PlayerLoginRowDataGateway loginGateway;

    /**
     * Create a new record in the database
     *
     * @param name     the player's name
     * @param password the player's password
     * @param id       The id of the player
     * @throws DatabaseException if the gateway fails
     */
    protected PlayerLogin(int playerID, String name, String password, int id)
            throws DatabaseException
    {
        try
        {
            this.loginGateway =
                    new PlayerLoginRowDataGateway(playerID, name, password);
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("no login information for " + name);
        }

    }

    /**
     * Create an object if the name and password are found in the db
     *
     * @param playerName the player's name
     * @param password   the player's password
     * @throws DatabaseException if the name/password combination isn't found in
     *                           the db
     */
    protected PlayerLogin(String playerName, String password) throws DatabaseException
    {
        try
        {
            this.loginGateway = new PlayerLoginRowDataGateway(playerName);
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("No login information for " + playerName + ".");
        }

        if (!loginGateway.checkPassword(password))
        {
            throw new DatabaseException("Incorrect password.");
        }


//        if (playerGateway.getOnline())
//        {
//
//            throw new DatabaseException("Player is already online.");
//        }

    }

    /**
     * Get a player's login information without checking his password
     *
     * @param playerID the player's unique ID
     * @throws DatabaseException if the player doesn't exist
     */
    protected PlayerLogin(int playerID) throws DatabaseException
    {
        try
        {
            this.loginGateway = new PlayerLoginRowDataGateway(playerID);
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException(
                    "no login information for player with ID " + playerID);
        }
    }

    /**
     * Get a player's login information without checking his password
     *
     * @param playerName the player's player name
     * @throws DatabaseException if the player doesn't exist
     */
    protected PlayerLogin(String playerName) throws DatabaseException
    {
        try
        {
            this.loginGateway = new PlayerLoginRowDataGateway(playerName);
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("no login information for " + playerName);
        }
    }

    /**
     * @param playerName The Name of the Player
     * @param password   The Password of the Player
     * @throws DatabaseException - shouldn't
     */
    public void editPlayeLogin(String playerName, String password)
            throws DatabaseException
    {
        this.loginGateway.setName(playerName);
        this.loginGateway.setPassword(password);
        this.loginGateway.persist();
    }

    /**
     * Return this player's unique ID
     *
     * @return the id
     */
    protected int getPlayerID()
    {
        return loginGateway.getPlayerID();
    }

    /**
     * Get the player's playername
     *
     * @return the playername
     */
    protected String getPlayerName()
    {
        return loginGateway.getPlayerName();
    }

    /**
     * Get the player's password
     *
     * @return the password
     */
    protected String getPlayerPassword()
    {
        return loginGateway.getPassword();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "PlayerLogin [name=" + loginGateway.getPlayerName() + "]";
    }

    /**
     * Compares a given object with the playername and player id.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj.getClass() != PlayerLogin.class)
        {
            return false;
        }
        PlayerLogin login = (PlayerLogin) obj;

        return login.getPlayerName().equals(this.getPlayerName()) &&
                login.getPlayerID() == this.getPlayerID();
    }
}
