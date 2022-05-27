package datasource;

import dataDTO.DoubloonPrizeDTO;
import datatypes.DoubloonPrizesForTest;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Mina Kindo, Christian Chroutamel
 */
public class DoubloonPrizesTableDataGatewayTest extends ServerSideTest
{

    // gateway instance
    private DoubloonPrizesTableDataGateway gateway;

    public DoubloonPrizesTableDataGateway getGatewaySingleton()
    {
        return DoubloonPrizesTableDataGateway.getSingleton();
    }

    @Test
    public void isSingleton()
    {
        DoubloonPrizesTableDataGateway obj1 = getGatewaySingleton();
        DoubloonPrizesTableDataGateway obj2 = getGatewaySingleton();
        assertSame(obj1, obj2);
        assertNotNull(obj2);
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
