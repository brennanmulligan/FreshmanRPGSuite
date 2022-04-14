package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.VanityItemsTableDataGatewayInterface;
import datasource.VanityItemsTableDataGatewayRDS;
import datatypes.VanityForTest;
import datatypes.VanityType;
import model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Vanity item portion of the database
 */
public class BuildTestVanityItems
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
        createVanityItemTable();
    }

    /**
     * Create a table of vanity items
     *
     * @throws DatabaseException shouldnt
     */
    public static void createVanityItemTable() throws DatabaseException
    {
        VanityItemsTableDataGatewayRDS.createTable();
        VanityItemsTableDataGatewayInterface gateway = VanityItemsTableDataGatewayRDS.getSingleton();
        for (VanityForTest v : VanityForTest.values())
        {
            gateway.addVanityItem(v.getName(), v.getDescription(), v.getTextureName(), VanityType.fromInt(v.getVanityType()));
        }
    }
}
