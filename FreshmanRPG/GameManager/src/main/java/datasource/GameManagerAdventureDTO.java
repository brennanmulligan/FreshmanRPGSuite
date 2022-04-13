package datasource;

import java.util.Date;

import criteria.AdventureCompletionCriteria;
import dataENUM.AdventureCompletionType;

/**
 * The Adventure DTO used by the game manager to import adventures into the database.
 *
 * @author Jordan Long
 *
 */
public class GameManagerAdventureDTO
{
	private int questID;
	private String questName;
	private String description;
	private int experiencePointsEarned;
	private AdventureCompletionType completionType;
	private AdventureCompletionCriteria completionParameter;
	private Date startDate;
	private Date endDate;

	/**
	 * Constructor for the GameManager Adventure DTO to import adventure to the database.
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
	public GameManagerAdventureDTO(int questID, String questName, String description, int exp, AdventureCompletionType type, AdventureCompletionCriteria parameter, Date startDate, Date endDate)
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
	public AdventureCompletionType getCompletionType()
	{
		return completionType;
	}

	/**
	 * @return Completion Parameters
	 */
	public AdventureCompletionCriteria getCompletionParameter()
	{
		return completionParameter;
	}

}
