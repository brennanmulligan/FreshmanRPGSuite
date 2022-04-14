package DatabaseBuilders;

import datasource.*;
import datatypes.PlayerOwnsVanityForTest;
import model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Vanity inventory portion of the database
 */
public class BuildTestVanityInventory
{
    /**
     * @param args unused
     * @throws DatabaseException shouldnt
     * @throws SQLException shouldnt
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingMocKDataSource(false);
        OptionsManager.getSingleton().setUsingTestDB(true);
        createVanityInventoryTable();
    }

    /**
     * Create a table of vanity inventory
     *
     * @throws DatabaseException
     */
    private static void createVanityInventoryTable() throws DatabaseException
    {
        VanityInventoryTableDataGatewayRDS.createTable();
        VanityInventoryTableDataGatewayInterface gateway = VanityInventoryTableDataGatewayRDS.getSingleton();
        for (PlayerOwnsVanityForTest v : PlayerOwnsVanityForTest.values())
        {
            gateway.addItemToInventory(v.getPlayerID(), v.getVanityID(), v.getIsWearing());
        }
    }
}
