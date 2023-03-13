package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests a player disconnect message
 *
 * @author nhydock
 */
@GameTest("GameShared")
public class PlayerLeaveMessageTest
{
    /**
     * Make sure its toString reporting is correct
     */
    @Test
    public void testToString()
    {
        PlayerLeaveMessage msg = new PlayerLeaveMessage(1, false);
        assertEquals("PlayerLeaveMessage: playerID = 1", msg.toString());
    }

}
