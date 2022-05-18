package datasource;

import dataDTO.DoubloonPrizeDTO;
import datatypes.DoubloonPrizesForTest;
import org.junit.After;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Mina Kindo, Christian Chroutamel
 */
public abstract class DoubloonPrizesTableDataGatewayTest extends DatabaseTest
{

    // gateway instance
    private DoubloonPrizesTableDataGateway gateway;

    public abstract DoubloonPrizesTableDataGateway getGatewaySingleton()
            throws DatabaseException;

    @Test
    public void isSingleton() throws DatabaseException
    {
        DoubloonPrizesTableDataGateway obj1 = getGatewaySingleton();
        DoubloonPrizesTableDataGateway obj2 = getGatewaySingleton();
        assertSame(obj1, obj2);
        assertNotNull(obj2);
    }

    @After
    public void tearDown() throws DatabaseException, SQLException
    {
        super.tearDown();
        if (gateway != null)
        {
            gateway.resetTableGateway();
        }
    }

    @Test
    public void testGetAllDoubloonPrizes() throws DatabaseException
    {
        DoubloonPrizesTableDataGateway gateway = getGatewaySingleton();
        ArrayList<DoubloonPrizeDTO> itemListFromGateway = gateway.getAllDoubloonPrizes();
        ArrayList<DoubloonPrizeDTO> itemListFromEnum = new ArrayList<>();
        DoubloonPrizesForTest[] prizes = DoubloonPrizesForTest.values();

        //Make DTOs of the stuff in the enum
        for (DoubloonPrizesForTest prize : prizes)
        {
            itemListFromEnum.add(new DoubloonPrizeDTO(prize.getName(), prize.getCost(),
                    prize.getDescription()));
        }

        //Tests that the gateway has the same amount of values as in the data source
        assertEquals(itemListFromEnum.size(), itemListFromGateway.size());

        //make sure the DTO list we get from the gateway has the correct information
        for (int i = 0; i < itemListFromGateway.size(); i++)
        {
            assertTrue(itemListFromGateway.contains(itemListFromEnum.get(i)));
        }
    }
}
