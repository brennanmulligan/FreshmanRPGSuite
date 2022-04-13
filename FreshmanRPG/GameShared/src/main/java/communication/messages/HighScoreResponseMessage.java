package communication.messages;

import java.util.ArrayList;

import datatypes.PlayerScoreRecord;

/**
 * Contains the top ten high scores in order
 *
 * @author Merlin
 *
 */
public class HighScoreResponseMessage implements Message
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<PlayerScoreRecord> scoreReports;

	/**
	 * @param scoreReports the top ten players (sorted)
	 */
	public HighScoreResponseMessage(ArrayList<PlayerScoreRecord> scoreReports)
	{
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
