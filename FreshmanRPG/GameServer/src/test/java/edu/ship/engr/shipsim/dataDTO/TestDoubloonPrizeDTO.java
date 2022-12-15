package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Andrew M, Christian C
 * This is the test class for the DoubloonPrizeDTO
 */
@GameTest("GameServer")
public class TestDoubloonPrizeDTO
{

    /**
     * This test tests the constructor and the getters
     */
    @Test
    public void testConstructor()
    {
        DoubloonPrizeDTO doubloonDTO = new DoubloonPrizeDTO("test Prize", 500, "test Description");

        assertEquals("test Prize", doubloonDTO.getName());
        assertEquals(500, doubloonDTO.getCost());
        assertEquals("test Description", doubloonDTO.getDescription());
    }

}
