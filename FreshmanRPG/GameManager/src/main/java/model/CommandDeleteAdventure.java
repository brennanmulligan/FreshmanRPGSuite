package model;

/**
 * A command for deleting an adventure
 */
public class CommandDeleteAdventure extends Command
{

	private int questId;
	private int adventureId;
	private final GameManagerQuestManager qm = GameManagerQuestManager.getInstance();

	/**
	 * Initialize our variables
	 * @param questId id of quest
	 * @param adventureId id of adventure
	 */
	public CommandDeleteAdventure(int questId, int adventureId)
	{
		this.questId = questId;
		this.adventureId = adventureId;
	}

	/**
	 * Execute the command
	 * @return True if successful and False if failure
	 */
	@Override
	boolean execute()
	{
		return qm.deleteAdventure(questId, adventureId);
	}

}
