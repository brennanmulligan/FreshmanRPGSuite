package testData;

import static org.junit.Assert.*;

import datatypes.KnowledgePointPrizesForTest;
import org.junit.Test;

public class TestKnowledgePointPrizesEnum
{

	/*
	 * test some enum objects
	 */
	@Test
	public void testKnowledgePointPrizEnum()
	{

		assertEquals("Bonus Points", KnowledgePointPrizesForTest.BONUSPOINTS.getName());
		assertEquals(100, KnowledgePointPrizesForTest.BONUSPOINTS.getCost());
		assertEquals("Bonus points on your final grade", KnowledgePointPrizesForTest.BONUSPOINTS.getDescription());

		assertEquals("Pizza Party", KnowledgePointPrizesForTest.PIZZAPARTY.getName());
		assertEquals(80, KnowledgePointPrizesForTest.PIZZAPARTY.getCost());
		assertEquals("get free pizza", KnowledgePointPrizesForTest.PIZZAPARTY.getDescription());
	}

}
