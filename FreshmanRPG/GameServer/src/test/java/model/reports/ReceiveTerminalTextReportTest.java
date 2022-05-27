package model.reports;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import model.QuestManager;
import datatypes.PlayersForTest;

/**
 *
 * @author Denny Fleagle, Chris Roadcap
 *
 */
public class ReceiveTerminalTextReportTest extends ServerSideTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void localSetUp()
	{
		QuestManager.resetSingleton();
	}

	/**
	 * Test the creation of report
	 */
	@Test
	public void createReport()
	{
		String resultText = "unknown";

		ReceiveTerminalTextReport report = new ReceiveTerminalTextReport(PlayersForTest.DAVE.getPlayerID(), resultText, "");

		assertEquals(PlayersForTest.DAVE.getPlayerID(), report.getPlayerID());
		assertEquals(resultText, report.getResultText());

	}

}
