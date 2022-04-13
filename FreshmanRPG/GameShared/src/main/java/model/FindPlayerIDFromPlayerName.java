package model;

import datasource.DatabaseException;
import datasource.PlayerLoginRowDataGateway;
import datasource.PlayerLoginRowDataGatewayMock;
import datasource.PlayerLoginRowDataGatewayRDS;

/**
 * @author Robert Windsich
 *Finds a player id by player name
 *
 */
public class FindPlayerIDFromPlayerName
{

	//TODO WHY DO WE NEED THIS?
	private static PlayerLoginRowDataGateway gateway;


	/**
	 * Find a player's Id by its name
	 * @param playerName to search for
	 * @return The player's Id
	 * @throws DatabaseException Shouldn't
	 */
	public static int getPlayerID(String playerName) throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			gateway = new PlayerLoginRowDataGatewayMock(playerName);
		}
		else
		{
			gateway = new PlayerLoginRowDataGatewayRDS(playerName);
		}

		return gateway.getPlayerID();
	}

}
