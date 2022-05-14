package communication.packers;

import static org.junit.Assert.assertEquals;

import communication.messages.ChatMessageToClient;
import communication.messages.ChatMessageToServer;
import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;
import model.reports.ChatSentReport;

/**
 * @author Dave
 *
 * Make sure that the ChatMessagePacker behaves properly.
 */
public class ChatMessageToServerPackerTest
{
	
	/**
	 * Make sure that the report is properly translated into the message.
	 */
	@Test
	public void testPacking()
	{
		int sender = 16;
		int receiver = 0;
		String text = "Hello world";
		Position loc = new Position(0,0);
		ChatType type = ChatType.Local;
		
		ChatSentReport report = new ChatSentReport(sender, receiver, text, loc, type);
		ClientChatMessagePacker packer = new ClientChatMessagePacker();
		ChatMessageToServer msg = (ChatMessageToServer) packer.pack(report);
		
		assertEquals(sender, msg.getSenderID());
		assertEquals(receiver, msg.getReceiverID());
		assertEquals(text, msg.getChatText());
		assertEquals(loc, msg.getPosition());
		assertEquals(type, msg.getType());
	}

}
