package datasource;

import dataDTO.VanityDTO;
import datatypes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A gateway to the player's inventory
 */
public class VanityInventoryTableDataGatewayMock implements VanityInventoryTableDataGatewayInterface
{
    private HashMap<Integer, ArrayList<MockPlayerOwnsVanityRow>> allPlayerInv;
    private VanityItemsTableDataGatewayInterface vanityItemsGateway;

    private static VanityInventoryTableDataGatewayInterface singleton;

    /**
     * Gets the instance of this gateway
     * @return the instance
     * @throws DatabaseException shouldn't
     */
    public static synchronized VanityInventoryTableDataGatewayInterface getSingleton() throws DatabaseException
    {
        if (singleton == null)
        {
            singleton = new VanityInventoryTableDataGatewayMock();
        }
        return singleton;
    }

    /**
     * A class that represents a row in the database
     */
    private class MockPlayerOwnsVanityRow
    {
        int playerID;
        int vanityID;
        int isWearing;

        /**
         * A mock row
         * @param playerID the ID of the player
         * @param vanityID the ID of the vanity item
         * @param isWearing if the player is wearing the vanity item
         */
        public MockPlayerOwnsVanityRow(int playerID, int vanityID, int isWearing)
        {
            this.playerID = playerID;
            this.vanityID = vanityID;
            this.isWearing = isWearing;
        }

        /**
         * @return if they are wearing the vanity item
         */
        public int getIsWearing()
        {
            return isWearing;
        }
    }

    /**
     * Makes a new instance of the gateway and reset the data
     * @throws DatabaseException shouldnt
     */
    private VanityInventoryTableDataGatewayMock() throws DatabaseException
    {
            resetData();
    }

    /**
     * @return vanity DTOs that the player is wearing
     */
    public ArrayList<VanityDTO> getWearing(int playerID) throws DatabaseException
    {
        ArrayList<VanityDTO> dtoList = new ArrayList<>();

        ArrayList<MockPlayerOwnsVanityRow> rows = allPlayerInv.get(playerID);
        if (rows == null)
        {
            rows = new ArrayList<>();
        }
        for (MockPlayerOwnsVanityRow row : rows)
        {
            if (row.getIsWearing() == 1)
            {
                dtoList.add(vanityItemsGateway.getVanityItemByID(row.vanityID));
            }
        }
        return dtoList;
    }

    /**
     * @return vanity DTOs of owned items
     */
    public ArrayList<VanityDTO> getAllOwnedItems(int playerID) throws DatabaseException
    {
        ArrayList<VanityDTO> dtoList = new ArrayList<>();

        ArrayList<MockPlayerOwnsVanityRow> rows = allPlayerInv.get(playerID);
        if (rows == null)
        {
            rows = new ArrayList<>();
        }
        for(MockPlayerOwnsVanityRow row : rows)
        {
            dtoList.add(vanityItemsGateway.getVanityItemByID(row.vanityID));
        }
        ArrayList<VanityDTO> defaultItems = DefaultItemsTableDataGatewayMock.getSingleton().getDefaultItems();
        for (VanityDTO v : defaultItems)
        {
            if (!dtoList.contains(v))
            {
                dtoList.add(v);
            }
        }
        return dtoList;
    }

    /**
     * Resets the test data
     * @throws DatabaseException if database problem happened
     */
    @Override
    public void resetData() throws DatabaseException
    {
        vanityItemsGateway = VanityItemsTableDataGatewayMock.getSingleton();
        allPlayerInv = new HashMap<>();
        for (PlayerOwnsVanityForTest p : PlayerOwnsVanityForTest.values())
        {
            MockPlayerOwnsVanityRow row = new MockPlayerOwnsVanityRow(p.getPlayerID(), p.getVanityID(), p.getIsWearing());
            insertRow(p.getPlayerID(), row);
        }
    }

