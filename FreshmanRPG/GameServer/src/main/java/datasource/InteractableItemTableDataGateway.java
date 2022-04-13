package datasource;

import java.util.ArrayList;

import dataDTO.InteractableItemDTO;

/**
 * Methods to be inherited by InteractableItemTableDataGateways
 *
 * @author Elisabeth Ostrow, Jake Moore
 */
public abstract class InteractableItemTableDataGateway
{
	/**
	 * Used for testing to set the data back to a known state
	 */
	public abstract void resetData();

	/**
	 * Find all items that have locations on the given map
	 *
	 * @param mapName the map to get the items from
	 * @return a list of all items on the given map
	 * @throws DatabaseException if an error occurs
	 */
	public abstract ArrayList<InteractableItemDTO> getItemsOnMap(String mapName) throws DatabaseException;

	/**
	 * Gets all items in DB
	 *
	 * @return all interactable items
	 * @throws DatabaseException if an error occurs
	 */
	public abstract ArrayList<InteractableItemDTO> getAllItems() throws DatabaseException;

}
