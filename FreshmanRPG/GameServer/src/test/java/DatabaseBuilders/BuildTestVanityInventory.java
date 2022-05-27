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
        OptionsManager.getSingleton().setUsingTestDB(true);
        createVanityInventoryTable();
    }

    /**
     * Create a table of vanity inventory
     *
     * @throws DatabaseException shouldn't
     */
    private static void createVanityInventoryTable() throws DatabaseException
    {
        VanityInventoryTableDataGateway.createTable();
        VanityInventoryTableDataGateway gateway =
                VanityInventoryTableDataGateway.getSingleton();
        for (PlayerOwnsVanityForTest v : PlayerOwnsVanityForTest.values())
        {
            gateway.addItemToInventory(v.getPlayerID(), v.getVanityID(), v.getIsWearing());
        }
    }
}
