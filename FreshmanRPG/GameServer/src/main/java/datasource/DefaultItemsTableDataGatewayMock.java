package datasource;

import dataDTO.VanityDTO;
import datatypes.DefaultItemsForTest;
import datatypes.VanityType;

import java.util.ArrayList;

/**
 * The mock gateway for the items in the default inventory table
 */
public class DefaultItemsTableDataGatewayMock implements DefaultItemsTableDataGateway
{
    private ArrayList<DefaultItemsRow> rows = new ArrayList<>();
    private VanityItemsTableDataGatewayInterface vanityItemsGateway;
    public DefaultItemsTableDataGatewayMock()
    {
        resetTableGateway();
    }

    /**
     * Adds a vanity item to the default item list so it will
     * be in all player's inventories
     *
     * @param defaultID the ID of the vanity item to add
     * @throws DatabaseException shouldn't
     */
    @Override
    public void addDefaultItem(int defaultID) throws DatabaseException
    {
        addDefaultItem(defaultID, 0);
    }

    /**
     * Adds a vanity item to the default item list so it will
     * be in all player's inventories
     *
     * @param defaultID      the ID of the vanity item to add
     * @param defaultWearing 1 if it should be the default a player is wearing, 0 if not
     * @throws DatabaseException shouldn't
     */
    @Override
    public void addDefaultItem(int defaultID, int defaultWearing) throws DatabaseException
    {
        DefaultItemsRow toBeAdded = new DefaultItemsRow(defaultID, defaultWearing);
        if (vanityItemsGateway.getVanityItemByID(defaultID) != null &&
                !rows.contains(toBeAdded))
        {
            rows.add(toBeAdded);
        }
        else
        {
            throw new DatabaseException(
                    "Cannot add default vanity item because it is already there");
        }
    }

    /**
     * Gets the default item for a specific vanity type
     *
     * @param type the vanity type
     * @return a dto of the default item for that type, or null if no default
     * @throws DatabaseException shouldnt
     */
    @Override
    public VanityDTO getDefaultItem(VanityType type) throws DatabaseException
    {
        ArrayList<VanityDTO> defaults = getDefaultItems();
        VanityDTO toReturn = null;
        for (VanityDTO dto : defaults)
        {
            if (dto.getVanityType() == type)
            {
                toReturn = dto;
            }
        }
        return toReturn;
    }

    /**
     * Gets all the default items stored in the database
     *
     * @return a list of the default vanity items all players have
     * @throws DatabaseException shouldn't
     */
    @Override
    public ArrayList<VanityDTO> getDefaultItems() throws DatabaseException
    {
        ArrayList<VanityDTO> results = new ArrayList<>();
        for (DefaultItemsRow row : rows)
        {
            results.add(vanityItemsGateway.getVanityItemByID(row.getVanityID()));
        }
        return results;
    }

    /**
     * @return the vanity items that are default when a player isn't
     * wearing a specific item
     * @throws DatabaseException shouldnt
     */
    @Override
    public ArrayList<VanityDTO> getDefaultWearing() throws DatabaseException
    {
        ArrayList<VanityDTO> results = new ArrayList<>();
        for (DefaultItemsRow row : rows)
        {
            if (row.getDefaultWearing() == 1)
            {
                results.add(vanityItemsGateway.getVanityItemByID(row.getVanityID()));
            }
        }
        return results;
    }

    /**
     * Sets a default item as the default item that
     * is worn if a user doesn't have anything selected
     *
     * @param vanityID the vanity id
     * @throws DatabaseException if the item isn't a default item
     */
    @Override
    public void setDefaultWearing(int vanityID) throws DatabaseException
    {
        ArrayList<VanityDTO> items = getDefaultItems();
        VanityDTO toBeAdded = vanityItemsGateway.getVanityItemByID(vanityID);
        VanityDTO toBeRemoved = null;
        VanityType type;
        ArrayList<VanityDTO> current = getDefaultWearing();

        if (items.contains(toBeAdded))
        {
            type = toBeAdded.getVanityType();
            for (VanityDTO dto : current)
            {
                if (dto.getVanityType() == type)
                {
                    toBeRemoved = dto;
                }
            }

            for (DefaultItemsRow row : rows)
            {
                if (toBeRemoved != null && row.getVanityID() == toBeRemoved.getID())
                {
                    row.defaultWearing = 0;
                }
                if (row.getVanityID() == toBeAdded.getID())
                {
                    row.defaultWearing = 1;
                }
            }
        }
        else
        {
            throw new DatabaseException("Item isn't a default item");
        }
    }

    /**
     * Removes a vanity item from the default item list so it wont
     * be in all player's inventories anymore
     *
     * @param defaultID the id of the item to be removed
     * @throws DatabaseException shouldnt
     */
    @Override
    public void removeDefaultItem(int defaultID) throws DatabaseException
    {
        DefaultItemsRow toBeRemoved = new DefaultItemsRow(defaultID, 0);
        if (!rows.remove(toBeRemoved))
        {
            throw new DatabaseException(
                    "Default item could not be removed because it was not there to begin with");
        }
    }

    /**
     * Resets the data
     */
    @Override
    public void resetTableGateway()
    {
        try
        {
            vanityItemsGateway = VanityItemsTableDataGatewayMock.getSingleton();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        rows = new ArrayList<>();
        for (DefaultItemsForTest item : DefaultItemsForTest.values())
        {
            rows.add(new DefaultItemsRow(item.getDefaultID(), item.getDefaultWearing()));
        }
    }

    /**
     * A class representing a default item row
     */
    private static class DefaultItemsRow
    {
        int vanityID;
        int defaultWearing;

        public DefaultItemsRow(int vanityID, int defaultWearing)
        {
            this.vanityID = vanityID;
            this.defaultWearing = defaultWearing;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }
            DefaultItemsRow that = (DefaultItemsRow) o;
            return vanityID == that.vanityID;
        }

        public int getDefaultWearing()
        {
            return defaultWearing;
        }

        public int getVanityID()
        {
            return vanityID;
        }
    }
}
