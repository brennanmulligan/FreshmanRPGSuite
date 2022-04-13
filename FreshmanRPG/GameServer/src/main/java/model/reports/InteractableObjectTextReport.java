package model.reports;

import model.QualifiedObservableReport;

/**
 *
 * @author Stephen Clabaugh, Jacob Knight
 * Report to send to client with text to display
 *
 */
public class InteractableObjectTextReport implements QualifiedObservableReport
{
	private final int playerID;
	private final String text;

	/**
	 * Constructor
	 * @param playerID - int
	 * @param text - String
	 */
	public InteractableObjectTextReport(int playerID, String text)
	{
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
