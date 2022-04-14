package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests a file message which is designed to carry a tmx file
 *
 * @author merlin
 *
 */
public class MapFileMessageTest
{
	/**
	 * Make sure its toString is correct
	 *
	 * @throws Exception shouldn't
	 */
	@Test
	public void testToString() throws Exception
	{
		MapFileMessage msg = new MapFileMessage("quad.tmx");
		assertEquals("quad.tmx", msg.toString());
		assertEquals("quad.tmx", msg.getMapFileName());
	}

}
