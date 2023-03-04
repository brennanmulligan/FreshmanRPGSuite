package edu.ship.engr.shipsim.model.reports;

/**
 * Sent by InteractObjectManager
 *
 * @author ed9737
 */
public class InteractionDeniedReport extends SendMessageReport
{

    private final int playerID;

    /**
     * @param playerID the unique ID of the player who tried to interact with object
     */
    public InteractionDeniedReport(int playerID)
    {
        super(playerID, true);
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
