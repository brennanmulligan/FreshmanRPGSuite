package communication.messages;

import dataDTO.VanityDTO;
import datatypes.PlayersForTest;
import datatypes.VanityType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
		PlayerJoinedMessage msg = new PlayerJoinedMessage(2, PlayersForTest.JOSH.getPlayerName(),
				PlayersForTest.JOSH.getVanityItems(), PlayersForTest.JOSH.getPosition(),
				PlayersForTest.JOSH.getCrew(), PlayersForTest.JOSH.getMajor(), PlayersForTest.JOSH.getSection());
		assertEquals("PlayerJoined Message: playerName = Josh", msg.toString());
		assertEquals(2, msg.getPlayerID());
		assertEquals("Josh", msg.getPlayerName());
		assertEquals(PlayersForTest.JOSH.getVanityItems(), msg.getVanities());
		assertEquals(PlayersForTest.JOSH.getPosition(), msg.getPosition());
		assertEquals(PlayersForTest.JOSH.getCrew(), msg.getCrew());
		assertEquals(PlayersForTest.JOSH.getMajor(), msg.getMajor());
		assertEquals(PlayersForTest.JOSH.getSection(), msg.getSection());
	}

}
