package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the CommandObjectiveNotificationComplete class
 *
 * @author Ryan
 */
@GameTest("GameClient")
public class CommandObjectiveNotificationCompleteTest
{

    /**
     * Tests that we can create Command and set/get its fields
     */
    @Test
    public void testCreateCommand()
    {
        int playerID = 1;
        int questID = 1;
        int objectiveID = 1;

        CommandObjectiveNotificationComplete cmd = new CommandObjectiveNotificationComplete(playerID, questID, objectiveID);
        assertEquals(playerID, cmd.getPlayerID());
        assertEquals(questID, cmd.getQuestID());
        assertEquals(objectiveID, cmd.getObjectiveID());
    }

}
