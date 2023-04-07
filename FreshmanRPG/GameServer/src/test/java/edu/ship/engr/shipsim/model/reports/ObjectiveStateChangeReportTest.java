package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.ObjectivesForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the report of a change state
 *
 * @author nk3668
 */
@GameTest("GameServer")
public class ObjectiveStateChangeReportTest
{

    /**
     * Tests the creation of the ObjectiveStateChangeReport
     */
    @Test
    public void testInitialization()
    {
        ObjectiveStateChangeReport report = new ObjectiveStateChangeReport(1,
                ObjectivesForTest.QUEST1_OBJECTIVE_1.getQuestID(),
                ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveID(),
                ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveDescription(), ObjectiveStateEnum.TRIGGERED, true,
                "Mom");

        assertEquals(1, report.getPlayerID());
        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getQuestID(), report.getObjectiveID());
        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveID(), report.getObjectiveID());
        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveDescription(), report.getObjectiveDescription());
        assertEquals(ObjectiveStateEnum.TRIGGERED, report.getNewState());
        assertTrue(report.isRealLifeObjective());
        assertEquals("Mom", report.getWitnessTitle());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(ObjectiveStateChangeReport.class).withRedefinedSuperclass().verify();
    }
}
