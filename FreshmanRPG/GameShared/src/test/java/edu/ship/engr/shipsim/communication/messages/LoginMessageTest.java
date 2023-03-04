package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests a login message
 *
 * @author merlin
 */
@GameTest("GameShared")
public class LoginMessageTest
{
    /**
     * Make sure its toString is correct
     */
    @Test
    public void testToString()
    {
        LoginMessage msg = new LoginMessage("fred", "xxx", false);
        assertEquals("Login Message: playerName = fred and password = xxx", msg.toString());
        assertEquals("fred", msg.getPlayerName());
        assertEquals("xxx", msg.getPassword());
    }

}
