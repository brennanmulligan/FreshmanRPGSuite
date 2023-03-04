package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests a login message
 *
 * @author merlin
 */
@GameTest("GameShared")
public class PlayerJoinedMessageTest
{
    /**
     * Make sure the object is built and its toString is correct
     */
    @Test
    public void testToStringAndConstructor()
    {
        PlayerJoinedMessage msg = new PlayerJoinedMessage(2, false, PlayersForTest.JOSH.getPlayerName(),
                PlayersForTest.JOSH.getVanityItems(), PlayersForTest.JOSH.getPosition(),
                PlayersForTest.JOSH.getCrew(), PlayersForTest.JOSH.getMajor(), PlayersForTest.JOSH.getSection());
        assertEquals("PlayerJoined Message: playerName = Josh", msg.toString());
        assertEquals(2, msg.getRelevantPlayerID());
        assertEquals("Josh", msg.getPlayerName());
        assertEquals(PlayersForTest.JOSH.getVanityItems(), msg.getVanities());
        assertEquals(PlayersForTest.JOSH.getPosition(), msg.getPosition());
        assertEquals(PlayersForTest.JOSH.getCrew(), msg.getCrew());
        assertEquals(PlayersForTest.JOSH.getMajor(), msg.getMajor());
        assertEquals(PlayersForTest.JOSH.getSection(), msg.getSection());
    }

}
