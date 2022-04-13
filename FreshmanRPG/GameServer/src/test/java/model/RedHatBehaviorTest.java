package model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.ChatType;
import model.reports.ChatMessageReceivedReport;
import model.reports.PlayerFinishedInitializingReport;

/**
 * Tests the RedHatBehavior
 * @author Brad Olah
 */
public class RedHatBehaviorTest
{
	private RedHatBehavior behavior;

	/**
	 * @throws DatabaseException shouldn't Set up the behavior for each test
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		PlayerManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		behavior = new RedHatBehavior(PlayersForTest.RED_HAT.getPlayerID());
		QualifiedObservableConnector.resetSingleton();
		ChatManager.resetSingleton();
	}


	/**
	 * Tests that the sorting hat will listen for a player message.
	 * If they say yes and have not been sorted they will be told their appearance and have their appearance change and the initial quest assigned.
	 * If they say yes and have been sorted nothing will happen.
	 * If they say anything besides yes and have not been sorted they will be told to say yes.
	 * @throws InterruptedException - Shouldn't
	 * @throws IllegalQuestChangeException - Shouldn't
	 * @throws DatabaseException - Shouldn't
	 * @throws SQLException - Shouldn't
	 * @throws IOException
	 */
	@Test
	public void testHatRespondsToPlayer() throws InterruptedException, DatabaseException, IllegalQuestChangeException, SQLException, IOException
	{
		Player p = PlayerManager.getSingleton().addPlayer(PlayersForTest.NEWBIE.getPlayerID());
		PlayerManager.getSingleton().addPlayer(PlayersForTest.RED_HAT.getPlayerID());

		assertEquals("default_player", p.getAppearanceType());
		ChatMessageReceivedReport report = new ChatMessageReceivedReport(p.getPlayerID(), 0, "yes", PlayersForTest.NEWBIE.getPosition(), ChatType.Local);
		behavior.receiveReport(report);
		ModelFacade.getSingleton().getNextCommand().execute();
		assertEquals(PlayersForTest.NEWBIE.getCrew().getCrewAppearanceType(), p.getAppearanceType());

		p.setAppearanceType("male_a");
		assertEquals("male_a", p.getAppearanceType());
		report = new ChatMessageReceivedReport(p.getPlayerID(), 0, "yes", p.getPlayerPosition(), ChatType.Local);
		behavior.receiveReport(report);
		assertEquals("male_a", p.getAppearanceType());

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

		MockQualifiedObserver mock = new MockQualifiedObserver(ChatMessageReceivedReport.class);
		p.setAppearanceType("default_player");
		assertEquals("default_player", p.getAppearanceType());

		PlayerFinishedInitializingReport testConnection = new PlayerFinishedInitializingReport(p.getPlayerID(), p.getPlayerName(), p.getAppearanceType());
		behavior.receiveReport(testConnection);

		ChatMessageReceivedReport report = (ChatMessageReceivedReport) mock.getReport();
		assertEquals("Newbie welcome to the game. Are you ready to be sorted?", report.getChatText());

		p.setAppearanceType("male_a");
		testConnection = new PlayerFinishedInitializingReport(p.getPlayerID(), p.getPlayerName(), p.getAppearanceType());

		behavior.receiveReport(testConnection);
		report = (ChatMessageReceivedReport) mock.getReport();
		assertEquals("Welcome back Newbie. Complete the introduction quest to leave this area.", report.getChatText());
	}

}
