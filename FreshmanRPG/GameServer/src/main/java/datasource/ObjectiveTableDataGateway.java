package datasource;

import java.util.ArrayList;

import datatypes.Position;
import model.ObjectiveRecord;

/**
 * Requirements for all gateways into the objective table
 *
 * @author merlin
 *
 */
public interface ObjectiveTableDataGateway
{

	/**
	 * return all of the objectives for a given quest
	 *
	 * @param questID the quest's unique ID
	 * @return null if there aren't any
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	ArrayList<ObjectiveRecord> getObjectivesForQuest(int questID) throws DatabaseException;

	/**
	 * Get the information about a specific objective
	 *
	 * @param questID the quest containing the objective
	 * @param objectiveID the objective's ID within that quest
	 * @return the objective's information
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	ObjectiveRecord getObjective(int questID, int objectiveID) throws DatabaseException;

	/**
	 * Returns a list of all objectives that are completed at the specified
	 * location.
	 *
	 * @param mapName the map where the user is located
	 * @param pos the position of the player
	 * @return null if there aren't any
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	ArrayList<ObjectiveRecord> findObjectivesCompletedForMapLocation(String mapName, Position pos)
			throws DatabaseException;

	/**
	 * Returns the next appropriate objective ID to use.
	 *
	 * @param questID - quest that will contain objective
	 * @return objective ID to use
	 * @throws DatabaseException - if no rows found with quest ID
	 */
	int getNextObjectiveID(int questID) throws DatabaseException;

	/**
	 * Used for testing
	 */
	void resetData();

}
