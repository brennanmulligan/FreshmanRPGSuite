package model;

import communication.messages.AdventureStateChangeMessage;
import datatypes.AdventureStateEnum;

/**
 * @author sl6469, Cody
 *
 */
public class CommandAdventureStateChange extends Command
{

	private int adventureID, questID;
	private String adventureDescription;
	private AdventureStateEnum adventureState;
	private String witnessTitle;
	private boolean realLifeAdventure;

	/**
	 * @param message that AdventureStateChangeMessage
	 */
	public CommandAdventureStateChange(AdventureStateChangeMessage message)
	{
		this.questID = message.getQuestID();
		this.adventureID = message.getAdventureID();
		this.adventureDescription = message.getAdventureDescription();
		this.adventureState = message.getNewState();
		this.realLifeAdventure = message.isRealLifeAdventure();
		this.witnessTitle = message.getWitnessTitle();
	}

	@Override
	boolean execute()
	{
		ClientPlayerManager.getSingleton().getThisClientsPlayer().sendAdventureStateChangeReport(questID, adventureID, adventureDescription, adventureState);
		return true;
	}

	/**
	 * @return the quest ID
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return the adventureID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the adventureDescription
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return the adventureState
	 */
	public AdventureStateEnum getAdventureState()
	{
		return adventureState;
	}

	/**
	 * @return true if the player must complete this adventure outside of the game
	 */
	public boolean isRealLifeAdventure()
	{
		return realLifeAdventure;
	}

	/**
	 * @return the person who can witness completion if this is a real life adventure
	 */
	public String getWitnessTitle()
	{
		return witnessTitle;
	}


}
