package edu.ship.engr.shipsim.model.reports;

/**
 * @author Stephen Clabaugh, Jacob Knight
 * Report to send to client with text to display
 */
public class InteractableObjectTextReport extends SendMessageReport
{
    private final int playerID;
    private final String text;

    /**
     * Constructor
     *
     * @param playerID - int
     * @param text     - String
     */
    public InteractableObjectTextReport(int playerID, String text)
    {
        super(playerID, true);
        this.playerID = playerID;
        this.text = text;
    }

    /**
     * @return playerID - int
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return text - String
     */
    public String getText()
    {
        return text;
    }
}
