package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the basic ClientPlayerObjective class and its functionality.
 *
 * @author Nathaniel
 */
public class ClientPlayerObjectiveTest
{
    /**
     * Test the initialization of ClientPlayerObjective
     */
    @Test
    public void testClientPlayerObjectiveInitializaiton()
    {
        ClientPlayerObjectiveStateDTO a = new ClientPlayerObjectiveStateDTO(1, "Test Objective", 3, ObjectiveStateEnum.HIDDEN, false,
                true, "Dept chair", QuestStateEnum.AVAILABLE);
        assertEquals(1, a.getObjectiveID());
        assertEquals("Test Objective", a.getObjectiveDescription());
        assertEquals(3, a.getObjectiveXP());
        assertEquals(ObjectiveStateEnum.HIDDEN, a.getObjectiveState());
        assertTrue(a.isRealLifeObjective());
        assertEquals("Dept chair", a.getWitnessTitle());
    }
}
