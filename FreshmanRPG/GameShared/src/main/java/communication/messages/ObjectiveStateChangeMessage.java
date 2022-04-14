package communication.messages;

import java.io.Serializable;

import datatypes.ObjectiveStateEnum;

/**
 * A message from an area server to a client telling the client to notify the
 * player that they has fulfilled an objective
 *
 * @author Ryan
 *
 */
public class ObjectiveStateChangeMessage implements Message, Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int playerID;
	private int questID;
	private int objectiveID;
	private String objectiveDescription;
	private ObjectiveStateEnum newState;
	private boolean realLifeObjective;
	private String witnessTitle;

	/**
	 * @param playerID the current player's id
	 * @param questID the quest id
	 * @param objectiveID the id of the objective
	 * @param objectiveDescription the description of the objective
	 * @param newState the new state the objective will be in
	 * @param realLifeObjective true if the player must complete this objective
	 *            in real life
	 * @param witnessTitle if this is a real life objective, the title of the
	 *            person who can witness completion
	 */
	public ObjectiveStateChangeMessage(int playerID, int questID, int objectiveID, String objectiveDescription,
									   ObjectiveStateEnum newState, boolean realLifeObjective, String witnessTitle)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.objectiveID = objectiveID;
		this.objectiveDescription = objectiveDescription;
		this.newState = newState;
		this.realLifeObjective = realLifeObjective;
		this.witnessTitle = witnessTitle;
	}

	/**
	 * @return the objective's descrption
	 */
	public String getObjectiveDescription()
	{
		return objectiveDescription;
	}

	/**
	 * @return the objective's ID
	 */
	public int getObjectiveID()
	{
		return objectiveID;
	}

	/**
	 * @return the state the quest has moved to
	 */
	public ObjectiveStateEnum getNewState()
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
	 * @return true if this objective must be completed in real life
	 */
	public boolean isRealLifeObjective()
	{
		return realLifeObjective;
	}

	/**
	 * @return the title of the person who can witness completion if this is a
	 *         real life objective
	 */
	public String getWitnessTitle()
	{
		return witnessTitle;
	}
}
