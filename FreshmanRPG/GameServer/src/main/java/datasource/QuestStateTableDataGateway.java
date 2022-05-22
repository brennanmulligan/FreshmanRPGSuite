package datasource;

import java.util.ArrayList;

import dataDTO.QuestStateRecordDTO;
import datatypes.QuestStateEnum;

/**
 * Requirements for all implementations of the table gateway into the quest
 * state data source
 *
 * @author Merlin
 *
 */
public interface QuestStateTableDataGateway extends TableDataGateway
{

	/**
	 * Get all of the quest states for a given player
	 *
	 * @param playerID the player
	 * @return a list of states
	 * @throws DatabaseException if we can't talk to the data source
	 */
	ArrayList<QuestStateRecordDTO> getQuestStates(int playerID) throws DatabaseException;

	/**
	 * Create the table from the data in QuestStatesForTest
	 *
	 * @throws DatabaseException if we can't talk to the data source
	 */
	void createTable() throws DatabaseException;

	/**
	 * Put a new row in the table
	 *
	 * @param playerID the player's ID
	 * @param questID the quest ID
	 * @param state the player's state for that quest
	 * @param needingNotification true if the player needs to be told of this
	 *            state
	 * @throws DatabaseException if we can't talk to the data source
	 */
	void createRow(int playerID, int questID, QuestStateEnum state, boolean needingNotification)
			throws DatabaseException;

	/**
	 * Change the state of a given quest for a given player
	 *
	 * @param playerID the player
	 * @param questID the ID of the quest
	 * @param newState the new state of the quest for this player
	 * @param needingNotification true if the player should be notified about
	 *            this quest's state
	 * @throws DatabaseException if the data source cannot make the change
	 */
	void udpateState(int playerID, int questID, QuestStateEnum newState, boolean needingNotification)
			throws DatabaseException;

	/**
	 * retrieves all quests
	 * @return all the quests
	 */
	ArrayList<QuestStateRecordDTO> retrieveAllQuestStates() throws DatabaseException;


}
