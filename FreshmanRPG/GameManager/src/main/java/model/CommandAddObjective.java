package model;

import criteria.ObjectiveCompletionCriteria;
import dataENUM.ObjectiveCompletionType;
import datasource.DatabaseException;

/**
 * Command used to add an objective.
 */
public class CommandAddObjective extends Command
{
	private int questId;
	private String objectiveDescription;
	private int experiencePointsGained;
	private ObjectiveCompletionType objectiveCompletionType;
	private ObjectiveCompletionCriteria objectiveCompletionCriteria;

	/**
	 * Construct and initialize a CommandAddObjective.
	 *
	 * @param questId - quest ID this objective belongs to
	 * @param objectiveDescription - objective description
	 * @param experiencePointsGained - experience points gained by completing objective
	 * @param objectiveCompletionType - objective completion type
	 * @param objectiveCompletionCriteria - objective completion criteria
	 */
	public CommandAddObjective(int questId, String objectiveDescription,
							   int experiencePointsGained, ObjectiveCompletionType objectiveCompletionType,
							   ObjectiveCompletionCriteria objectiveCompletionCriteria)
	{
		this.questId = questId;
		this.objectiveDescription = objectiveDescription;
		this.experiencePointsGained = experiencePointsGained;
		this.objectiveCompletionType = objectiveCompletionType;
		this.objectiveCompletionCriteria = objectiveCompletionCriteria;
	}

	/**
	 * Execute command.
	 *
	 * @return true if successful
	 */
	@Override
	boolean execute()
	{
		try
		{
			final boolean success = GameManagerQuestManager.getInstance().addObjective(questId,
					objectiveDescription, experiencePointsGained,
                    objectiveCompletionType, objectiveCompletionCriteria);
			return success;
		}
		catch (DatabaseException e)
		{
			return false;
		}
	}

}
