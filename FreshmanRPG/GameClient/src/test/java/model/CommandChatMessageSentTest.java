package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import org.easymock.EasyMock;
import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;
import model.reports.ClientPlayerMovedReport;
import datatypes.ChatTextDetails;

/**
 * @author Matthew Kujawski and Dave Abrams
 *These set of test makes sure that the CommandChatMessageSent works properly.
 */
public class CommandChatMessageSentTest 
{

	/**
	 * This test makes sure that the command can properly break a String from the UI apart into message content and message type.
	 */
	@Test
	public void testInstantiation() 
	{
		String localMessage = "This is a local message";
		CommandChatMessageSent ccms = new CommandChatMessageSent(new ChatTextDetails(localMessage, ChatType.Local));
		assertTrue(ccms.getValidity());
		assertEquals(ChatType.Local, ccms.getType());
		assertEquals("This is a local message", ccms.getMessage());
		
		String zoneMsg = "/z test";
		CommandChatMessageSent ccms2 = new CommandChatMessageSent(new ChatTextDetails(zoneMsg, ChatType.Zone));
		assertEquals(ChatType.Zone, ccms2.getType());
	}
	


}
