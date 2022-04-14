package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datasource.ObjectivesForTest;
import datatypes.ObjectiveStateEnum;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Tests the report of a change state
 * @author nk3668
 *
 */
public class ObjectiveStateChangeReportTest
{

	/** 
	 * Tests the creation of the ObjectiveStateChangeReport 
	 */
	@Test
	public void testInitialization() 
	{
		int playerID = 42;
		int questID = ObjectivesForTest.ONE.getQuestID();
		ObjectiveStateChangeReportInClient report = new ObjectiveStateChangeReportInClient(playerID, questID, 1, ObjectivesForTest.ONE.getObjectiveDescription(), ObjectiveStateEnum.TRIGGERED);
		assertEquals(ObjectivesForTest.ONE.getObjectiveID(), report.getObjectiveID());
		assertEquals(ObjectivesForTest.ONE.getObjectiveDescription(),report.getObjectiveDescription());
		assertEquals(ObjectiveStateEnum.TRIGGERED, report.getNewState());
		assertEquals(playerID, report.getPlayerID());
		assertEquals(questID, report.getQuestID());
	}
	
	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(ObjectiveStateChangeReportInClient.class).verify();
	}
}
