package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.DoubloonPrizeMessage;
import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.DoubloonPrizeReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Andrew M, Christian C
 * <p>
 * This is the test class for the doubloon packer
 */
@GameTest("GameServer")
public class DoubloonPackerTest
{

    /**
     * Tests that we can pack a report and send a message
     */
    @Test
    public void testPacker()
    {
        ArrayList<DoubloonPrizeDTO> list = new ArrayList<>();

        //make DTOs
        DoubloonPrizeDTO dto = new DoubloonPrizeDTO("Test", 100, "Test Description");
        DoubloonPrizeDTO dto2 = new DoubloonPrizeDTO("Test2", 150, "Test Description 2");
        //Add DTOs
        list.add(dto);
        list.add(dto2);
        //Make report and packer
        DoubloonPrizeReport report = new DoubloonPrizeReport(PlayersForTest.MERLIN.getPlayerID(), list);
        DoubloonPacker packer = new DoubloonPacker();
        StateAccumulator accum = new StateAccumulator(null).setPlayerId(PlayersForTest.MERLIN.getPlayerID());
        packer.setAccumulator(accum);
        DoubloonPrizeMessage messages = packer.pack(report);

        assertEquals(list.get(0).getName(), messages.getName(0));
        assertEquals(list.get(0).getCost(), messages.getPrice(0));
        assertEquals(list.get(0).getDescription(), messages.getDescription(0));
    }

}
