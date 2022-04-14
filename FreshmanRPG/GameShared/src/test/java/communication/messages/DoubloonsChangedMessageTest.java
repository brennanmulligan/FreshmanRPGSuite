package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.PlayersForTest;

/**
 * @author Matthew Croft
 * @author Emily Maust
 *
 */
public class DoubloonsChangedMessageTest
{
	/**
	 * Tests that we can create ExperienceChangeMessage and sets its fields
	 */
	@Test
	public void testCreateMessage()
	{
		DoubloonsChangedMessage msg = new DoubloonsChangedMessage(PlayersForTest.JOHN.getPlayerID(),
				PlayersForTest.JOHN.getDoubloons(), PlayersForTest.JOHN.getBuffPool());
		assertEquals(PlayersForTest.JOHN.getDoubloons(), msg.getDoubloons());
		assertEquals(PlayersForTest.JOHN.getBuffPool(), msg.getBuffPool());
	}
}
