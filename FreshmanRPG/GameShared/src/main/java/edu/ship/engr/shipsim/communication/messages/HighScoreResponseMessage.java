package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;

import java.util.ArrayList;

/**
 * Contains the top ten high scores in order
 *
 * @author Merlin
 */
public class HighScoreResponseMessage extends Message
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<PlayerScoreRecord> scoreReports;

    /**
     * @param scoreReports the top ten players (sorted)
     */
    public HighScoreResponseMessage(ArrayList<PlayerScoreRecord> scoreReports, boolean quietMessage)
    {
        super(0, quietMessage);
        this.scoreReports = scoreReports;
    }

    /**
     * @return the top ten players
     */
    public ArrayList<PlayerScoreRecord> getScoreReports()
    {
        return scoreReports;
    }

}
