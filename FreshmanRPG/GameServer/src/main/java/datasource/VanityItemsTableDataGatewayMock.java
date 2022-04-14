package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityForTest;
import datatypes.VanityType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class VanityItemsTableDataGatewayMock implements VanityItemsTableDataGatewayInterface
{
    private static HashMap<Integer, VanityDTO> vanityItems;
    int nextKey = 1;
    private static VanityItemsTableDataGatewayInterface singleton;

    /**
     * Gets the instance of this gateway
     * @return the instance
     * @throws DatabaseException shouldn't
     */
    public static synchronized VanityItemsTableDataGatewayInterface getSingleton() throws DatabaseException
    {
        if (singleton == null)
        {
            singleton = new VanityItemsTableDataGatewayMock();
        }
        return singleton;
    }

    private VanityItemsTableDataGatewayMock()
    {
        resetData();
    }
    /**
     * Gets a vanity item by id
     *
     * @param id the id of the vanity item
     * @return a DTO containing all the info of the vanity item
     */
    @Override
    public VanityDTO getVanityItemByID(int id)
    {
        return vanityItems.get(id);
    }

    /**
     * @return a list of all vanity items
     */
    @Override
    public ArrayList<VanityDTO> getAllVanityItems()
    {
        return new ArrayList<>(vanityItems.values());
    }

    /**
     * Gets a vanity type by id
     *
     * @param id the id of the vanity item
     * @return the type of the vanity
     */
    @Override
    public VanityType getVanityTypeByID(int id)
    {
        return vanityItems.get(id).getVanityType();
    }

    /**
     * Updates a vanity item
     *
     * @param id          id of the item to be updated
     * @param name        the new name
     * @param description the new description
     * @param textureName the new texture name
     * @param vanityType  the new vanity type
     */
    @Override
    public void updateVanityItem(int id, String name, String description, String textureName, VanityType vanityType)
    {
        VanityDTO item = new VanityDTO(id, name, description, textureName, vanityType);
        vanityItems.put(id, item);
    }

    /**
     * Adds a new vanity item
     *
     * @param name        the name of the vanity item
     * @param description the description of the vanity item
     * @param textureName the texture name of the vanity item
     * @param vanityType  the type of vanity
     * TODO: Change max+1 to increment only
     */
    @Override
    public void addVanityItem(String name, String description, String textureName, VanityType vanityType)
    {
        VanityDTO itemToAdd = new VanityDTO(nextKey, name, description, textureName, vanityType);
        vanityItems.put(nextKey, itemToAdd);
        nextKey++;
    }

    /**
     * Resets the test data
     */
    @Override
    public void resetData()
    {
        nextKey = 1;
        vanityItems = new HashMap<>();
        for (VanityForTest v : VanityForTest.values())
        {
            addVanityItem(v.getName(), v.getDescription(), v.getTextureName(), VanityType.fromInt(v.getVanityType()));
        }
    }
}
