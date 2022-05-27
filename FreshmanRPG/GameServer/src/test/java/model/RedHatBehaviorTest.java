package model;

import datasource.DatabaseException;
import datasource.ServerSideTest;
import datatypes.PlayersForTest;
import model.reports.ChatMessageToClientReport;
import model.reports.PlayerFinishedInitializingReport;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the RedHatBehavior
 * @author Brad Olah
 */
public class RedHatBehaviorTest extends ServerSideTest
{
	private RedHatBehavior behavior;

	/**
	 * @throws DatabaseException shouldn't Set up the behavior for each test
	 */
	@Before
	public void localSetUp() throws DatabaseException
	{
		PlayerManager.resetSingleton();
		behavior = new RedHatBehavior(PlayersForTest.RED_HAT.getPlayerID());
		QualifiedObservableConnector.resetSingleton();
		ChatManager.resetSingleton();
	}

	/**
	 * Tests that the sorting hat will send a message when a player logs in.
	 * It will ask if they are ready to be sorted if they have not already.
	 * It will tell them to do the introduction quest if they have been sorted.
	 */
	@Test
	public void testHatRespondsToLogin()
	{

		Player p = PlayerManager.getSingleton().addPlayer(PlayersForTest.NEWBIE.getPlayerID());
		PlayerManager.getSingleton().addPlayer(PlayersForTest.RED_HAT.getPlayerID());

		MockQualifiedObserver mock = new MockQualifiedObserver(ChatMessageToClientReport.class);
		p.setAppearanceType("default_player");
		assertEquals("default_player", p.getAppearanceType());

		PlayerFinishedInitializingReport testConnection = new PlayerFinishedInitializingReport(p.getPlayerID(), p.getPlayerName(), p.getAppearanceType());
		behavior.receiveReport(testConnection);

		ChatMessageToClientReport report = (ChatMessageToClientReport) mock.getReport();
		assertEquals("Newbie welcome to the game. Are you ready to be sorted?", report.getChatText());

		p.setAppearanceType("male_a");
		testConnection = new PlayerFinishedInitializingReport(p.getPlayerID(), p.getPlayerName(), p.getAppearanceType());

		behavior.receiveReport(testConnection);
		report = (ChatMessageToClientReport) mock.getReport();
		assertEquals("Welcome back Newbie. Complete the introduction quest to leave this area.", report.getChatText());
	}

}
