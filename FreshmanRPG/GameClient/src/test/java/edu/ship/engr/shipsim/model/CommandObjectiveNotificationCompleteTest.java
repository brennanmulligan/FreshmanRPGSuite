package edu.ship.engr.shipsim.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the CommandObjectiveNotificationComplete class
 *
 * @author Ryan
 */
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
