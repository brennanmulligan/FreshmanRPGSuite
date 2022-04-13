package communication.messages;

import java.io.Serializable;

/**
 *
 * @author Ryan
 *
 */
public class AdventureNotificationCompleteMessage implements Message, Serializable
{

	private int playerID;
	private int questID;
	private int adventureID;

	/**
	 * @param playerID id of the player
	 * @param questID id of the quest
	 * @param adventureID id of the adventure
	 */
	public AdventureNotificationCompleteMessage(int playerID, int questID, int adventureID)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return id of the player
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return id of the quest
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return id of the adventure
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

}
