package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests that the ExperienceChangeMessage class functionality
 *
 * @author Olivia
 * @author LaVonne
 */
public class ExperienceChangedMessageTest
{

    /**
     * Tests that we can create ExperienceChangeMessage and sets its fields
     */
    @Test
    public void testCreateMessage()
    {
        LevelRecord record = new LevelRecord("Serf", 15, 10, 7);
        ExperienceChangedMessage msg = new ExperienceChangedMessage(PlayersForTest.JOHN.getPlayerID(),
                PlayersForTest.JOHN.getExperiencePoints(), record);
        assertEquals(PlayersForTest.JOHN.getExperiencePoints(), msg.getExperiencePoints());
    }
}
