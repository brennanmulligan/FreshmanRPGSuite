package model;

import communication.messages.ObjectiveStateChangeMessage;
import datatypes.ObjectiveStateEnum;

/**
 * @author sl6469, Cody
 *
 */
public class CommandObjectiveStateChange extends Command
{

	private int objectiveID, questID;
	private String objectiveDescription;
	private ObjectiveStateEnum objectiveState;
	private String witnessTitle;
	private boolean realLifeObjective;

	/**
	 * @param message that ObjectiveStateChangeMessage
	 */
	public CommandObjectiveStateChange(ObjectiveStateChangeMessage message)
	{
		this.questID = message.getQuestID();
		this.objectiveID = message.getObjectiveID();
		this.objectiveDescription = message.getObjectiveDescription();
		this.objectiveState = message.getNewState();
		this.realLifeObjective = message.isRealLifeObjective();
		this.witnessTitle = message.getWitnessTitle();
	}

	@Override
	boolean execute()
	{
		ClientPlayerManager.getSingleton().getThisClientsPlayer().sendObjectiveStateChangeReport(questID, objectiveID, objectiveDescription, objectiveState);
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
	 * @return the objectiveID
	 */
	public int getObjectiveID()
	{
		return objectiveID;
	}

	/**
	 * @return the objectiveDescription
	 */
	public String getObjectiveDescription()
	{
		return objectiveDescription;
	}

	/**
	 * @return the objectiveState
	 */
	public ObjectiveStateEnum getObjectiveState()
	{
		return objectiveState;
	}

	/**
	 * @return true if the player must complete this objective outside of the game
	 */
	public boolean isRealLifeObjective()
	{
		return realLifeObjective;
	}

	/**
	 * @return the person who can witness completion if this is a real life objective
	 */
	public String getWitnessTitle()
	{
		return witnessTitle;
	}


}
