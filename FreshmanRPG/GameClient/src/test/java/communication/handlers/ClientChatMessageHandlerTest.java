package communication.handlers;

import static org.junit.Assert.assertEquals;

import model.CommandChatMessageReceivedFromServer;
import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communication.messages.ChatMessageToClient;
import datatypes.ChatType;
import datatypes.Position;
import model.ClientModelFacade;

/**
 * @author Frank Schmidt
 *
 */
public class ClientChatMessageHandlerTest
{

	@BeforeClass
	public static void hardReset()
	{
		OptionsManager.getSingleton().setTestMode(true);
	}

	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void setUp()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, false);
	}

	/**
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		ClientChatMessageHandler h = new ClientChatMessageHandler();
		assertEquals(ChatMessageToClient.class, h.getMessageTypeWeHandle());
	}

	/**
	 * We should add a command to the ModelFacade command queue
	 * 
	 * @throws InterruptedException
	 *             shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		ClientChatMessageHandler handler = new ClientChatMessageHandler();
		ChatMessageToClient
				chat = new ChatMessageToClient(42, 0, "message", new Position(1, 1),
				ChatType.Local);
		handler.process(chat);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		CommandChatMessageReceivedFromServer cmd =
				(CommandChatMessageReceivedFromServer) ClientModelFacade.getSingleton().getNextCommand();
		assertEquals(42,cmd.getSenderID());
		assertEquals(0, cmd.getReceiverID());
		assertEquals("message",cmd.getChatText());
		assertEquals(new Position(1,1), cmd.getLocation());
		assertEquals(ChatType.Local, cmd.getType());
		ClientModelFacade.getSingleton().emptyQueue();
	}
}