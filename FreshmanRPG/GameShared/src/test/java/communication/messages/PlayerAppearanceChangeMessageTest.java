package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.PlayersForTest;

/**
 * Checks to ensure that the correct message is thrown
 * @author Scott Bowling
 */
public class PlayerAppearanceChangeMessageTest
{

	/**
	 * Tests that the correct message was thrown with the correct fields filled in
	 */
	@Test
	public void testToString()
	{
		PlayerAppearanceChangeMessage msg = new PlayerAppearanceChangeMessage(2, PlayersForTest.MERLIN.getAppearanceType());
		assertEquals("Appearance Change: Appearance changed to: merlin", msg.toString());

	}

}
