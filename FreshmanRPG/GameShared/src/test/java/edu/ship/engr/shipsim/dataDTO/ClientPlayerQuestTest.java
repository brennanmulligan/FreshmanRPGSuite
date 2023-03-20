package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the
 *
 * @author nk3668
 */
@GameTest("GameShared")
public class ClientPlayerQuestTest
{

    /**
     * Tests that a clients player will contain state, id, and a description.
     */
    @Test
    public void testClientPlayerQuestInitialization()
    {
        Date expireDate = new Date();
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 42, 133, true,
                false, expireDate);
        assertEquals(1, q.getQuestID());
        assertEquals("title", q.getQuestTitle());
        assertEquals("Test Quest 1", q.getQuestDescription());
        assertEquals(QuestStateEnum.AVAILABLE, q.getQuestState());
        assertEquals(42, q.getExperiencePointsGained());
        assertEquals(133, q.getObjectivesToFulfillment());
        assertEquals(expireDate, q.getExpireDate());
    }

    /**
     * Create a new ClientPlayerQuest with two ClientPlayerObjectives
     *
     * @return the ClientPlayerQuest
     */
    public static ClientPlayerQuestStateDTO createOneQuestWithTwoObjectives()
    {
        ClientPlayerObjectiveStateDTO objectiveOne = new ClientPlayerObjectiveStateDTO(1, "Test Objective 1", 4,
                ObjectiveStateEnum.HIDDEN, false, true, "Henry", QuestStateEnum.AVAILABLE);
        ClientPlayerObjectiveStateDTO objectiveTwo = new ClientPlayerObjectiveStateDTO(2, "Test Objective 2", 4,
                ObjectiveStateEnum.HIDDEN, false, false, null, QuestStateEnum.AVAILABLE);
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 1, 2, true,
                false, null);
        q.addObjective(objectiveOne);
        q.addObjective(objectiveTwo);
        assertEquals(2, q.getObjectiveList().size());
        ClientPlayerObjectiveStateDTO a = q.getObjectiveList().get(0);
        assertTrue(a.isRealLifeObjective());
        assertEquals("Henry", a.getWitnessTitle());
        a = q.getObjectiveList().get(1);
        assertFalse(a.isRealLifeObjective());
        assertNull(a.getWitnessTitle());
        return q;
    }

    /**
     * Create a new ClientPlayerQuest with two ClientPlayerObjectives
     *
     * @return the ClientPlayerQuest
     */
    public static ClientPlayerQuestStateDTO createOneQuestWithTwoObjectivesNeedingNotification()
    {
        ClientPlayerObjectiveStateDTO objectiveOne = new ClientPlayerObjectiveStateDTO(1, "Test Objective 1", 4,
                ObjectiveStateEnum.COMPLETED, true, true, "CEO:", QuestStateEnum.AVAILABLE);
        ClientPlayerObjectiveStateDTO objectiveTwo = new ClientPlayerObjectiveStateDTO(2, "Test Objective 2", 2,
                ObjectiveStateEnum.COMPLETED, true, false, null, QuestStateEnum.AVAILABLE);
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "title", "Test Quest 1", QuestStateEnum.COMPLETED, 1, 2, true,
                false,null);
        q.addObjective(objectiveOne);
        q.addObjective(objectiveTwo);
        assertEquals(2, q.getObjectiveList().size());
        return q;
    }

    /**
     * Tests the ability to add an objective to the quest
     */
    @Test
    public void testAddingObjectives()
    {
        ClientPlayerObjectiveStateDTO objectiveOne = new ClientPlayerObjectiveStateDTO(1, "Test Objective 1", 10,
                ObjectiveStateEnum.HIDDEN, false, false, null, QuestStateEnum.AVAILABLE);
        assertEquals(1, objectiveOne.getObjectiveID());
        ClientPlayerObjectiveStateDTO objectiveTwo = new ClientPlayerObjectiveStateDTO(2, "Test Objective 2", 7,
                ObjectiveStateEnum.HIDDEN, false, true, "Liz", QuestStateEnum.AVAILABLE);
        assertEquals(2, objectiveTwo.getObjectiveID());
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 1, 2, true,
                false, null);
        q.addObjective(objectiveOne);
        q.addObjective(objectiveTwo);
        assertEquals(2, q.getObjectiveList().size());
        assertEquals(1, q.getObjectiveList().get(0).getObjectiveID());
        assertEquals(2, q.getObjectiveList().get(1).getObjectiveID());
    }
}
