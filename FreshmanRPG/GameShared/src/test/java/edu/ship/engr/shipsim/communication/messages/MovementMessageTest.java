package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests a login message
 *
 * @author merlin
 */
@GameTest("GameShared")
public class MovementMessageTest
{
    /**
     * Make sure its toString is correct
     */
    @Test
    public void testToString()
    {
        Position position = new Position(42, 13);
        PlayerMovedMessage msg = new PlayerMovedMessage(1, false, position);
        assertEquals(1, msg.getRelevantPlayerID());
        assertEquals(position, msg.getPosition());
        assertEquals("Movement Message: relevantPlayerID = 1, position = " + position.toString(), msg.toString());
    }
}
