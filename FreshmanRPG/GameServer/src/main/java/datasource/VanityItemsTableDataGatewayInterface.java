package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityType;

import java.util.ArrayList;

public interface VanityItemsTableDataGatewayInterface
{
    /**
     * Gets a vanity item by id
     * @param id the id of the vanity item
     * @return a DTO containing all the info of the vanity item
     */
    VanityDTO getVanityItemByID(int id) throws DatabaseException;

    /**
     * @return a list of all vanity items
     */
    ArrayList<VanityDTO> getAllVanityItems() throws DatabaseException;

    /**
     * Gets a vanity type by id
     * @param id the id of the vanity item
     * @return the type of the vanity
     */
    VanityType getVanityTypeByID(int id) throws DatabaseException;

    /**
     * Updates a vanity item
     * @param id id of the item to be updated
     * @param name the new name
     * @param description the new description
     * @param textureName the new texture name
     * @param vanityType the new vanity type
     */
    void updateVanityItem(int id, String name, String description, String textureName, VanityType vanityType) throws DatabaseException;

    /**
     * Adds a new vanity item
     * @param name the name of the vanity item
     * @param description the description of the vanity item
     * @param textureName the texture name of the vanity item
     * @param vanityType the type of vanity
     */
    void addVanityItem(String name, String description, String textureName, VanityType vanityType) throws DatabaseException;

    /**
     * Resets the test data
     */
    void resetData();
}
