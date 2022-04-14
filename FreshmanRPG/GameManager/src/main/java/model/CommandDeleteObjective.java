package model;

/**
 * A command for deleting an objective
 */
public class CommandDeleteObjective extends Command
{

	private int questId;
	private int objectiveId;
	private final GameManagerQuestManager qm = GameManagerQuestManager.getInstance();

	/**
	 * Initialize our variables
	 * @param questId id of quest
	 * @param objectiveId id of objective
	 */
	public CommandDeleteObjective(int questId, int objectiveId)
	{
		this.questId = questId;
		this.objectiveId = objectiveId;
	}

	/**
	 * Execute the command
	 * @return True if successful and False if failure
	 */
	@Override
	boolean execute()
	{
		return qm.deleteObjective(questId, objectiveId);
	}

}
