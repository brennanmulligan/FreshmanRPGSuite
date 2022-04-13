package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.PlayersForTest;

/**
 * Tests a login message
 *
 * @author merlin
 *
 */
public class PlayerJoinedMessageTest
{
	/**
	 * Make sure the object is built and its toString is correct
	 */
	@Test
	public void testToStringAndConstructor()
	{
		PlayerJoinedMessage msg = new PlayerJoinedMessage(2, PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getAppearanceType(), PlayersForTest.MERLIN.getPosition(),
				PlayersForTest.MERLIN.getCrew(), PlayersForTest.MERLIN.getMajor(), PlayersForTest.MERLIN.getSection());
		assertEquals("PlayerJoined Message: playerName = Merlin", msg.toString());
		assertEquals(2, msg.getPlayerID());
		assertEquals("Merlin", msg.getPlayerName());
		assertEquals(PlayersForTest.MERLIN.getAppearanceType(), msg.getAppearanceType());
		assertEquals(PlayersForTest.MERLIN.getPosition(), msg.getPosition());
		assertEquals(PlayersForTest.MERLIN.getCrew(), msg.getCrew());
		assertEquals(PlayersForTest.MERLIN.getMajor(), msg.getMajor());
		assertEquals(PlayersForTest.MERLIN.getSection(), msg.getSection());
	}

}
