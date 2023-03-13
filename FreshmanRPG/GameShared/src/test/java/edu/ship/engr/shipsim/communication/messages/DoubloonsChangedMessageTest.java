package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Matthew Croft
 * @author Emily Maust
 */
@GameTest("GameShared")
public class DoubloonsChangedMessageTest
{
    /**
     * Tests that we can create ExperienceChangeMessage and sets its fields
     */
    @Test
    public void testCreateMessage()
    {
        DoubloonsChangedMessage msg = new DoubloonsChangedMessage(PlayersForTest.JOHN.getPlayerID(), false,
                PlayersForTest.JOHN.getDoubloons(), PlayersForTest.JOHN.getBuffPool());
        assertEquals(PlayersForTest.JOHN.getDoubloons(), msg.getDoubloons());
        assertEquals(PlayersForTest.JOHN.getBuffPool(), msg.getBuffPool());
    }
}
