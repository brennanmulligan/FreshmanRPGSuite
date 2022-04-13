package communication.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import datatypes.ServersForTest;

/**
 * Tests a login message
 *
 * @author merlin
 *
 */
public class TeleportationContinuationMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testToStringAndGetters()
	{
		TeleportationContinuationMessage msg = new TeleportationContinuationMessage(
				ServersForTest.FIRST_SERVER.getMapName(), ServersForTest.FIRST_SERVER.getHostName(),
				ServersForTest.FIRST_SERVER.getPortNumber(), 2, 4);
		assertTrue(msg.toString()
				.startsWith("TeleportationContinuationMessage: mapName = " + ServersForTest.FIRST_SERVER.getMapName()
						+ " and hostName = " + ServersForTest.FIRST_SERVER.getHostName() + " and portNumber = "
						+ ServersForTest.FIRST_SERVER.getPortNumber()));
		assertEquals(ServersForTest.FIRST_SERVER.getMapName(), msg.getMapName());
		assertEquals(ServersForTest.FIRST_SERVER.getHostName(), msg.getHostName());
		assertEquals(ServersForTest.FIRST_SERVER.getPortNumber(), msg.getPortNumber());
		assertEquals(2, msg.getPlayerID());
		assertEquals(4, msg.getPin());
	}

}
