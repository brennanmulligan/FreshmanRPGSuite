package model;

import datasource.DatabaseException;
import datasource.PlayerLoginRowDataGateway;

/**
 * @author Robert Windsich
 *Finds a player id by player name
 *
 */
public class FindPlayerIDFromPlayerName
{

	/**
	 * Find a player's Id by its name
	 * @param playerName to search for
	 * @return The player's Id
	 * @throws DatabaseException Shouldn't
	 */
	public static int getPlayerID(String playerName) throws DatabaseException
	{
			return (new PlayerLoginRowDataGateway(playerName).getPlayerID());
	}

}
