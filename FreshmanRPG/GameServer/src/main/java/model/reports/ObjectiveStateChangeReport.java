package model.reports;

import datatypes.ObjectiveStateEnum;
import model.QualifiedObservableReport;

import java.util.Objects;

/**
 * Report to the client that an objective state change had occured.
 *
 * @author nk3668
 *
 */
public final class ObjectiveStateChangeReport implements QualifiedObservableReport
{
	private final int playerID;
	private final int questID;
	private final int objectiveID;
	private final String objectiveDescription;
	private final ObjectiveStateEnum newState;
	private final boolean realLifeObjective;
	private final String witnessTitle;

	/**
	 * @param id players ID
	 * @param questID id of the quest
	 * @param objectiveID objectives ID to change state
	 * @param objectiveDescription description of objective
	 * @param newState new state to be changed to
	 * @param realLifeObjective true if the player must complete this objective
	 *            outside of the game
	 * @param witnessTitle the title of the person who can witness completion if
	 *            this is a real life objective
	 */
	public ObjectiveStateChangeReport(int id, int questID, int objectiveID, String objectiveDescription,
									  ObjectiveStateEnum newState, boolean realLifeObjective, String witnessTitle)
	{
		this.playerID = id;
		this.questID = questID;
		this.objectiveID = objectiveID;
		this.objectiveDescription = objectiveDescription;
		this.newState = newState;
		this.realLifeObjective = realLifeObjective;
		this.witnessTitle = witnessTitle;
	}

	/**
	 * @return player ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return objective ID
	 */
	public int getObjectiveID()
	{
		return objectiveID;
	}

	/**
	 * @return objective Description
	 */
	public String getObjectiveDescription()
	{
		return objectiveDescription;
	}

	/**
	 * @return new State
	 */
	public ObjectiveStateEnum getNewState()
	{
		return newState;
	}

	/**
	 * @return quest id
	 */
	public int getQuestID()
	{
		return questID;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		ObjectiveStateChangeReport that = (ObjectiveStateChangeReport) o;
		return playerID == that.playerID && questID == that.questID && objectiveID == that.objectiveID &&
				realLifeObjective == that.realLifeObjective &&
				Objects.equals(objectiveDescription, that.objectiveDescription) && newState == that.newState &&
				Objects.equals(witnessTitle, that.witnessTitle);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(playerID, questID, objectiveID, objectiveDescription, newState, realLifeObjective, witnessTitle);
	}

	/**
	 * @return true if the player must complete this objective in real life
	 */
	public boolean isRealLifeObjective()
	{
		return realLifeObjective;
	}

	/**
	 * @return if the player must complete this objective in real life, the
	 *         title of the person who can witness completion
	 */
	public String getWitnessTitle()
	{
		return witnessTitle;
	}

}
