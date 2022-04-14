package model;

import datasource.ObjectiveStateTableDataGateway;
import datasource.ObjectiveStateTableDataGatewayMock;
import datasource.ObjectiveStateTableDataGatewayRDS;
import datasource.DatabaseException;
import datatypes.ObjectiveStateEnum;

/**
 *Calls the updateState method in the objective state table data gateway
 */
public class CommandObjectiveCompleted extends Command
{
	private int playerID;
	private int questID;
	private int objectiveID;
	private ObjectiveStateEnum newState;
	private boolean needingNotification;

	private ObjectiveStateTableDataGateway objectiveStateGateway;


	/**
	 * @param playerID of player to change
	 * @param questID of quest to change
	 * @param objectiveID of objective to change
	 */
	public CommandObjectiveCompleted(int playerID, int questID, int objectiveID)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.objectiveID = objectiveID;
		this.needingNotification = true;
		this.newState = ObjectiveStateEnum.COMPLETED;

	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			this.objectiveStateGateway = ObjectiveStateTableDataGatewayMock.getSingleton();

		}
		else
		{
			this.objectiveStateGateway = ObjectiveStateTableDataGatewayRDS.getSingleton();
		}

		try
		{
			objectiveStateGateway.updateState(playerID, questID, objectiveID, newState, needingNotification);

		}
		catch (DatabaseException e)
		{
			return false;
		}

		return true;
	}

}
