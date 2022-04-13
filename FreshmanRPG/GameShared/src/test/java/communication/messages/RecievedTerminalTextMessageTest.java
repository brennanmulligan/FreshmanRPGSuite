package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.PlayersForTest;

/**
 *
 * @author Denny Fleagle
 * @author Chris Roadcap
 * @author Ben Lehman
 *
 */
public class RecievedTerminalTextMessageTest
{
	/**
	 * Test the message gets created correctly
	 */
	@Test
	public void testCreation()
	{
		//create string for terminal
		String result = "unknown";

		//Create message
		ReceiveTerminalTextMessage message = new ReceiveTerminalTextMessage(PlayersForTest.ANDY.getPlayerID(), result);

		assertEquals(PlayersForTest.ANDY.getPlayerID(), message.getRequestingPlayerID());

		assertEquals(result, message.getResultText());
	}

}
