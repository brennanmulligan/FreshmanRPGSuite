package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;
import datatypes.PlayersForTest;

/**
 * Tests a ChatMessage
 *
 * @author Andrew
 *
 */
public class ChatMessageTest
{

	/**
	 * Test initialization of a chat message
	 */
	@Test
	public void test()
	{
		Position p = new Position(0, 0);
		ChatMessage msg = new ChatMessage(PlayersForTest.MERLIN.getPlayerID(), 0, "Hello World!", p, ChatType.Local);
		assertEquals(PlayersForTest.MERLIN.getPlayerID(), msg.getSenderID());
		assertEquals(0, msg.getReceiverID());
		assertEquals("Hello World!", msg.getChatText());
		assertEquals(ChatType.Local, msg.getType());
		assertEquals(p, msg.getPosition());
	}

}
