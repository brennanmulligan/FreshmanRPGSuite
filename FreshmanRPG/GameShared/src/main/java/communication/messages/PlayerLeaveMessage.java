package communication.messages;

import java.io.Serializable;

/**
 * Sent to all clients when a player disconnects from an area server
 *
 * @author nhydock
 *
 */
public class PlayerLeaveMessage implements Message, Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int playerID;

	/**
	 * @param playerID the unique ID of the player
	 */
	public PlayerLeaveMessage(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * get this player's unique ID
	 *
	 * @return the player's ID
	 */
	public int getPlayerID()
	{
		return this.playerID;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "PlayerLeaveMessage: playerID = " + playerID;
	}
}
