package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author nk3668 Tests the creation & functionality of the
 * ObjectivesNeedingNotificationReport class
 */
public class ObjectiveNeedingNotificationReportTest
{
    /**
     * Ensures that the report contains the correct data
     */
    @Test
    public void testInitialization()
    {

        ObjectiveNeedingNotificationReport report = new ObjectiveNeedingNotificationReport(
                1, 2, 1, "Silly Objective", ObjectiveStateEnum.COMPLETED, true,
                "El Presidente");
        assertEquals(1, report.getPlayerID());
        assertEquals(2, report.getQuestID());
        assertEquals(1, report.getObjectiveID());
        assertEquals("Silly Objective", report.getObjectiveDescription());
        assertTrue(report.isRealLifeObjective());
        assertEquals("El Presidente", report.getWitnessTitle());
    }

    /**
     * Testing the equality of two instances of this class
     */
    @Test
    public void testEqualsContract()
    {
        EqualsVerifier.forClass(ObjectiveNeedingNotificationReport.class).verify();
    }
}
