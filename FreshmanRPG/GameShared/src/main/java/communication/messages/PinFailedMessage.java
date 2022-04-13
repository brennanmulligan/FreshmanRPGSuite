package communication.messages;

import java.io.Serializable;

/**
 * The message that goes from the main server to the client when the user has a
 * pin error
 *
 * Matt and Andy
 */
public class PinFailedMessage implements Message, Serializable
{

	private int playerID;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param playerID the unique ID of the player who tried to connect with an
	 *            invalid pin
	 */
	public PinFailedMessage(int playerID)
	{
		this.playerID = playerID;
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
		PinFailedMessage other = (PinFailedMessage) obj;
		if (playerID != other.playerID)
		{
			return false;
		}
		return true;
	}

	/**
	 * @return the unique ID of the player whose pin was invalid
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + playerID;
		return result;
	}

	/**
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Pin failed message for player #" + playerID;
	}

}
