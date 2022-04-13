package dataDTO;

import datatypes.AdventureStateEnum;

/**
 * A data transfer record that contains the state of one adventure for one
 * player
 *
 * @author Merlin
 *
 */
public class AdventureStateRecordDTO
{

	private int playerID;
	private int questID;
	private int adventureID;
	private AdventureStateEnum state;
	private boolean needingNotification;

	/**
	 * @param playerID the player
	 * @param questID the quest that contains the adventure
	 * @param adventureID the adventure
	 * @param state the player's state for that adventure
	 * @param needingNotification true if the player should be notified about
	 *            this adventure state
	 */
	public AdventureStateRecordDTO(int playerID, int questID, int adventureID, AdventureStateEnum state,
								   boolean needingNotification)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
		this.state = state;
		this.needingNotification = needingNotification;
	}

	/**
	 * @return the adventure ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the player's ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the quest ID
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return the state
	 */
	public AdventureStateEnum getState()
	{
		return state;
	}

	/**
	 * @return true if the player has not been notified that this adventure is
	 *         in this state
	 */
	public boolean isNeedingNotification()
	{
		return needingNotification;
	}

	/**
	 * @param newState the state this adventure should have
	 */
	public void setState(AdventureStateEnum newState)
	{
		this.state = newState;
	}

	/**
	 * Remember whether the player needs to be notified about the state we are
	 * in
	 *
	 * @param b true if we should notify the player
	 */
	public void setNeedingNotification(boolean b)
	{
		this.needingNotification = b;
	}

}
