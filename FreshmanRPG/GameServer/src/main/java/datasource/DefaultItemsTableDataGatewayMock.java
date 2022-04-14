package datasource;

import dataDTO.VanityDTO;
import datatypes.DefaultItemsForTest;

import java.util.ArrayList;

/**
 * The mock gateway for the items in the default inventory table
 */
public class DefaultItemsTableDataGatewayMock implements DefaultItemsTableDataGateway
{
    private ArrayList<Integer> rows = new ArrayList<>();
    private VanityItemsTableDataGatewayInterface vanityItemsGateway;
    private static DefaultItemsTableDataGateway singleton;

    private DefaultItemsTableDataGatewayMock() throws DatabaseException
    {
        resetData();
    }

    /**
     * Gets the instance of this gateway
     * @return the instance
     */
    public static synchronized DefaultItemsTableDataGateway getSingleton() throws DatabaseException
    {
        if (singleton == null)
        {
            singleton = new DefaultItemsTableDataGatewayMock();
        }
        return singleton;
    }

    /**
     * Gets all the default items stored in the database
     * @return a list of the default vanity items all players have
     * @throws DatabaseException shouldn't
     */
    @Override
    public ArrayList<VanityDTO> getDefaultItems() throws DatabaseException
    {
        ArrayList<VanityDTO> results = new ArrayList<>();
        for (int id : rows)
        {
            results.add(vanityItemsGateway.getVanityItemByID(id));
        }
        return results;
    }

    /**
     * Adds a vanity item to the default item list so it will
     * be in all player's inventories
     * @param defaultID the ID of the vanity item to add
     * @throws DatabaseException shouldn't
     */
    @Override
    public void addDefaultItem(int defaultID) throws DatabaseException
    {
        if (vanityItemsGateway.getVanityItemByID(defaultID) != null && !rows.contains(defaultID))
        {
            rows.add(defaultID);
        }
        else
        {
        throw new DatabaseException("Cannot add default vanity item because it is already there");
        }
    }

    @Override
    public void removeDefaultItem(int defaultID) throws DatabaseException
    {
        if (!rows.remove((Integer)defaultID))
        {
            throw new DatabaseException("Default item could not be removed because it was not there to begin with");
        }
    }

    @Override
    public void resetData() throws DatabaseException
    {
        vanityItemsGateway = VanityItemsTableDataGatewayMock.getSingleton();
        rows = new ArrayList<>();
        for (DefaultItemsForTest item : DefaultItemsForTest.values())
        {
            rows.add(item.getDefaultID());
        }
    }
}
