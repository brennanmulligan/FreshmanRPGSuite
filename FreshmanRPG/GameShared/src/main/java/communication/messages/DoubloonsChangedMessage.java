package communication.messages;

import java.io.Serializable;

/**
 * @author Matthew Croft
 * @author Evan Stevenson
 *
 */
public class DoubloonsChangedMessage implements Message, Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int doubloons;

	private int playerID;

	private int buffPool;


	/**
	 * @param playerID of the current player
	 * @param doubloons the player has
	 * @param level the player is
	 * @param playerID of the current player
	 * @param buffPool - the player's current bonuspoint pool
	 */
	public DoubloonsChangedMessage(int playerID, int doubloons, int buffPool)
	{
		this.doubloons = doubloons;
		this.playerID = playerID;
		this.buffPool = buffPool;
	}

	/**
	 * @return doubloons
	 */
	public int getDoubloons()
	{
		return doubloons;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the buffPool
	 */
	public int getBuffPool()
	{
		return buffPool;
	}
}
