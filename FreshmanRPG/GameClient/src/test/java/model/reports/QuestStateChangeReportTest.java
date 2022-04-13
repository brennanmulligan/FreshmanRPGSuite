package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.QuestStateEnum;

/**
 * @author Ryan
 *
 */
public class QuestStateChangeReportTest 
{

	/**
	 * Test creating a report and test its getters
	 */
	@Test
	public void testInitialization() 
	{
		QuestStateChangeReport r = new QuestStateChangeReport(42, 1, "Big Quest", QuestStateEnum.TRIGGERED);
		
		assertEquals(42,r.getPlayerID());
		assertEquals(1, r.getQuestID());
		assertEquals("Big Quest", r.getQuestDescription());
		assertEquals(QuestStateEnum.TRIGGERED, r.getNewState());
	}

}
