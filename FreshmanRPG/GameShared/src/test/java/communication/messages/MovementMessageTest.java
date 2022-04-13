package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.Position;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Tests a login message
 *
 * @author merlin
 *
 */
public class MovementMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testToString()
	{
		Position position = new Position(42, 13);
		PlayerMovedMessage msg = new PlayerMovedMessage(1, position);
		assertEquals(1, msg.getPlayerID());
		assertEquals(position, msg.getPosition());
		assertEquals("Movement Message: playerID = 1, position = " + position.toString(), msg.toString());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(PlayerMovedMessage.class).verify();
	}

}
