package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * Report if a Buff has run out.
 *
 * @author Aaron Gerber
 * @author Stephen Clabaugh
 */
public class NoMoreBuffReport implements Report
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
