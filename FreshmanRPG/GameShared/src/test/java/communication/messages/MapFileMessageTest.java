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
		MapFileMessage msg = new MapFileMessage("theGreen.tmx");
		assertEquals("theGreen.tmx", msg.toString());
		assertEquals("theGreen.tmx", msg.getMapFileName());
	}

}
