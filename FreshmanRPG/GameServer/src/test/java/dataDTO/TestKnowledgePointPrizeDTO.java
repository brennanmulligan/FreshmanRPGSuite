package dataDTO;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * @author Andrew M, Christian C 
 * This is the test class for the KnowledgePointPrizeDTO
 */
public class TestKnowledgePointPrizeDTO
{

	/**
	 * This test tests the constructor and the getters 
	 */
	@Test
	public void testConstructor()
	{

		KnowledgePointPrizeDTO kppDTO = new KnowledgePointPrizeDTO("test Prize", 500, "test Description");

		assertEquals("test Prize", kppDTO.getName());
		assertEquals(500, kppDTO.getCost());
		assertEquals("test Description", kppDTO.getDescription());
	}

}
