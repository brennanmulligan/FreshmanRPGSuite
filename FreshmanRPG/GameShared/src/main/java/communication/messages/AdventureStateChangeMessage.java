package communication.messages;

import java.io.Serializable;

import datatypes.AdventureStateEnum;

/**
 * A message from an area server to a client telling the client to notify the
 * player that they has fulfilled an adventure
 *
 * @author Ryan
 *
 */
public class AdventureStateChangeMessage implements Message, Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int playerID;
	private int questID;
	private int adventureID;
	private String adventureDescription;
	private AdventureStateEnum newState;
	private boolean realLifeAdventure;
	private String witnessTitle;

	/**
	 * @param playerID the current player's id
	 * @param questID the quest id
	 * @param adventureID the id of the adventure
	 * @param adventureDescription the description of the adventure
	 * @param newState the new state the adventure will be in
	 * @param realLifeAdventure true if the player must complete this adventure
	 *            in real life
	 * @param witnessTitle if this is a real life adventure, the title of the
	 *            person who can witness completion
	 */
	public AdventureStateChangeMessage(int playerID, int questID, int adventureID, String adventureDescription,
									   AdventureStateEnum newState, boolean realLifeAdventure, String witnessTitle)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.newState = newState;
		this.realLifeAdventure = realLifeAdventure;
		this.witnessTitle = witnessTitle;
	}

	/**
	 * @return the adventure's descrption
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return the adventure's ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the state the quest has moved to
	 */
	public AdventureStateEnum getNewState()
	{
		return newState;
	}

	/**
	 * Get the player's ID
	 *
	 * @return playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Get the quest's ID
	 *
	 * @return questID
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return true if this adventure must be completed in real life
	 */
	public boolean isRealLifeAdventure()
	{
		return realLifeAdventure;
	}

	/**
	 * @return the title of the person who can witness completion if this is a
	 *         real life adventure
	 */
	public String getWitnessTitle()
	{
		return witnessTitle;
	}
}
