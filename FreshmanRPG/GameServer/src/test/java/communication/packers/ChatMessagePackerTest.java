package communication.packers;

import static org.junit.Assert.assertEquals;

import communication.messages.ChatMessageToClient;
import datasource.ServerSideTest;
import model.reports.ChatMessageToClientReport;
import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;

/**
 * @author Dave
 *
 *         Make sure that the ChatMessagePacker behaves properly.
 */
public class ChatMessagePackerTest extends ServerSideTest
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

		ChatMessageToClientReport report = new ChatMessageToClientReport(sender, receiver,
				text, loc, type);
		ChatMessageToClientPacker packer = new ChatMessageToClientPacker();
		ChatMessageToClient msg = (ChatMessageToClient) packer.pack(report);

		assertEquals(sender, msg.getSenderID());
		assertEquals(receiver, msg.getReceiverID());
		assertEquals(text, msg.getChatText());
		assertEquals(loc, msg.getPosition());
		assertEquals(type, msg.getType());
	}

}
