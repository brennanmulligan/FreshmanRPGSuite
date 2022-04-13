package datasource;

import java.util.Date;

import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datatypes.Position;

/**
 * The Quest DTO used by the GameManager to import quests into the database.
 * @author Jordan Long
 *
 */
public class GameManagerQuestDTO
{
	private String questTitle;
	private String description;
	private String triggerMapName;
	private int triggerRow;
	private int triggerCol;
	private int experiencePointsEarned;
	private int fulfillment;
	private QuestCompletionActionType completionActionType;
	private QuestCompletionActionParameter completionActionParameter;
	private Date startDate;
	private Date endDate;


	/**
	 * Constructor for the Quest DTO to import into the database.
	 *
	 * @param questTitle questTitle
	 * @param description description
	 * @param triggerMapName triggerMapName
	 * @param triggerRow triggerRow
	 * @param triggerCol triggerCol
	 * @param exp Experience Points
	 * @param fulfillment fulfillment
	 * @param completionActionType  completionActionType
	 * @param completionActionParameter completionActionParameter 
	 * @param startDate startDate of quest
	 * @param endDate endDate of quest
	 */
	public GameManagerQuestDTO(String questTitle, String description, String triggerMapName, int triggerRow, int triggerCol, int exp,
							   int fulfillment, QuestCompletionActionType completionActionType, QuestCompletionActionParameter completionActionParameter, Date startDate, Date endDate)
	{
		this.questTitle = questTitle;
		this.description = description;
		this.triggerMapName = triggerMapName;
		this.triggerRow = triggerRow;
		this.triggerCol = triggerCol;
		this.experiencePointsEarned = exp;
		this.fulfillment = fulfillment;
		this.completionActionType = completionActionType;
		this.completionActionParameter = completionActionParameter;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Converts to Date and then returns a Date object.
	 * @return the StartDate
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @return The EndDate
	 */
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * @return The Description of the quest
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return Quest Exp
	 */
	public int getExperiencePointsEarned()
	{
		return experiencePointsEarned;
	}

	/**
	 * @return the questTitle
	 */
	public String getQuestTitle()
	{
		return questTitle;
	}

	/**
	 * @return the map_location
	 */
	public String getTriggerMapName()
	{
		return triggerMapName;
	}

	/**
	 * @return the trigger_x
	 */
	public int getTrigger_x()
	{
		return triggerRow;
	}

	/**
	 * @return the trigger_y
	 */
	public int getTrigger_y()
	{
		return triggerCol;
	}

	/**
	 * @return p Position of the trigger for quest.
	 */
	public Position getTriggerPosition()
	{
		Position p = new Position(triggerRow, triggerCol);
		return p;
	}

	/**
	 * @return the fulfillment
	 */
	public int getFulfillment()
	{
		return fulfillment;
	}

	/**
	 * @return the completionActionType
	 */
	public QuestCompletionActionType getCompletionActionType()
	{
		return completionActionType;
	}

	/**
	 * @return the completionActionParameter
	 */
	public QuestCompletionActionParameter getCompletionActionParameter()
	{
		return completionActionParameter;
	}


}
