package datasource;

import model.OptionsManager;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class TableDataGatewayManagerTest
{
    @BeforeClass
    public static void hardReset()
    {
        OptionsManager.getSingleton().setTestMode(true);
    }
    @Test
    public void isSingleton()
    {
        TableDataGatewayManager x = TableDataGatewayManager.getSingleton();
        TableDataGatewayManager y = TableDataGatewayManager.getSingleton();
        assertSame(x,y);
    }

    @Test
    public void canGetMock()
    {
        DoubloonPrizesTableDataGatewayMock p = new DoubloonPrizesTableDataGatewayMock();
        DoubloonPrizesTableDataGateway x =
                (DoubloonPrizesTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway("DoubloonPrizes");
        assertNotNull(x);
    }

    @Test
    public void factoriesAreSingletons()
    {
        DoubloonPrizesTableDataGateway x =
                (DoubloonPrizesTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway("DoubloonPrizes");
        DoubloonPrizesTableDataGateway y =
                (DoubloonPrizesTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway("DoubloonPrizes");
        assertSame(x,y);
    }
}
