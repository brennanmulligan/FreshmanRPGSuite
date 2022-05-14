package model;

import java.sql.SQLException;

import datatypes.PlayersForTest;
import model.reports.ChatMessageToClientReport;
import model.reports.NPCChatReport;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.ChatType;
import model.reports.ChatMessageReceivedReport;

/**
 * @author merlin
 *
 */
public class TutorBehaviorTest
{
	private TutorBehavior behavior;

	/**
	 * @throws DatabaseException shouldn't 
	 * Set up the behavior for each test
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		PlayerManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		behavior = new TutorBehavior(PlayersForTest.TUTOR.getPlayerID());
		QualifiedObservableConnector.resetSingleton();
		ChatManager.resetSingleton();
	}

	/**
	 * Makes sure that the tutor responds to a chat message that is ChatType.Local
	 * @throws InterruptedException - Shouldn't
	 * @throws IllegalQuestChangeException - Shouldn't
	 * @throws DatabaseException - Shouldn't
	 * @throws SQLException - Shouldn't
	 */
	@Test
	public void testTutorRespondsToPlayer() throws InterruptedException, DatabaseException, IllegalQuestChangeException, SQLException
	{
		Player p = PlayerManager.getSingleton().addPlayer(PlayersForTest.NICK.getPlayerID());
		PlayerManager.getSingleton().addPlayer(PlayersForTest.TUTOR.getPlayerID());

		PlayerManager.getSingleton().addPlayer(1);
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ChatMessageToClientReport.class);
		obs.receiveReport(EasyMock.anyObject(ChatMessageToClientReport.class));
		EasyMock.replay(obs);

		NPCChatReport
				report = new NPCChatReport(p.getPlayerID(), 0, "Hello, tutor",
				PlayersForTest.NICK.getPosition(), ChatType.Local);
		behavior.receiveReport(report);

		EasyMock.verify(obs);
	}

	/**
	 * Makes sure that the tutor doesn't respond to a chat message that isnt in ChatType.Local
	 * @throws InterruptedException - Shouldn't
	 * @throws IllegalQuestChangeException - Shouldn't
	 * @throws DatabaseException - Shouldn't
	 * @throws SQLException - Shouldn't
	 */
	@Test
	public void testTutorDoesntRespondToPlayer() throws InterruptedException, DatabaseException, IllegalQuestChangeException, SQLException
	{
		Player p = PlayerManager.getSingleton().addPlayer(PlayersForTest.NICK.getPlayerID());
		PlayerManager.getSingleton().addPlayer(PlayersForTest.TUTOR.getPlayerID());

		PlayerManager.getSingleton().addPlayer(1);
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, ChatMessageReceivedReport.class);
		EasyMock.replay(obs);

		NPCChatReport report = new NPCChatReport(p.getPlayerID(), 0, "Hello, tutor",
				PlayersForTest.NICK.getPosition(), ChatType.Zone);
		behavior.receiveReport(report);

		EasyMock.verify(obs);
	}
}
