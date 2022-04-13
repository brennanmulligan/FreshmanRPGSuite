package communication.packers;

import static org.junit.Assert.*;

import org.junit.Test;

import communication.messages.SendTerminalTextMessage;
import model.ClientPlayerManager;
import model.reports.SendTerminalTextReport;

/**
 * @author bl5922 and np5756
 *
 */
public class SendTerminalTextPackerTest
{

	/**
	 * Tests the SendTerminalTextPacker by creating a report and making a message by packing the report
	 */
	@Test
	public void testingOnlinePlayerPackerToHaveID()
	{
		int playerID = ClientPlayerManager.getSingleton().getThisClientsPlayer().getID();
		String terminalText = "who";
		SendTerminalTextReport cpr = new SendTerminalTextReport(playerID, terminalText);
		
		SendTerminalTextPacker packer = new SendTerminalTextPacker();
		SendTerminalTextMessage msg = (SendTerminalTextMessage) packer.pack(cpr);
		assertEquals(playerID, msg.getPlayerID());
		assertEquals(terminalText, msg.getTerminalText());

		
	}

}