    /**
     * Method to add a row to the database
     * @param playerID the ID of the player
     * @param rowToBeAdded the row to add to the database
     * @throws DatabaseException if the row couldn't be added
     */
    private void insertRow(int playerID, MockPlayerOwnsVanityRow rowToBeAdded) throws DatabaseException
    {
        if (allPlayerInv.containsKey(playerID))
        {
            ArrayList<MockPlayerOwnsVanityRow> rows = allPlayerInv.get(playerID);
            for (MockPlayerOwnsVanityRow row : rows)
            {
                if (row.vanityID == rowToBeAdded.vanityID)
                {
                    throw new DatabaseException("Duplicate item (" + row.vanityID + ") in player's (" + row.playerID + ") inventory");
                }
            }
            rows.add(rowToBeAdded);
        }
        else
        {
            ArrayList<MockPlayerOwnsVanityRow> row = new ArrayList<>();
            row.add(rowToBeAdded);
            allPlayerInv.put(playerID, row);
        }
    }

    /**
     * Adds an item to a player's owned items
     * @param playerID the ID of the player to update
     * @param vanityID the ID of the vanity they now own
     * @throws DatabaseException if the database couldn't update
     */
    public void addItemToInventory(int playerID, int vanityID) throws DatabaseException
    {
        insertRow(playerID, new MockPlayerOwnsVanityRow(playerID, vanityID, 0));
    }

    /**
     * Adds an item to a player's owned items
     * @param playerID the ID of the player to update
     * @param vanityID the ID of the vanity they now own
     * @param isWearing 1 if the player is wearing it, 0 if not
     * @throws DatabaseException if the database couldn't update
     */
    @Override
    public void addItemToInventory(int playerID, int vanityID, int isWearing) throws DatabaseException
    {
        insertRow(playerID, new MockPlayerOwnsVanityRow(playerID, vanityID, isWearing));
    }

    /**
     * Updates the player's inventory based on the current state of their inventory
     * @param playerID the ID of the player to update
     * @param newInventory the list of items in their new inventory
     * @throws DatabaseException if the item couldn't be added
     */
    public void updateInventory(int playerID, ArrayList<VanityDTO> newInventory) throws DatabaseException
    {
        ArrayList<VanityDTO> owned = getAllOwnedItems(playerID);
        int lastKnown = newInventory.size() - 1;
        while(lastKnown >= 0 && !owned.contains(newInventory.get(lastKnown)))
        {
                lastKnown--;
        }

        for (int i = lastKnown + 1; i < newInventory.size(); i++)
        {
            addItemToInventory(playerID, newInventory.get(i).getID());
        }
    }

    /**
     * Saves what a player is currently wearing
     * @param playerID the ID of the player to update
     * @param newWearing the list of items they are going to wear
     * @throws DatabaseException if the player doesn't own the item they are equipping
     */
    public void updateCurrentlyWearing(int playerID, ArrayList<VanityDTO> newWearing) throws DatabaseException
    {
        ArrayList<VanityDTO> owned = getAllOwnedItems(playerID);
        ArrayList<Integer> itemsIDs = new ArrayList<>();
        for (VanityDTO dto : newWearing)
        {
            itemsIDs.add(dto.getID());
        }

        for (VanityDTO item : newWearing)
        {
            if (!owned.contains(item))
            {
                throw new DatabaseException("Player " + playerID + "can't put on item "+
                        item.getID() + " because they don't own it.");
            }
        }

        ArrayList<MockPlayerOwnsVanityRow> thisPlayerInventory = allPlayerInv.get(playerID);
        ArrayList<Integer> defaultItemIDs = new ArrayList<>();
        ArrayList<MockPlayerOwnsVanityRow> toRemove = new ArrayList<>();
        for (DefaultItemsForTest item : DefaultItemsForTest.values())
        {
            defaultItemIDs.add(item.getDefaultID());
        }

        if(thisPlayerInventory != null)
        {
            for (MockPlayerOwnsVanityRow row : thisPlayerInventory)
            {
                row.isWearing = 0;
                if (itemsIDs.contains(row.vanityID))
                {
                    row.isWearing = 1;
                }
                if (row.isWearing == 0 && defaultItemIDs.contains(row.vanityID))
                {
                    toRemove.add(row);
                }
            }
            allPlayerInv.get(playerID).removeAll(toRemove);
        }
    }
}
