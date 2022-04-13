package communication.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import communication.messages.ChatMessage;
import datatypes.ChatType;
import datatypes.Position;
import model.ClientModelFacade;
import model.CommandChatMessageReceived;

/**
 * @author Frank Schmidt
 *
 */
public class ChatMessageHandlerTest
{

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
		ChatMessageHandler h = new ChatMessageHandler();
		assertEquals(ChatMessage.class, h.getMessageTypeWeHandle());
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
		ChatMessageHandler handler = new ChatMessageHandler();
		ChatMessage chat = new ChatMessage(42, 0, "message", new Position(1, 1),
				ChatType.Local);
		handler.process(chat);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		CommandChatMessageReceived cmd = (CommandChatMessageReceived) ClientModelFacade.getSingleton().getNextCommand();
		assertEquals(42,cmd.getSenderID());
		assertEquals(0, cmd.getReceiverID());
		assertEquals("message",cmd.getChatText());
		assertEquals(new Position(1,1), cmd.getLocation());
		assertEquals(ChatType.Local, cmd.getType());
		ClientModelFacade.getSingleton().emptyQueue();
	}
}
