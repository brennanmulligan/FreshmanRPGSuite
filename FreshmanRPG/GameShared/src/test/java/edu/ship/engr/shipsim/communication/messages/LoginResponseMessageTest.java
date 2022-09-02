package edu.ship.engr.shipsim.communication.messages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Sent from the server to the client upon successful login to the system
 *
 * @author Merlin
 */
public class LoginResponseMessageTest
{

    /**
     * Just make sure it holds the right stuff
     */
    @Test
    public void basic()
    {
        LoginSuccessfulMessage msg = new LoginSuccessfulMessage(42, "localhost", 1872, 12345);
        assertEquals(42, msg.getPlayerID());
        assertEquals("localhost", msg.getHostName());
        assertEquals(1872, msg.getPortNumber());
        assertEquals(12345, msg.getPin(), 0.0001);
        assertEquals("Successful login of player " + msg.getPlayerID(), msg.toString());
    }

}
