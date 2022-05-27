package model;

import datasource.ServerSideTest;
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
public class TutorBehaviorTest extends ServerSideTest
{
	private TutorBehavior behavior;

	/**
	 * @throws DatabaseException shouldn't 
	 * Set up the behavior for each test
	 */
	@Before
	public void localSetUp() throws DatabaseException
	{
		PlayerManager.resetSingleton();
		behavior = new TutorBehavior(PlayersForTest.TUTOR.getPlayerID());
		QualifiedObservableConnector.resetSingleton();
		ChatManager.resetSingleton();
	}

	/**
	 * Makes sure that the tutor responds to a chat message that is ChatType.Local
	 */
	@Test
	public void testTutorRespondsToPlayer()
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
	 */
	@Test
	public void testTutorDoesntRespondToPlayer()
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
