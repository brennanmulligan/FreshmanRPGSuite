package datasource;

import datatypes.Position;

/**
 * Required functionality for all row data gateways into the PlayerConnection
 * information
 *
 * @author Merlin
 *
 */
public interface PlayerConnectionRowDataGateway
{

	/**
	 * delete the row in the table for the current player id
	 *
	 * @throws DatabaseException if the data source has an exception
	 */
	void deleteRow() throws DatabaseException;

	/**
	 * @return the time when the row was last updated
	 * @throws DatabaseException if the data source has an exception
	 */
	String getChangedOn() throws DatabaseException;

	/**
	 * @return the name of the map associated with this connection
	 */
	String getMapName();

	/**
	 * @return this player's pin
	 */
	int getPin();

	/**
	 * set data back to a know state; used only for testing
	 */
	void resetData();

	/**
	 * Used only for testing!
	 *
	 * @param newTime the timestamp
	 * @throws DatabaseException if the data source can't store the new time
	 */
	void setChangedOn(String newTime) throws DatabaseException;

	/**
	 * change the name of the map associated with this connection
	 *
	 * @param string new map name
	 * @throws DatabaseException if the data source has an exception
	 */
	void storeMapName(String string) throws DatabaseException;

	/**
	 * Store a new pin for our player
	 *
	 * @param pin the new PIN
	 * @throws DatabaseException if the data source cannot store the pin
	 */
	void storePin(int pin) throws DatabaseException;

	/**
	 * @return the player's position on the current map
	 */
	Position getPosition();

	/**
	 * @param position the player's new position
	 * @throws DatabaseException if we can't talk to the database
	 */
	void storePosition(Position position) throws DatabaseException;

}
