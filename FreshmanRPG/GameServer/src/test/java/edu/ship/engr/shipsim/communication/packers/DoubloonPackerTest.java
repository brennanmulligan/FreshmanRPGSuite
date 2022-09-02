package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.DoubloonPrizeMessage;
import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.model.reports.DoubloonPrizeReport;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author Andrew M, Christian C
 * <p>
 * This is the test class for the doubloon packer
 */

public class DoubloonPackerTest extends ServerSideTest
{

    /**
     * Tests that we can pack a report and send a message
     */
    @Test
    public void testPacker()
    {
        ArrayList<DoubloonPrizeDTO> list = new ArrayList<>();
        DoubloonPrizeMessage messages;

        //make DTOs
        DoubloonPrizeDTO dto = new DoubloonPrizeDTO("Test", 100, "Test Description");
        DoubloonPrizeDTO dto2 = new DoubloonPrizeDTO("Test2", 150, "Test Description 2");
        //Add DTOs
        list.add(dto);
        list.add(dto2);
        //Make report and packer
        DoubloonPrizeReport report = new DoubloonPrizeReport(1, list);
        DoubloonPacker packer = new DoubloonPacker();
        messages = packer.pack(report);

        assertEquals(list.get(0).getName(), messages.getName(0));
        assertEquals(list.get(0).getCost(), messages.getPrice(0));
        assertEquals(list.get(0).getDescription(), messages.getDescription(0));


    }

}
