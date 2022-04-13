package model;

import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.AdventureStateTableDataGatewayRDS;
import datasource.DatabaseException;
import datatypes.AdventureStateEnum;

/**
 *Calls the updateState method in the adventure state table data gateway
 */
public class CommandAdventureCompleted extends Command
{
	private int playerID;
	private int questID;
	private int adventureID;
	private AdventureStateEnum newState;
	private boolean needingNotification;

	private AdventureStateTableDataGateway adventureStateGateway;


	/**
	 * @param playerID of player to change
	 * @param questID of quest to change
	 * @param adventureID of adventure to change
	 */
	public CommandAdventureCompleted(int playerID, int questID, int adventureID)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
		this.needingNotification = true;
		this.newState = AdventureStateEnum.COMPLETED;

	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			this.adventureStateGateway = AdventureStateTableDataGatewayMock.getSingleton();

		}
		else
		{
			this.adventureStateGateway = AdventureStateTableDataGatewayRDS.getSingleton();
		}

		try
		{
			adventureStateGateway.updateState(playerID, questID, adventureID, newState, needingNotification);

		}
		catch (DatabaseException e)
		{
			return false;
		}

		return true;
	}

}
