package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityForTest;
import datatypes.VanityShopItemsForTest;
import datatypes.VanityType;
import model.VanityTableRow;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a mock gateway to the items that are in the vanity shop.
 * @author Aaron, Jake
 */
public class VanityShopTableDataGatewayMock implements VanityShopTableDataGateway
{
    private static VanityShopTableDataGateway singleton;
    private ArrayList<VanityShopRow> rows = new ArrayList<>();
    private VanityItemsTableDataGatewayInterface vanityItemsGateway = VanityItemsTableDataGatewayMock.getSingleton();

    private class VanityShopRow
    {
        int vanityID;
        int price;

        VanityShopRow(int vanityID, int price)
        {
            this.vanityID = vanityID;
            this.price = price;
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
            VanityShopRow that = (VanityShopRow) o;
            return vanityID == that.vanityID && price == that.price;
        }
    }
    /**
     * Creates a new mock gateway
     */
    private VanityShopTableDataGatewayMock() throws DatabaseException
    {
        resetData();
    }

    /**
     * Retrieves the mock gateway singleton.
     *
     * @return singleton
     */
    public static synchronized VanityShopTableDataGateway getSingleton() throws DatabaseException
    {
        if (singleton == null)
        {
            singleton = new VanityShopTableDataGatewayMock();
        }
        return singleton;
    }

    @Override
    public ArrayList<VanityDTO> getVanityShopInventory() throws DatabaseException
    {
        ArrayList<VanityDTO> vanityItems = new ArrayList<>();

        for (VanityShopRow item : rows)
        {
            VanityDTO actualItem = vanityItemsGateway.getVanityItemByID(item.vanityID);
            VanityDTO shopItem = new VanityDTO(actualItem.getID(), actualItem.getName(), actualItem.getDescription(), actualItem.getTextureName(), actualItem.getVanityType(), item.price);
            vanityItems.add(shopItem);
        }
        return vanityItems;
    }

    @Override
    public void addItem(int vanityID, int price) throws DatabaseException
    {
        if(vanityItemsGateway.getVanityItemByID(vanityID) == null || price < 0)
        {
            throw new DatabaseException("Can't add item to shop because it is invalid");
        }
        VanityShopRow toBeAdded = new VanityShopRow(vanityID, price);
        if (!rows.contains(toBeAdded))
        {
            rows.add(toBeAdded);
        }
        else
        {
            throw new DatabaseException("Shop already has that item");
        }
    }

    @Override
    public void updateItem(int vanityID, int price) throws DatabaseException
    {
        boolean updated = false;
        if (price < 0)
        {
            throw new DatabaseException("Price < 0");
        }
        for (VanityShopRow r : rows)
        {
            if (r.vanityID == vanityID)
            {
                r.price = price;
                updated = true;
            }
        }
        if (!updated)
        {
            throw new DatabaseException("Couldn't update the shop");
        }
    }

    @Override
    public void resetData()
    {
        rows = new ArrayList<>();
        for(VanityShopItemsForTest item : VanityShopItemsForTest.values())
        {
            rows.add(new VanityShopRow(item.getVanityID(), item.getPrice()));
        }
    }
}
