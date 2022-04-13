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
public abstract class PlayerTableDataGateway
{

	/**
	 * the game location where new players should start
	 */
	public static final GameLocationDTO INITIAL_GAME_LOCATION = new GameLocationDTO("StartingRoom.tmx", new Position(2, 32));

	/**
	 * Used for testing to set the data back to a known state
	 */
	public abstract void resetData();

	/**
	 * @return the top ten players as ranked by experience points
	 * @throws DatabaseException if we can't retrieve the data
	 */
	public abstract ArrayList<PlayerScoreRecord> getTopTenList() throws DatabaseException;

	/**
	 * @return the entire list of players and their experience points
	 * @throws DatabaseException if we can't retrieve the data
	 */
	public abstract ArrayList<PlayerScoreRecord> getHighScoreList() throws DatabaseException;

	/**
	 * @return the entire list of players
	 * @throws DatabaseException if we can't retrieve the data
	 */
	public abstract ArrayList<PlayerDTO> retrieveAllPlayers() throws DatabaseException;

	/**
	 *
	 * @return the list of online players
	 * @throws DatabaseException if we can't retrieve the data
	 */
	public abstract ArrayList<PlayerDTO> retrieveAllOnlinePlayers() throws DatabaseException;
}