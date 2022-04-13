package communication.messages;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dataDTO.KnowledgePointPrizeDTO;

/*
 * @Author: Christian C, Andrew M
 */
public class KnowledgePointPrizeMessageTest
{

	private ArrayList<KnowledgePointPrizeDTO> list = new ArrayList<>();

	/*
	 * test the easy constructor set up
	 */
	@Test
	public void testConstructor()
	{

		KnowledgePointPrizeDTO dto = new KnowledgePointPrizeDTO("pizza", 100, "pizza party");
		list.add(dto);
		KnowledgePointPrizeMessage kpm = new KnowledgePointPrizeMessage(1, list);

		assertEquals("pizza", kpm.getName(0));
		assertEquals(100, kpm.getPrice(0));
		assertEquals("pizza party", kpm.getDescription(0));
	}

}
