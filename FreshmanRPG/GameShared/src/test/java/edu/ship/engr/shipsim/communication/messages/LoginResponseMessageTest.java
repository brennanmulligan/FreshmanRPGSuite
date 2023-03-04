package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Sent from the server to the client upon successful login to the system
 *
 * @author Merlin
 */
@GameTest("GameShared")
public class LoginResponseMessageTest
{

    /**
     * Just make sure it holds the right stuff
     */
    @Test
    public void basic()
    {
        LoginSuccessfulMessage msg = new LoginSuccessfulMessage(42, false, "localhost", 1872, 12345);
        assertEquals(42, msg.getRelevantPlayerID());
        assertEquals("localhost", msg.getHostName());
        assertEquals(1872, msg.getPortNumber());
        assertEquals(12345, msg.getPin(), 0.0001);
        assertEquals("Successful login of player " + msg.getRelevantPlayerID(), msg.toString());
    }

}
