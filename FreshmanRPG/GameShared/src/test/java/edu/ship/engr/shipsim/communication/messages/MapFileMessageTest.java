package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests a file message which is designed to carry a tmx file
 *
 * @author merlin
 */
@GameTest("GameShared")
public class MapFileMessageTest
{
    @BeforeAll
    public static void setup()
    {
        OptionsManager.getSingleton().setTestMode(true);
    }

    /**
     * Make sure its toString is correct
     *
     * @throws Exception shouldn't
     */
    @Test
    public void testToString() throws Exception
    {
        MapFileMessage msg = new MapFileMessage(42, false, "quad.tmx");
        assertEquals("quad.tmx", msg.toString());
        assertEquals("quad.tmx", msg.getMapFileName());
        assertTrue(msg.getFileContents().length() > 0);
    }

}
