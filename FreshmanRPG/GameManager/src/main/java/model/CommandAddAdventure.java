package model;

import criteria.AdventureCompletionCriteria;
import dataENUM.AdventureCompletionType;
import datasource.DatabaseException;

/**
 * Command used to add an adventure.
 */
public class CommandAddAdventure extends Command
{
	private int questId;
	private String adventureDescription;
	private int experiencePointsGained;
	private AdventureCompletionType adventureCompletionType;
	private AdventureCompletionCriteria adventureCompletionCriteria;

	/**
	 * Construct and initialize a CommandAddAdventure.
	 *
	 * @param questId - quest ID this adventure belongs to
	 * @param adventureDescription - adventure description
	 * @param experiencePointsGained - experience points gained by completing adventure
	 * @param adventureCompletionType - adventure completion type
	 * @param adventureCompletionCriteria - adventure completion criteria
	 */
	public CommandAddAdventure(int questId, String adventureDescription,
							   int experiencePointsGained, AdventureCompletionType adventureCompletionType,
							   AdventureCompletionCriteria adventureCompletionCriteria)
	{
		this.questId = questId;
		this.adventureDescription = adventureDescription;
		this.experiencePointsGained = experiencePointsGained;
		this.adventureCompletionType = adventureCompletionType;
		this.adventureCompletionCriteria = adventureCompletionCriteria;
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
			final boolean success = GameManagerQuestManager.getInstance().addAdventure(questId,
					adventureDescription, experiencePointsGained,
					adventureCompletionType, adventureCompletionCriteria);
			return success;
		}
		catch (DatabaseException e)
		{
			return false;
		}
	}

}
