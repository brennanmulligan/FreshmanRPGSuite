package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;

/**
 * @author Dave
 * 
 * Make sure that the ChatSentReport behaves properly.
 */
public class ChatSentReportTest
{
	/**
	 * Make sure that the report properly holds the information it was given.
	 */
	@Test
	public void testInit()
	{
		String text = "Hello world";
		int name = 16;
		int receiver = 0;
		Position loc = new Position(1,1);
		ChatType type = ChatType.Local;
		
		ChatSentReport report = new ChatSentReport(name, receiver, text, loc, type);
		
		assertEquals(text, report.getMessage());
		assertEquals(name, report.getSenderID());
		assertEquals(receiver, report.getReceiverID());
		assertEquals(loc, report.getPosition());
		assertEquals(type, report.getType());
	}
}