package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that the ExperienceChangeMessage class functionality
 *
 * @author Olivia
 * @author LaVonne
 */
@GameTest("GameShared")
public class ExperienceChangedMessageTest
{

    /**
     * Tests that we can create ExperienceChangeMessage and sets its fields
     */
    @Test
    public void testCreateMessage()
    {
        LevelRecord record = new LevelRecord("Serf", 15, 10, 7);
        ExperienceChangedMessage msg = new ExperienceChangedMessage(PlayersForTest.JOHN.getPlayerID(),false,
                PlayersForTest.JOHN.getExperiencePoints(), record);
        assertEquals(PlayersForTest.JOHN.getExperiencePoints(), msg.getExperiencePoints());
    }
}
