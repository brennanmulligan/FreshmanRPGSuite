package datasource;

import dataDTO.VanityDTO;

import java.util.ArrayList;

/**
 * Interface for the default items gateway
 */
public interface DefaultItemsTableDataGateway
{
    /**
     * Gets all the default items stored in the database
     * @return a list of the default vanity items all players have
     * @throws DatabaseException shouldn't
     */
    ArrayList<VanityDTO> getDefaultItems() throws DatabaseException;

    /**
     * Adds a vanity item to the default item list so it will
     * be in all player's inventories
     * @param defaultID the ID of the vanity item to add
     * @throws DatabaseException shouldn't
     */
    void addDefaultItem(int defaultID) throws DatabaseException;

    /**
     * Removes a vanity item to the default item list so it wont
     * be in all player's inventories anymore
     * @param defaultID the id of the item to be removed
     * @throws DatabaseException shouldnt
     */
    void removeDefaultItem(int defaultID) throws DatabaseException;

    /**
     * Resets the data
     * @throws DatabaseException shouldnt
     */
    void resetData() throws DatabaseException;
}
