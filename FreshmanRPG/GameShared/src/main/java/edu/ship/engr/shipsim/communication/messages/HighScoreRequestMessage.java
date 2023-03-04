package edu.ship.engr.shipsim.communication.messages;

/**
 * This message requests that we send the high scores to the player making the
 * request
 *
 * @author Merlin
 */
public class HighScoreRequestMessage extends Message
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public HighScoreRequestMessage(int playerID, boolean quietMessage)
    {
        super(playerID, quietMessage);
    }
}
