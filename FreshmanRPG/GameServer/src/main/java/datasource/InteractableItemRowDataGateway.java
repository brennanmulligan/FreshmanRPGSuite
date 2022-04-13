package datasource;

import criteria.InteractableItemActionParameter;
import dataENUM.InteractableItemActionType;
import datatypes.Position;

/**
 * The interface for InteractableItemRowDataGateway
 */
public interface InteractableItemRowDataGateway
{
	/**
	 * Get the Item's ID
	 *
	 * @return itemID
	 */
	public int getItemID();

	/**
	 * Get the name of the item
	 *
	 * @return item's name
	 */
	public String getName();

	/**
	 * Set the name of the item
	 *
	 * @param name - name of the item
	 */
	public void setName(String name);

	/**
	 * Get the Position of the item
	 *
	 * @return position - Type: Position
	 */
	public Position getPosition();

	/**
	 * Set the position
	 *
	 * @param pos - the new position
	 */
	public void setPosition(Position pos);

	/**
	 * Get the actionType
	 *
	 * @return actionType
	 */
	public InteractableItemActionType getActionType();

	/**
	 * Set the actionType
	 *
	 * @param action - the actionType
	 */
	public void setActionType(InteractableItemActionType action);

	/**
	 * @return actionParam
	 */
	public InteractableItemActionParameter getActionParam();

	/**
	 * @param param - The actionParam
	 */
	public void setActionParam(InteractableItemActionParameter param);

	/**
	 * Get the mapName
	 *
	 * @return the mapname
	 */
	public String getMapName();

	/**
	 * set the mapName of the item
	 *
	 * @param mapName - The new mapName
	 */
	public void setMapName(String mapName);

	/**
	 * Delete an item
	 *
	 * @throws DatabaseException - if this action caused a database error.
	 */
	public void delete() throws DatabaseException;

	/**
	 * Reset the data that is currently stored in the gateway.
	 */
	public void resetData();

	/**
	 * Persist the current item to the database.
	 *
	 * @throws DatabaseException if this action caused a database error.
	 */
	public void persist() throws DatabaseException;
}
