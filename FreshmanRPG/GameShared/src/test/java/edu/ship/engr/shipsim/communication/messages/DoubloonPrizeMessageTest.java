package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Christian C, Andrew M
 */
@GameTest("GameShared")
public class DoubloonPrizeMessageTest
{

    private ArrayList<DoubloonPrizeDTO> list = new ArrayList<>();

    /*
     * test the easy constructor set up
     */
    @Test
    public void testConstructor()
    {

        DoubloonPrizeDTO dto = new DoubloonPrizeDTO("pizza", 100, "pizza party");
        list.add(dto);
        DoubloonPrizeMessage kpm = new DoubloonPrizeMessage(1, false, list);

        assertEquals("pizza", kpm.getName(0));
        assertEquals(100, kpm.getPrice(0));
        assertEquals("pizza party", kpm.getDescription(0));
        assertFalse(kpm.isQuietMessage());
    }

}
