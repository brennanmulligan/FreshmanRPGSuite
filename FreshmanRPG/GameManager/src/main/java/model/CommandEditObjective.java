package model;

import criteria.ObjectiveCompletionCriteria;
import dataENUM.ObjectiveCompletionType;
import datasource.DatabaseException;

/**
 * @author Darin Alleman and Darnell Martin
 * Command to Edit an Objective
 */
public class CommandEditObjective extends Command
{
	private int questId;
	private int objectiveId;
	private String objectiveDescription;
	private ObjectiveCompletionType objectiveCompletionType;
	private ObjectiveCompletionCriteria objectiveCompletionCriteria;
	private int experiencePointsGained;

	/**
	 * Constructor for command
	 * @param questID - quest ID of the objective to edit
	 * @param objectiveID - objective ID of the objective to edit
	 * @param objectiveDescription - description of the objective
	 * @param experienceGained - exp gained of the objective when completed
	 * @param completionType - completion type of the objective
	 * @param completionCriteria - completion criteria of the objective
	 */
	public CommandEditObjective(int questID, int objectiveID, String objectiveDescription, int experienceGained,
								ObjectiveCompletionType completionType, ObjectiveCompletionCriteria completionCriteria)
	{
		this.questId = questID;
		this.objectiveId = objectiveID;
		this.objectiveDescription = objectiveDescription;
		this.experiencePointsGained = experienceGained;
		this.objectiveCompletionType = completionType;
		this.objectiveCompletionCriteria = completionCriteria;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerQuestManager.getInstance().editObjective(questId, objectiveId, objectiveDescription, experiencePointsGained, objectiveCompletionType, objectiveCompletionCriteria);
			return true;
		}
		catch (DatabaseException e)
		{
			return false;
		}
	}

}
