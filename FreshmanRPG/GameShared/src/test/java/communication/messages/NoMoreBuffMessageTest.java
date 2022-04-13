package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class for testing NoMoreBuffMessage
 *
 */
public class NoMoreBuffMessageTest
{
	/**
	 * test that the tostring method of the nomorebuff message returns the correct string
	 */
	@Test
	public void testToString()
	{
		NoMoreBuffMessage message = new NoMoreBuffMessage(1);

		assertEquals("NoMoreBuffMessage: No remaining buff for playerID = 1", message.toString());
	}
}
