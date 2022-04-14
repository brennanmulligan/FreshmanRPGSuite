package datasource;

import java.util.ArrayList;

/**
 * Requirements for all gateways into the objective table
 *
 * @author merlin
 *
 */
public interface LevelTableDataGateway
{

	/**
	 * return all of the objectives for a given quest
	 *
	 * @return empty list if there aren't any
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	ArrayList<LevelRecord> getAllLevels() throws DatabaseException;

}
