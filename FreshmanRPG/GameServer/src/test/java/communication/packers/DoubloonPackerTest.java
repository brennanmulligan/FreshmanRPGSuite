package communication.packers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import datasource.ServerSideTest;
import org.junit.Test;

import communication.messages.DoubloonPrizeMessage;
import dataDTO.DoubloonPrizeDTO;
import model.reports.DoubloonPrizeReport;

/**
 *
 * @author Andrew M, Christian C
 *
 * This is the test class for the doubloon packer
 *
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
