package datasource;

import model.OptionsManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Mock test
 *
 * @author Jake Moore, Elisabeth Ostrow
 */
public class InteractableItemTableDataGatewayMockTest
        extends InteractableItemTableDataGatewayTest
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
                (DefaultItemsTableDataGatewayMock) getGatewaySingleton();
        gateway.resetTableGateway();
    }
    /**
     * Gets the correct singleton
     */
    @Override
    public InteractableItemTableDataGateway getGatewaySingleton()
    {
        return (InteractableItemTableDataGateway) TableDataGatewayManager.getSingleton()
                .getTableGateway("InteractableItem");
    }

}
