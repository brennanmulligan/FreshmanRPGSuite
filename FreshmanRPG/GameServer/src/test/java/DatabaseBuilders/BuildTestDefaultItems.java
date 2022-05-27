package DatabaseBuilders;

import datasource.*;
import datatypes.DefaultItemsForTest;
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
        OptionsManager.getSingleton().setUsingTestDB(true);
        createDefaultItemsTable();
    }

    private static void createDefaultItemsTable() throws DatabaseException
    {
        DefaultItemsTableDataGateway.createTable();
        DefaultItemsTableDataGateway gateway =
                DefaultItemsTableDataGateway.getSingleton();
        for (DefaultItemsForTest v : DefaultItemsForTest.values())
        {
            gateway.addDefaultItem(v.getDefaultID(), v.getDefaultWearing());
        }
    }
}
