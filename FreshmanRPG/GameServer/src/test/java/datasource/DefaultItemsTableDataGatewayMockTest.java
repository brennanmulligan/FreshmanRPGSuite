package datasource;

import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;

public class DefaultItemsTableDataGatewayMockTest extends DefaultItemsTableDataGatewayTest
{

    @BeforeClass
    public static void hardReset()
    {
        OptionsManager.getSingleton().setUsingMocKDataSource(true);
    }

    @Before
    public void setUp() throws DatabaseException
    {
        DefaultItemsTableDataGatewayMock gateway =
                (DefaultItemsTableDataGatewayMock) findGateway();
        gateway.resetTableGateway();
    }
    @Override
    DefaultItemsTableDataGateway findGateway() throws DatabaseException
    {
        return (DefaultItemsTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
                "DefaultItems");
    }
}
