package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;
import model.reports.ChatReceivedReport;
import model.reports.ChatSentReport;

/**
 * Tests the functionality of the ChatManager
 * 
 * @author Steve
 *
 */
public class ChatManagerTest 
{
	/**
	 * Always start with a new singleton
	 */
	@Before
	public void reset()
	{
		ClientPlayerManager.resetSingleton();
		ClientChatManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}
	

	/**
	 * There should be only one manager
	 */
	@Test
	public void testSingleton()
	{
		ClientChatManager cm1 = ClientChatManager.getSingleton();
		assertSame(cm1, ClientChatManager.getSingleton());
		ClientChatManager.resetSingleton();
		assertNotSame(cm1, ClientChatManager.getSingleton());
	}
	
	/**
	 * When an area chat is received, a report will always be sent out
	 */
	@Test
	public void notifiesOnAreaChatReceived()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ChatReceivedReport report = new ChatReceivedReport(42, 0, "message", ChatType.Zone);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ChatReceivedReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ClientChatManager.getSingleton().sendChatToUI(42, 0, "message", new Position(0,0), ChatType.Zone);

		EasyMock.verify(obs);
	}
	
	/**
	 * When a local chat is received, a report will always be sent out
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void notifiesOnLocalChatReceived() throws AlreadyBoundException, NotBoundException
	{
		ClientPlayerManager.getSingleton().initiateLogin("X", "X");
		ClientPlayer p = ClientPlayerManager.getSingleton().finishLogin(1);
		p.setPosition(new Position(5,5));
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ChatReceivedReport report = new ChatReceivedReport(1, 0, "message", ChatType.Local);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ChatReceivedReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ClientChatManager.getSingleton().sendChatToUI(1, 0, "message", new Position(0,0), ChatType.Local);

		EasyMock.verify(obs);
	}
	
	/**
	 * Conditions for when a local message should be received
	 * @throws AlreadyBoundException shouldn't
	 * @throws NotBoundException shouldn't
	 */
	@Test
	public void canReceiveLocalMessageValid() throws AlreadyBoundException, NotBoundException
	{
		ClientPlayerManager.getSingleton().initiateLogin("X", "X");
		ClientPlayer p = ClientPlayerManager.getSingleton().finishLogin(1);
		p.setPosition(new Position(5,5));
		
		assertTrue(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(5,5)));
		assertTrue(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(0,5)));
		assertTrue(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(5,0)));
		assertTrue(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(0,0)));
		assertTrue(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(6,6)));
		assertTrue(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(10,10)));
		assertTrue(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(0,10)));
		assertTrue(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(10,0)));
	}
	
	/**
	 * Conditions for when a local message should not be received
	 * @throws AlreadyBoundException shouldn't
	 * @throws NotBoundException shouldn't
	 */
	@Test
	public void canReceiveLocalMessageInvalid() throws AlreadyBoundException, NotBoundException
	{
		ClientPlayerManager.getSingleton().initiateLogin("X", "X");
		ClientPlayer p = ClientPlayerManager.getSingleton().finishLogin(1);
		p.setPosition(new Position(5,5));
		
		assertFalse(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(0,-1)));
		assertFalse(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(-1,0)));
		assertFalse(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(11,0)));
		assertFalse(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(0,11)));
		assertFalse(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(5,11)));
		assertFalse(ClientChatManager.getSingleton().canReceiveLocalMessage(new Position(11,5)));
	}
	
	/**
	 * Properly sends a report for a message going to the server
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void notifiesOnSendChatToServer() throws AlreadyBoundException, NotBoundException
	{
		ClientPlayerManager.getSingleton().initiateLogin("X", "X");
		ClientPlayer p = ClientPlayerManager.getSingleton().finishLogin(1);
		p.setPosition(new Position(5,5));
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ChatSentReport report = new ChatSentReport(1, 0, "message", p.getPosition(), ChatType.Local);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ChatSentReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ClientChatManager.getSingleton().sendChatToServer("message", ChatType.Local, report.getReceiverID());

		EasyMock.verify(obs);
	}
	
	/**
	 * @throws AlreadyBoundException
	 * @throws NotBoundException
	 */
	@Test
	public void sendChatToFriend() throws AlreadyBoundException, NotBoundException
	{
		ClientPlayerManager.getSingleton().initiateLogin("X", "X");
		ClientPlayer p = ClientPlayerManager.getSingleton().finishLogin(1);
		p.setPosition(new Position(5,5));
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ChatReceivedReport report = new ChatReceivedReport(1, 2, "message", ChatType.Private);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ChatReceivedReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ClientChatManager.getSingleton().sendChatToUI(report.getSenderID(), report.getReceiverID(), "message", p.getPosition(), ChatType.Private);

		EasyMock.verify(obs);
		
	}
	
}
