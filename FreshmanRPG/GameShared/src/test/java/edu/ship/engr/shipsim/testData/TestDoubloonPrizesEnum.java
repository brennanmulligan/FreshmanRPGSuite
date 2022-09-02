package edu.ship.engr.shipsim.testData;

import edu.ship.engr.shipsim.datatypes.DoubloonPrizesForTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDoubloonPrizesEnum
{

    /*
     * test some enum objects
     */
    @Test
    public void testDoubloonPrizeEnum()
    {

        assertEquals("Bonus Points", DoubloonPrizesForTest.BONUSPOINTS.getName());
        assertEquals(100, DoubloonPrizesForTest.BONUSPOINTS.getCost());
        assertEquals("Bonus points on your final grade", DoubloonPrizesForTest.BONUSPOINTS.getDescription());

        assertEquals("Pizza Party", DoubloonPrizesForTest.PIZZAPARTY.getName());
        assertEquals(80, DoubloonPrizesForTest.PIZZAPARTY.getCost());
        assertEquals("get free pizza", DoubloonPrizesForTest.PIZZAPARTY.getDescription());
    }

}
