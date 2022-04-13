package model;

import criteria.AdventureCompletionCriteria;
import dataENUM.AdventureCompletionType;
import datasource.DatabaseException;

/**
 * @author Darin Alleman and Darnell Martin
 * Command to Edit an Adventure
 */
public class CommandEditAdventure extends Command
{
	private int questId;
	private int adventureId;
	private String adventureDescription;
	private AdventureCompletionType adventureCompletionType;
	private AdventureCompletionCriteria adventureCompletionCriteria;
	private int experiencePointsGained;

	/**
	 * Constructor for command
	 * @param questID - quest ID of the adventure to edit
	 * @param adventureID - adventure ID of the adventure to edit
	 * @param adventureDescription - description of the adventure
	 * @param experienceGained - exp gained of the adventure when completed
	 * @param completionType - completion type of the adventure
	 * @param completionCriteria - completion criteria of the adventure
	 */
	public CommandEditAdventure(int questID, int adventureID, String adventureDescription, int experienceGained,
								AdventureCompletionType completionType, AdventureCompletionCriteria completionCriteria)
	{
		this.questId = questID;
		this.adventureId = adventureID;
		this.adventureDescription = adventureDescription;
		this.experiencePointsGained = experienceGained;
		this.adventureCompletionType = completionType;
		this.adventureCompletionCriteria = completionCriteria;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerQuestManager.getInstance().editAdventure(questId, adventureId, adventureDescription, experiencePointsGained, adventureCompletionType, adventureCompletionCriteria);
			return true;
		}
		catch (DatabaseException e)
		{
			return false;
		}
	}

}
