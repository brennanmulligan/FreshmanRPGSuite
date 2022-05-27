package model;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import datasource.ServerSideTest;
import model.reports.ChatMessageToClientReport;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;
import model.cheatCodeBehaviors.MockCheatCodeBehavior;
import model.reports.ChatMessageReceivedReport;
import datatypes.PlayersForTest;

/**
 * @author Dave
 *
 *         Make sure the ChatManager behaves properly.
 */
public class ChatManagerTest extends ServerSideTest
{
	/**
	 * Start fresh for each test
	 */
	@Before
	public void reset()
	{
		ChatManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * Make sure that ChatManger behaves as a singleton
	 */
	@Test
	public void testSingleton()
	{
		ChatManager cm1 = ChatManager.getSingleton();
		ChatManager cm2 = ChatManager.getSingleton();

		assertSame(cm1, cm2);
		ChatManager.resetSingleton();
		assertNotSame(cm1, ChatManager.getSingleton());
	}

	/**
	 * Make sure that ChatManager notifies its observers when it creates a
	 * SendChatMessage report.
	 */
	@Test
	public void testNotifiesObserversOnDirectSend()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ChatMessageToClientReport report = new ChatMessageToClientReport(42, 0, "message",
				new Position(1, 1),
				ChatType.Local);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ChatMessageToClientReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ChatManager.getSingleton().sendChatToClients(42, 0, "message", new Position(1, 1), ChatType.Local);

		EasyMock.verify(obs);
	}

	/**
	 * If it sees a cheat code, it should process it and NOT send the chat to
	 * everyone
	 */
	@Test
	public void handlesCheatCodes()
	{
		// set up a mock object to listen so we can check that it doesn't receive
		// anything
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, ChatMessageReceivedReport.class);
		EasyMock.replay(obs);

		PlayersForTest merlin = PlayersForTest.MERLIN;
		MockCheatCodeBehavior.gaveCheat = false;
		ChatManager.getSingleton().processChatMessage(merlin.getPlayerID(), 0, MockCheatCodeBehavior.CHAT_TEXT,
				merlin.getPosition(), ChatType.Local);

		assertTrue(MockCheatCodeBehavior.gaveCheat);
		EasyMock.verify(obs);
	}

	/**
	 * If it sees a cheat code, it should process it and NOT send the chat to
	 * everyone
	 */
	@Test
	public void sendTheChatIfItIsntACheatCode()
	{
		PlayersForTest merlin = PlayersForTest.MERLIN;
		// set up a mock object to listen so we can check that it doesn't receive
		// anything
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ChatMessageToClientReport.class);
		ChatMessageToClientReport report = new ChatMessageToClientReport(merlin.getPlayerID()
				, 0, "message", merlin.getPosition(),
				ChatType.Local);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);


		MockCheatCodeBehavior.gaveCheat = false;
		ChatManager.getSingleton().processChatMessage(merlin.getPlayerID(), 0, "message",
				merlin.getPosition(), ChatType.Local);

		assertTrue(MockCheatCodeBehavior.gaveCheat);
		EasyMock.verify(obs);
	}

}