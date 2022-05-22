package datasource;

import java.util.ArrayList;

import criteria.GameLocationDTO;
import dataDTO.PlayerDTO;
import datatypes.PlayerScoreRecord;
import datatypes.Position;

/**
 * Defines the behavior required for Player table data gateways
 *
 * @author Merlin
 *
 */
public interface PlayerTableDataGateway extends TableDataGateway
{

	/**
	 * the game location where new players should start
	 */
	GameLocationDTO INITIAL_GAME_LOCATION = new GameLocationDTO("StartingRoom.tmx", new Position(2, 32));

	/**
	 * @return the top ten players as ranked by experience points
	 * @throws DatabaseException if we can't retrieve the data
	 */
	ArrayList<PlayerScoreRecord> getTopTenList() throws DatabaseException;

	/**
	 * @return the entire list of players and their experience points
	 * @throws DatabaseException if we can't retrieve the data
	 */
	ArrayList<PlayerScoreRecord> getHighScoreList() throws DatabaseException;

	/**
	 * @return the entire list of players
	 * @throws DatabaseException if we can't retrieve the data
	 */
	ArrayList<PlayerDTO> retrieveAllPlayers() throws DatabaseException;

	/**
	 *
	 * @return the list of online players
	 * @throws DatabaseException if we can't retrieve the data
	 */
	ArrayList<PlayerDTO> retrieveAllOnlinePlayers() throws DatabaseException;
}