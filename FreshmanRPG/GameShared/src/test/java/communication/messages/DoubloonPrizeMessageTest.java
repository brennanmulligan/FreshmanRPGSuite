package communication.messages;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dataDTO.DoubloonPrizeDTO;

/*
 * @Author: Christian C, Andrew M
 */
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
		DoubloonPrizeMessage kpm = new DoubloonPrizeMessage(1, list);

		assertEquals("pizza", kpm.getName(0));
		assertEquals(100, kpm.getPrice(0));
		assertEquals("pizza party", kpm.getDescription(0));
	}

}
