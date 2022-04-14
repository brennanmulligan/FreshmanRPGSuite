package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityForTest;
import datatypes.VanityType;
import model.VanityTableRow;
import java.util.ArrayList;

/**
 * Represents a mock gateway to the items that are in the vanity shop.
 * @author Aaron, Jake
 */
public class VanityShopTableDataGatewayMock implements VanityShopTableDataGateway
{
    private static VanityShopTableDataGateway singleton;
    private final ArrayList<VanityTableRow> rows = new ArrayList<>();

    /**
     * Creates a new mock gateway
     */
    public VanityShopTableDataGatewayMock()
    {
        for(VanityForTest item : VanityForTest.values())
        {
            rows.add(new VanityTableRow(item.getId(), item.getVanityType(), item.getName(), item.getDescription(), item.getTextureName(), item.getPrice()));
        }
    }

    /**
     * Retrieves the mock gateway singleton.
     *
     * @return singleton
     */
    public static synchronized VanityShopTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new VanityShopTableDataGatewayMock();
        }
        return singleton;
    }

    @Override
    public ArrayList<VanityTableRow> getRows()
    {
        return rows;
    }

    @Override
    public ArrayList<VanityDTO> getVanityShopInventory()
    {
        ArrayList<VanityDTO> vanityItems = new ArrayList<>();

        for (VanityTableRow item : rows)
        {
            VanityDTO actualItem = new VanityDTO(item.getId(), item.getName(), item.getDescription(), item.getTextureName(), VanityType.fromInt(item.getVanityType()), item.getPrice());
            vanityItems.add(actualItem);
        }
        return vanityItems;
    }
}
