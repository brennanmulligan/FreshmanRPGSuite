package dataDTO;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * @author Andrew M, Christian C 
 * This is the test class for the DoubloonPrizeDTO
 */
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
