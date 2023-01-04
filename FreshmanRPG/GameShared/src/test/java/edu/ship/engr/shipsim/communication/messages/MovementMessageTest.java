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
        PlayerMovedMessage msg = new PlayerMovedMessage(1, position);
        assertEquals(1, msg.getPlayerID());
        assertEquals(position, msg.getPosition());
        assertEquals("Movement Message: playerID = 1, position = " + position.toString(), msg.toString());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(PlayerMovedMessage.class).verify();
    }

}