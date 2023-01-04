package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * Sent by InteractObjectManager
 *
 * @author ed9737
 */
public class InteractionDeniedReport implements Report
{

    private final int playerID;

    /**
     * @param playerID the unique ID of the player who tried to interact with object
     */
    public InteractionDeniedReport(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * @return the ID of the player involved
     */
    public int getPlayerID()
    {
        return playerID;
    }
}
