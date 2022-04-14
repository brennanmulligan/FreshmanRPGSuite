package datasource;

import java.util.Date;

import criteria.ObjectiveCompletionCriteria;
import dataENUM.ObjectiveCompletionType;

/**
 * The Objective DTO used by the game manager to import objectives into the database.
 *
 * @author Jordan Long
 *
 */
public class GameManagerObjectiveDTO
{
	private int questID;
	private String questName;
	private String description;
	private int experiencePointsEarned;
	private ObjectiveCompletionType completionType;
	private ObjectiveCompletionCriteria completionParameter;
	private Date startDate;
	private Date endDate;

	/**
	 * Constructor for the GameManager Objective DTO to import objectives to the database.
	 *
	 * @param questID questID
	 * @param questName questName
	 * @param description description
	 * @param exp Experience Points
	 * @param type Quest Type
	 * @param parameter Quest Parameter
	 * @param startDate startDate
	 * @param endDate endDate
	 */
	public GameManagerObjectiveDTO(int questID, String questName, String description, int exp, ObjectiveCompletionType type, ObjectiveCompletionCriteria parameter, Date startDate, Date endDate)
	{
		this.questID = questID;
		this.questName = questName;
		this.description = description;
		this.experiencePointsEarned = exp;
		this.completionType = type;
		this.completionParameter = parameter;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return The QuestID
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return The QuestName
	 */
	public String getQuestName()
	{
		return questName;
	}

	/**
	 * @return Quest StartDate
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @return Quest EndDate
	 */
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * @return Quest Description
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
	 * @return Quest Completion Type
	 */
	public ObjectiveCompletionType getCompletionType()
	{
		return completionType;
	}

	/**
	 * @return Completion Parameters
	 */
	public ObjectiveCompletionCriteria getCompletionParameter()
	{
		return completionParameter;
	}

}
