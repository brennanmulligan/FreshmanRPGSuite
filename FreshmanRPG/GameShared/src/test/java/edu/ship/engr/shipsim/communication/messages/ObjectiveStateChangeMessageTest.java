package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ryan
 */
@GameTest("GameShared")
public class ObjectiveStateChangeMessageTest
{

    /**
     * Test initialization of message
     */
    @Test
    public void testInit()
    {
        ObjectiveStateChangeMessage msg = new ObjectiveStateChangeMessage(1, false, 2, 3,  "Big Objective",
                ObjectiveStateEnum.TRIGGERED, true, "Provost");

        assertEquals(1, msg.getRelevantPlayerID());
        assertEquals(2, msg.getQuestID());
        assertEquals(3, msg.getObjectiveID());
        assertEquals("Big Objective", msg.getObjectiveDescription());
        assertEquals(ObjectiveStateEnum.TRIGGERED, msg.getNewState());
        assertTrue(msg.isRealLifeObjective());
        assertEquals("Provost", msg.getWitnessTitle());
    }

}
