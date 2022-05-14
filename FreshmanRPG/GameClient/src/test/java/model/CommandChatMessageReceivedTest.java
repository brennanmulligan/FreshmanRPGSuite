package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;

/**
 * @author Matthew Kujawski
 *
 */
public class CommandChatMessageReceivedTest 
{

	/**
	 * Making sure that the CommandChatMessageReceived has the correct information
	 */
	@Test
	public void testCreateTheReportWithCorrectMessage() 
	{
		String message = "Hello";
		int senderID = 42;
		int receiver = 0;
		Position location = new Position(1,1);
		ChatType type = ChatType.Local;
		
		CommandChatMessageReceivedFromServer ccmr =
				new CommandChatMessageReceivedFromServer(senderID,
				receiver, message, location, type);
		assertEquals(message, ccmr.getChatText());
		assertEquals(senderID, ccmr.getSenderID());
		assertEquals(receiver, ccmr.getReceiverID());
		assertEquals(location, ccmr.getLocation());
		assertEquals(type, ccmr.getType());
	}
}