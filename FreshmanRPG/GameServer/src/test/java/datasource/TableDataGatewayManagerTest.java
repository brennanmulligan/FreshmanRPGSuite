package datasource;

import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TableDataGatewayManagerTest
{
    @BeforeClass
    public static void hardReset()
    {
        OptionsManager.getSingleton().setUsingMocKDataSource(true);
        OptionsManager.getSingleton().setTestMode(true);
    }

    @Before
    public void setup()
    {
        TableDataGatewayManager.resetSingleton();
    }

    @Test
    public void canGetMock()
    {
        DoubloonPrizesTableDataGatewayMock x =
                (DoubloonPrizesTableDataGatewayMock) TableDataGatewayManager.getSingleton()
                        .getTableGateway("DoubloonPrizes");
        assertNotNull(x);
    }

    @Test
    public void canGetRDS()
    {
        OptionsManager.getSingleton().setUsingMocKDataSource(false);
        DoubloonPrizesTableDataGatewayRDS x =
                (DoubloonPrizesTableDataGatewayRDS) TableDataGatewayManager.getSingleton()
                        .getTableGateway("DoubloonPrizes");

        assertNotNull(x);
        OptionsManager.getSingleton().setUsingMocKDataSource(true);
    }

    @Test
    public void canStoreBothMockAndRDS()
    {
        //Make sure it stores different gateways for Mock and RDS
        OptionsManager.getSingleton().setUsingMocKDataSource(true);
        TableDataGateway mock =
                TableDataGatewayManager.getSingleton().getTableGateway("DoubloonPrizes");
        OptionsManager.getSingleton().setUsingMocKDataSource(false);
        TableDataGateway rds = TableDataGatewayManager.getSingleton().getTableGateway(
                "DoubloonPrizes");
        assertNotSame(mock, rds);

        //Make sure we get the same one if we go back to mock mode
        OptionsManager.getSingleton().setUsingMocKDataSource(true);
        TableDataGateway mock2 =
                TableDataGatewayManager.getSingleton().getTableGateway("DoubloonPrizes");
        assertSame(mock, mock2);
    }

    @Test
    public void factoriesAreSingletons()
    {
        DoubloonPrizesTableDataGateway x =
                (DoubloonPrizesTableDataGateway) TableDataGatewayManager.getSingleton()
                        .getTableGateway("DoubloonPrizes");
        DoubloonPrizesTableDataGateway y =
                (DoubloonPrizesTableDataGateway) TableDataGatewayManager.getSingleton()
                        .getTableGateway("DoubloonPrizes");
        assertSame(x, y);
    }

    @Test
    public void isSingleton()
    {
        TableDataGatewayManager x = TableDataGatewayManager.getSingleton();
        TableDataGatewayManager y = TableDataGatewayManager.getSingleton();
        assertSame(x, y);
    }
    
}
