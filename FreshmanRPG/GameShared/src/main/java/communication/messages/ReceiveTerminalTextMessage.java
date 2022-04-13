package communication.messages;

import java.io.Serializable;
import java.util.ArrayList;

import dataDTO.PlayerMapLocationDTO;

/**
 *
 * @author Chris Roadcap
 * @author Denny Fleagle
 *
 */
public class ReceiveTerminalTextMessage implements Message, Serializable
{
	private String resultText;
	private static final long serialVersionUID = 1L;
	private int requestingPlayerID;

	/**
	 * Constructor
	 *
	 * @param resultText
	 *            The text to send back to the client
	 * @param requestingPlayerID
	 *            the id of the player requesting the information
	 */
	public ReceiveTerminalTextMessage(int requestingPlayerID, String resultText)
	{
		this.requestingPlayerID = requestingPlayerID;
		this.resultText = resultText;
	}

	/**
	 * Gets the list of players/maps Strings
	 *
	 * @return the list of players/maps Strings
	 */
	public String getResultText()
	{
		return resultText;
	}

	/**
	 * get the player id of the requesting player
	 *
	 * @return requesting player id
	 */
	public int getRequestingPlayerID()
	{
		return requestingPlayerID;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + requestingPlayerID;
		result = prime * result + ((resultText == null) ? 0 : resultText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		ReceiveTerminalTextMessage other = (ReceiveTerminalTextMessage) obj;
		if (requestingPlayerID != other.requestingPlayerID)
		{
			return false;
		}
		if (resultText == null)
		{
			if (other.resultText != null)
			{
				return false;
			}
		}
		else if (!resultText.equals(other.resultText))
		{
			return false;
		}
		return true;
	}

}
