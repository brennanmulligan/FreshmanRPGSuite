package datasource;

import dataDTO.VanityDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for vanityTDG
 */
public interface VanityInventoryTableDataGatewayInterface
{
    /**
     * @return the list of vanity items
     */
    ArrayList<VanityDTO> getWearing(int playerID) throws DatabaseException;

    /**
     * @return vanity DTOs of owned items
     */
    ArrayList<VanityDTO> getAllOwnedItems(int playerID) throws DatabaseException;

    /**
     * Saves what a player has in their inventory
     * @param playerID the ID of the player to update
     * @param newInventory the list of items in their new inventory
     * @throws DatabaseException shouldnt
     */
    void updateInventory(int playerID, ArrayList<VanityDTO> newInventory) throws DatabaseException;

    /**
     * Saves what a player is currently wearing
     * @param playerID the ID of the player to update
     * @param newWearing the list of items they are wearing
     * @throws DatabaseException if the player doesn't own the item they are equipping
     */
    void updateCurrentlyWearing(int playerID, ArrayList<VanityDTO> newWearing) throws DatabaseException;

    /**
     * Adds an item to a player's owned items
     * @param playerID the ID of the player to update
     * @param vanityID the ID of the vanity they now own
     * @throws DatabaseException if the database couldn't update
     */
    void addItemToInventory(int playerID, int vanityID) throws DatabaseException;

    /**
     * Adds an item to a player's owned items
     * @param playerID the ID of the player to update
     * @param vanityID the ID of the vanity they now own
     * @param isWearing 1 if the player is wearing it, 0 if not
     * @throws DatabaseException if the database couldn't update
     */
    void addItemToInventory(int playerID, int vanityID, int isWearing) throws DatabaseException;

    /**
     * Resets the test data
     * @throws DatabaseException if database problem happened
     */
    void resetData() throws DatabaseException;
}
