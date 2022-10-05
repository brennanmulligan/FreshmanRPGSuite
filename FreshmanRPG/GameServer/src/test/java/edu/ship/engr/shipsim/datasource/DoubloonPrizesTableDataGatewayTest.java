package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import edu.ship.engr.shipsim.datatypes.DoubloonPrizesForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mina Kindo, Christian Chroutamel
 */
@GameTest("GameServer")
public class DoubloonPrizesTableDataGatewayTest
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
