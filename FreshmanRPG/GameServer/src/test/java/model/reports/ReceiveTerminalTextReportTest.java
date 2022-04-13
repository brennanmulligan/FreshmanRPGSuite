package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dataDTO.PlayerDTO;
import model.OptionsManager;
import model.QuestManager;
import datatypes.PlayersForTest;

/**
 *
 * @author Denny Fleagle, Chris Roadcap
 *
 */
public class ReceiveTerminalTextReportTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		QuestManager.resetSingleton();
	}

	/**
	 * Test the creation of report
	 */
	@Test
	public void createReport()
	{
		PlayerDTO player1 = new PlayerDTO();
		player1.setPlayerName(PlayersForTest.MERLIN.getPlayerName());
		player1.setMapName(PlayersForTest.MERLIN.getMapName());

		PlayerDTO player2 = new PlayerDTO();
		player2.setPlayerName(PlayersForTest.JAWN.getPlayerName());
		player2.setMapName(PlayersForTest.JAWN.getMapName());

		String resultText = "unknown";

		ReceiveTerminalTextReport report = new ReceiveTerminalTextReport(PlayersForTest.DAVE.getPlayerID(), resultText, "");

		assertEquals(PlayersForTest.DAVE.getPlayerID(), report.getPlayerID());
		assertEquals(resultText, report.getResultText());

	}

}
