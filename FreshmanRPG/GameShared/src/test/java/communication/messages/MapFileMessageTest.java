package communication.messages;

import org.junit.Test;

import static org.junit.Assert.*;

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
		assertTrue(msg.getFileContents().length() > 0);
	}

}
