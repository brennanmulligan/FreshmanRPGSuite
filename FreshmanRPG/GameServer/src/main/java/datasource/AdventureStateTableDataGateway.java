package datasource;

import java.util.ArrayList;

import dataDTO.AdventureStateRecordDTO;
import datatypes.AdventureStateEnum;

/**
 * Defines the operations required by a gateway into the states of adventures
 * for each player
 *
 * @author Merlin
 *
 */
public interface AdventureStateTableDataGateway
{

	/**
	 * Get a player's states of all of the adventures for a given quest
	 *
	 * @param playerID the player
	 * @param questID the quest
	 * @return all of the adventure states
	 * @throws DatabaseException if we can't get the info from the data source
	 */
	ArrayList<AdventureStateRecordDTO> getAdventureStates(int playerID, int questID) throws DatabaseException;

	/**
	 * Get a list of all of the adventures that a player current has pending
	 *
	 * @param playerID the player
	 * @return the list
	 * @throws DatabaseException if we can't talk to the data source
	 */
	ArrayList<AdventureStateRecordDTO> getPendingAdventuresForPlayer(int playerID) throws DatabaseException;

	/**
	 * Get a list of all of the uncompleted (e.g. HIDDEN or TRIGGERED) adventures that a player current has
	 *
	 * @param playerID the player
	 * @return the list
	 * @throws DatabaseException if we can't talk to the data source
	 */
	ArrayList<AdventureStateRecordDTO> getUncompletedAdventuresForPlayer(int playerID) throws DatabaseException;

	/**
	 * Change the state of an adventure for a given player. If no state
	 * currently exists, this will add it
	 *
	 * @param playerID the player
	 * @param questID the quest the adventure is a part of
	 * @param adventureID the adventure
	 * @param newState the new state
	 * @param needingNotification true if the user still needs to be notified
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	void updateState(int playerID, int questID, int adventureID, AdventureStateEnum newState,
					 boolean needingNotification) throws DatabaseException;

	/**
	 * Create the table
	 *
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	void createTable() throws DatabaseException;

	/**
	 * Add a row to the table
	 *
	 * @param playerID the player
	 * @param questID the quest
	 * @param adventureID the adventure within that quest
	 * @param state the state of the adventure for that player
	 * @param needingNotification true if the player should be notified about
	 *            this adventure state
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	void createRow(int playerID, int questID, int adventureID, AdventureStateEnum state, boolean needingNotification)
			throws DatabaseException;

	/**
	 * reset the data back to the data in AdventureStatesForTest
	 */
	void resetData();

}
