package communication.messages;

import java.io.Serializable;

/**
 * A message that the client has disconnected
 *
 * @author Nick?
 *
 */
public class DisconnectMessage implements Message, Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int playerID;

	/**
	 * @param playerID the player ID we should use to connect
	 */
	public DisconnectMessage(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Disconnect Message: playerID = " + playerID;
	}
}
