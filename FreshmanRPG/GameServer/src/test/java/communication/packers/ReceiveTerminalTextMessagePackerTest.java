package communication.packers;

import static org.junit.Assert.assertEquals;

import datatypes.PlayersForTest;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.ReceiveTerminalTextMessage;
import model.reports.ReceiveTerminalTextReport;

/**
 *
 * @author Denny Fleagle
 * @author Chris Roadcap
 *
 */
public class ReceiveTerminalTextMessagePackerTest
{
	/**
	 * Test the packing ability
	 */
	@Test
	public void test()
	{
		int playerID = PlayersForTest.MERLIN.getPlayerID();

		//Create result text
		String resultText = "unknown atm";


		//Create report and packer using the created list
		ReceiveTerminalTextReport report = new ReceiveTerminalTextReport(playerID, resultText, "");
		ReceiveTerminalTextMessagePacker packer = new ReceiveTerminalTextMessagePacker();

		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(playerID);
		packer.setAccumulator(stateAccumulator);

		//Get message from packer's pack() method, and name and map from message
		ReceiveTerminalTextMessage message = (ReceiveTerminalTextMessage) packer.pack(report);
		resultText = message.getResultText();

		//test result text is the same
		assertEquals("unknown atm", resultText);
	}

}
