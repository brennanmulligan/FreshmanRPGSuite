package communication.packers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import communication.messages.KnowledgePointPrizeMessage;
import dataDTO.KnowledgePointPrizeDTO;
import model.reports.KnowledgePointPrizeReport;

/**
 *
 * @author Andrew M, Christian C
 *
 * This is the test class for the knowledge point packer
 *
 */

public class KnowledgePointPackerTest
{

	/**
	 * Tests that we can pack a report and send a message
	 */
	@Test
	public void testPacker()
	{
		ArrayList<KnowledgePointPrizeDTO> list = new ArrayList<>();
		KnowledgePointPrizeMessage messages;

		//make DTOs
		KnowledgePointPrizeDTO dto = new KnowledgePointPrizeDTO("Test", 100, "Test Description");
		KnowledgePointPrizeDTO dto2 = new KnowledgePointPrizeDTO("Test2", 150, "Test Description 2");
		//Add DTOs
		list.add(dto);
		list.add(dto2);
		//Make report and packer
		KnowledgePointPrizeReport report = new KnowledgePointPrizeReport(1, list);
		KnowledgePointPacker packer = new KnowledgePointPacker();
		messages = packer.pack(report);

		assertEquals(list.get(0).getName(), messages.getName(0));
		assertEquals(list.get(0).getCost(), messages.getPrice(0));
		assertEquals(list.get(0).getDescription(), messages.getDescription(0));


	}

}
