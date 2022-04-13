package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import criteria.GameLocationDTO;
import datatypes.AdventuresForTest;

/**
 * Test to make sure the TeleportOnQuestFinishReport works
 *
 * @author Chris Hersh, Zach Thompson, Abdul
 *
 */
public class TeleportOnQuestCompletionReportTest
{

	/**
	 * Tests the creation of the TeleportOnQuestFinishReport
	 */
	@Test
	public void testInitialization()
	{
		String host = "hostname";
		int port = 22;
		GameLocationDTO gl = new GameLocationDTO("theGreen.tmx", null);

		TeleportOnQuestCompletionReport report = new TeleportOnQuestCompletionReport(1,
				AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), gl, host, port);

		assertEquals(1, report.getPlayerID());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), report.getQuestID());
		assertEquals(gl, report.getLocation());
		assertEquals(host, report.getHostName());
		assertEquals(port, report.getPortNumber());
	}

}