package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;

/**
 * @author Josh
 *
 */
public class ChatMessageReceivedCommandTest
{

	/**
	 * Making sure that the CommandChatMessageReceived has the correct
	 * information
	 */
	@Test
	public void testCreateTheReportWithCorrectMessage()
	{
		String text = "Hello";
		int playerID = 42;
		int receiverID = 0;
		Position location = new Position(1, 1);
		ChatType type = ChatType.Local;

		CommandChatMessageReceived ccmr = new CommandChatMessageReceived(playerID, receiverID, text, location, type);

		assertEquals(text, ccmr.getChatText());
		assertEquals(playerID, ccmr.getSenderID());
		assertEquals(receiverID, ccmr.getReceiverID());
		assertEquals(location, ccmr.getLocation());
		assertEquals(type, ccmr.getType());
	}
}