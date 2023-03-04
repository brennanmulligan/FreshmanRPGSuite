package edu.ship.engr.shipsim.model.reports;

/**
 * Report if a Buff has run out.
 *
 * @author Aaron Gerber
 * @author Stephen Clabaugh
 */
public class NoMoreBuffReport extends SendMessageReport
{
    /**
     * The player's id
     */
    private final int playerID;

    /**
     * Constructor
     *
     * @param playerID - the id of the player
     */
    public NoMoreBuffReport(int playerID)
    {
        super(playerID, true);
        this.playerID = playerID;
    }

    /**
     * Instance variable getter
     *
     * @return playerID
     */
    public int getPlayerID()
    {
        return playerID;
    }
}
