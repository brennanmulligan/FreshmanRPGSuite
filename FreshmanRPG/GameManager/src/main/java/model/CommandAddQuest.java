package model;

import java.util.Date;

import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datasource.DatabaseException;
import datatypes.Position;

/**
 * Command to add a quest
 * @author Caleb Bushman & Darnell Martin
 *
 */
public class CommandAddQuest extends Command
{

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
	 * Construct the command to add a quest
	 * @param questTitle - title of the quest
	 * @param questDescription - description of the quest
	 * @param triggerMapName - name of the map that is triggered
	 * @param triggerPosition - position of the map that is triggered
	 * @param experiencePointsGained - the number experience points for completing the quest
	 * @param adventuresForFulfillment - adventures needed for fulfillment
	 * @param completionActionType - completion type 
	 * @param completionActionParameter - completion parameter
	 * @param startDate - the start date
	 * @param endDate - the end date
	 */
	public CommandAddQuest(String questTitle, String questDescription, String triggerMapName,
						   Position triggerPosition, int experiencePointsGained, int adventuresForFulfillment,
						   QuestCompletionActionType completionActionType, QuestCompletionActionParameter completionActionParameter,
						   Date startDate, Date endDate)
	{
		this.adventuresForFulfillment = adventuresForFulfillment;
		this.completionActionParameter = completionActionParameter;
		this.completionActionType = completionActionType;
		this.endDate = endDate;
		this.experiencePointsGained = experiencePointsGained;
		this.questDescription = questDescription;
		this.questTitle = questTitle;
		this.startDate = startDate;
		this.triggerMapName = triggerMapName;
		this.triggerPosition = triggerPosition;
	}

	/**
	 * Add the quest with the quest manager
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
			manager.addQuest(this.questTitle, this.questDescription, this.triggerMapName, this.triggerPosition, this.experiencePointsGained,
					this.adventuresForFulfillment, this.completionActionType, this.completionActionParameter, this.startDate, this.endDate);

		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return false;
	}


}
