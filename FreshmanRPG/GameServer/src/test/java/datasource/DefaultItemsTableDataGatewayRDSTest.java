package datasource;

import model.OptionsManager;
import org.junit.After;
import org.junit.BeforeClass;

import java.sql.SQLException;

public class DefaultItemsTableDataGatewayRDSTest extends DefaultItemsTableDataGatewayTest
{
    @BeforeClass
    public static void hardReset() throws DatabaseException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        OptionsManager.getSingleton().setTestMode(true);
        DatabaseManager.getSingleton().setTesting();
    }

    @After
    public void tearDown() throws DatabaseException
    {
        try
        {
            DatabaseManager.getSingleton().rollBack();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Get a gateway for an existing row of the table
     * @return the gateway
     * @throws DatabaseException shouldnt
     */
    @Override
    DefaultItemsTableDataGateway findGateway() throws DatabaseException
    {
        return (DefaultItemsTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
                "DefaultItems");
    }
}
