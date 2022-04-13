package communication.messages;

import java.io.Serializable;

/**
 * @author sk5587 & ed9737 contains text that we want to send to client
 */
public class DisplayTextMessage implements Message, Serializable
{
	private static final long serialVersionUID = 1L;
	private int playerID;
	private String text;

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + playerID;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		DisplayTextMessage other = (DisplayTextMessage) obj;
		if (playerID != other.playerID)
		{
			return false;
		}
		if (text == null)
		{
			if (other.text != null)
			{
				return false;
			}
		}
		else if (!text.equals(other.text))
		{
			return false;
		}
		return true;
	}

	/**
	 * @param playerID player id of the player we want to send this message to
	 * @param text text we want to send to the player
	 */
	public DisplayTextMessage(int playerID, String text)
	{
		this.playerID = playerID;
		this.text = text;
	}

	/**
	 * Get the player's id
	 *
	 * @return player id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Gets the text
	 *
	 * @return text
	 */
	public String getText()
	{
		return text;
	}
}
