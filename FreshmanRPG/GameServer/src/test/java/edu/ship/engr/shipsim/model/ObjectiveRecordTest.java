package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.CriteriaStringDTO;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests a simple data transfer object that contains the information about one
 * objective
 *
 * @author merlin
 */
@GameTest("GameServer")
public class ObjectiveRecordTest
{
    /**
     * Just make sure it holds and returns everything
     */
    @Test
    public void constructAnObjectiveRecord()
    {
        ObjectiveRecord record = new ObjectiveRecord(1, 1, "Objective Description 1", 42, ObjectiveCompletionType.CHAT,
                new CriteriaStringDTO("Lab Instructor"));
        assertEquals(1, record.getObjectiveID());
        assertEquals("Objective Description 1", record.getObjectiveDescription());
        assertEquals(1, record.getQuestID());
        assertEquals(42, record.getExperiencePointsGained());
        assertEquals(ObjectiveCompletionType.CHAT, record.getCompletionType());
        assertEquals(new CriteriaStringDTO("Lab Instructor"), record.getCompletionCriteria());
        assertFalse(record.isRealLifeObjective());

    }

    /**
     * Correctly calculates whether it is a real life objective
     */
    @Test
    public void detectsRealLife()
    {
        ObjectiveRecord record = new ObjectiveRecord(1, 1, "Objective Description 1", 42,
                ObjectiveCompletionType.REAL_LIFE, new CriteriaStringDTO("Lab Instructor"));
        assertTrue(record.isRealLifeObjective());
        record = new ObjectiveRecord(1, 1, "Objective Description 1", 42, ObjectiveCompletionType.CHAT,
                new CriteriaStringDTO("Lab Instructor"));
        assertFalse(record.isRealLifeObjective());
    }
}
