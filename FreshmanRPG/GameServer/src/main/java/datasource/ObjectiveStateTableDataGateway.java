package datasource;

import java.util.ArrayList;

import dataDTO.ObjectiveStateRecordDTO;
import datatypes.ObjectiveStateEnum;

/**
 * Defines the operations required by a gateway into the states of objectives
 * for each player
 *
 * @author Merlin
 *
 */
public interface ObjectiveStateTableDataGateway extends TableDataGateway
{

	/**
	 * Get a player's states of all of the objectives for a given quest
	 *
	 * @param playerID the player
	 * @param questID the quest
	 * @return all of the objective states
	 * @throws DatabaseException if we can't get the info from the data source
	 */
	ArrayList<ObjectiveStateRecordDTO> getObjectiveStates(int playerID, int questID) throws DatabaseException;

	/**
	 * Get a list of all of the objectives that a player current has pending
	 *
	 * @param playerID the player
	 * @return the list
	 * @throws DatabaseException if we can't talk to the data source
	 */
	ArrayList<ObjectiveStateRecordDTO> getPendingObjectivesForPlayer(int playerID) throws DatabaseException;

	/**
	 * Get a list of all of the uncompleted (e.g. HIDDEN or TRIGGERED) objectives that a player current has
	 *
	 * @param playerID the player
	 * @return the list
	 * @throws DatabaseException if we can't talk to the data source
	 */
	ArrayList<ObjectiveStateRecordDTO> getUncompletedObjectivesForPlayer(int playerID) throws DatabaseException;

	/**
	 * Change the state of an objective for a given player. If no state
	 * currently exists, this will add it
	 *
	 * @param playerID the player
	 * @param questID the quest the objective is a part of
	 * @param objectiveID the objective
	 * @param newState the new state
	 * @param needingNotification true if the user still needs to be notified
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	void updateState(int playerID, int questID, int objectiveID, ObjectiveStateEnum newState,
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
	 * @param objectiveID the objective within that quest
	 * @param state the state of the objective for that player
	 * @param needingNotification true if the player should be notified about
	 *            this objective state
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	void createRow(int playerID, int questID, int objectiveID, ObjectiveStateEnum state, boolean needingNotification)
			throws DatabaseException;

}
