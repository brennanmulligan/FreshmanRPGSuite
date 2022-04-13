package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * @author Dave
 *
 *         Make sure that the SendChatMessageReport behaves properly.
 */
public class SendChatMessageReportTest
{
	/**
	 * The report should correctly remember the information it was given.
	 */
	@Test
	public void testInit()
	{
		String msg = "Test message";
		int senderID = 42;
		Position pos = new Position(0, 1);
		ChatType type = ChatType.Local;

		ChatMessageReceivedReport report = new ChatMessageReceivedReport(senderID, 0, msg, pos, type);

		assertEquals(msg, report.getChatText());
		assertEquals(senderID, report.getSenderID());
		assertEquals(pos, report.getPosition());
		assertEquals(type, report.getType());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(ChatMessageReceivedReport.class).verify();
	}
}
