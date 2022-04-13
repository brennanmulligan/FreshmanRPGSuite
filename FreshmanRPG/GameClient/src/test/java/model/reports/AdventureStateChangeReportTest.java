package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datasource.AdventuresForTest;
import datatypes.AdventureStateEnum;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Tests the report of a change state
 * @author nk3668
 *
 */
public class AdventureStateChangeReportTest 
{

	/** 
	 * Tests the creation of the AdventureStateChangeReport 
	 */
	@Test
	public void testInitialization() 
	{
		int playerID = 42;
		int questID = AdventuresForTest.ONE.getQuestID();
		AdventureStateChangeReportInClient report = new AdventureStateChangeReportInClient(playerID, questID, 1, AdventuresForTest.ONE.getAdventureDescription(), AdventureStateEnum.TRIGGERED);
		assertEquals(AdventuresForTest.ONE.getAdventureID(), report.getAdventureID());
		assertEquals(AdventuresForTest.ONE.getAdventureDescription(),report.getAdventureDescription());
		assertEquals(AdventureStateEnum.TRIGGERED, report.getNewState());
		assertEquals(playerID, report.getPlayerID());
		assertEquals(questID, report.getQuestID());
	}
	
	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(AdventureStateChangeReportInClient.class).verify();
	}
}
