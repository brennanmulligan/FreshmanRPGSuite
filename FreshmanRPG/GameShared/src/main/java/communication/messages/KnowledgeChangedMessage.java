package communication.messages;

import java.io.Serializable;

/**
 * @author Matthew Croft
 * @author Evan Stevenson
 *
 */
public class KnowledgeChangedMessage implements Message, Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int knowledgePoints;

	private int playerID;

	private int buffPool;


	/**
	 * @param playerID of the current player
	 * @param knowledgePoints the player has
	 * @param level the player is
	 * @param playerID of the current player
	 * @param buffPool - the player's current bonuspoint pool
	 */
	public KnowledgeChangedMessage(int playerID, int knowledgePoints, int buffPool)
	{
		this.knowledgePoints = knowledgePoints;
		this.playerID = playerID;
		this.buffPool = buffPool;
	}

	/**
	 * @return knowledgePoints
	 */
	public int getKnowledgePoints()
	{
		return knowledgePoints;
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
