package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityType;

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
     * Adds a vanity item to the default item list so it will
     * be in all player's inventories
     * @param defaultID the ID of the vanity item to add
     * @throws DatabaseException shouldn't
     */
    void addDefaultItem(int defaultID, int defaultWearing) throws DatabaseException;

    /**
     * Removes a vanity item to the default item list so it wont
     * be in all player's inventories anymore
     * @param defaultID the id of the item to be removed
     * @throws DatabaseException shouldnt
     */
    void removeDefaultItem(int defaultID) throws DatabaseException;

    /**
     * Gets the default item for a specific vanity type
     * @param type the vanity type
     * @return a dto of the default item for that type, or null if no default
     * @throws DatabaseException shouldnt
     */
    VanityDTO getDefaultItem(VanityType type) throws DatabaseException;

    /**
     * @return the vanity items that are default when a player isn't
     * wearing a specific item
     * @throws DatabaseException shouldnt
     */
    ArrayList<VanityDTO> getDefaultWearing() throws DatabaseException;

    /**
     * Sets a default item as the default item that
     * is worn if a user doesn't have anything selected
     * @param vanityID the vanity id
     * @throws DatabaseException if the item isn't a default item
     */
    void setDefaultWearing(int vanityID) throws DatabaseException;

    /**
     * Resets the data
     * @throws DatabaseException shouldnt
     */
    void resetData() throws DatabaseException;
}
