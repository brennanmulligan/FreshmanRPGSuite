package DatabaseBuilders;

import datasource.*;
import datatypes.DefaultItemsForTest;
import datatypes.PlayerOwnsVanityForTest;
import model.OptionsManager;

import java.sql.SQLException;

public class BuildTestDefaultItems
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
        createDefaultItemsTable();
    }

    private static void createDefaultItemsTable() throws DatabaseException
    {
        DefaultItemsTableDataGatewayRDS.createTable();
        DefaultItemsTableDataGateway gateway = DefaultItemsTableDataGatewayRDS.getSingleton();
        for (DefaultItemsForTest v : DefaultItemsForTest.values())
        {
            gateway.addDefaultItem(v.getDefaultID());
        }
    }
}
