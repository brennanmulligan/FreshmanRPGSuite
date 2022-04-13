package communication.packers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import communication.messages.ChatMessage;
import datatypes.ChatType;
import datatypes.Position;
import model.reports.ChatMessageReceivedReport;

/**
 * @author Dave
 *
 *         Make sure that the ChatMessagePacker behaves properly.
 */
public class ChatMessagePackerTest
{

	/**
	 * Make sure that the report is properly translated into the message.
	 */
	@Test
	public void testPacking()
	{
		int sender = 42;
		int receiver = 0;
		String text = "Hello world";
		Position loc = new Position(0, 0);
		ChatType type = ChatType.Local;

		ChatMessageReceivedReport report = new ChatMessageReceivedReport(sender, receiver, text, loc, type);
		ChatMessagePacker packer = new ChatMessagePacker();
		ChatMessage msg = (ChatMessage) packer.pack(report);

		assertEquals(sender, msg.getSenderID());
		assertEquals(receiver, msg.getReceiverID());
		assertEquals(text, msg.getChatText());
		assertEquals(loc, msg.getPosition());
		assertEquals(type, msg.getType());
	}

}
