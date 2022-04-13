package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests functionality for a key input message
 *
 * @author Ian Keefer & TJ Renninger
 *
 */
public class KeyInputMessageTest
{

	/**
	 * Test creation of a key input message
	 */
	@Test
	public void testInitialization()
	{
		String input = "q";
		KeyInputMessage keyInputMessage = new KeyInputMessage(input);
		assertEquals(input, keyInputMessage.getInput());
	}

}
