package model;

import java.util.Date;

import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datasource.DatabaseException;
import datatypes.Position;

/**
 * Command to update a quest.
 */
public class CommandEditQuest extends Command
{
	private int questID;
	private String questTitle;
	private String questDescription;
	private String triggerMapName;
	private Position triggerPosition;
	private int experiencePointsGained;
	private int adventuresForFulfillment;
	private QuestCompletionActionType completionActionType;
	private QuestCompletionActionParameter completionActionParameter;
	private Date startDate;
	private Date endDate;

	/**
	 * Construct and initialize a CommandEditQuest.
	 *
	 * @param questID - quest ID
	 * @param questTitle - title
	 * @param questDescription - description
	 * @param triggerMapName - map 
	 * @param triggerPosition - position on map
	 * @param experiencePointsGained - XP gained from completing quest
	 * @param adventuresForFulfillment - how many adventures needed for fulfillment
	 * @param completionActionType - completion type
	 * @param completionActionParameter - completion parameter
	 * @param startDate - start date
	 * @param endDate - end date
	 */
	public CommandEditQuest(int questID, String questTitle, String questDescription, String triggerMapName, Position triggerPosition, int experiencePointsGained,
							int adventuresForFulfillment, QuestCompletionActionType completionActionType,
							QuestCompletionActionParameter completionActionParameter, Date startDate, Date endDate)
	{
		this.questID = questID;
		this.questTitle = questTitle;
		this.questDescription = questDescription;
		this.triggerMapName = triggerMapName;
		this.triggerPosition = triggerPosition;
		this.experiencePointsGained = experiencePointsGained;
		this.adventuresForFulfillment = adventuresForFulfillment;
		this.completionActionType = completionActionType;
		this.completionActionParameter = completionActionParameter;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Execute the command.
	 *
	 * @return true if successful
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerQuestManager.getInstance().editQuest(
					questID,
					questTitle,
					questDescription,
					triggerMapName,
					triggerPosition,
					experiencePointsGained,
					adventuresForFulfillment,
					completionActionType,
					completionActionParameter,
					startDate,
					endDate
			);
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
