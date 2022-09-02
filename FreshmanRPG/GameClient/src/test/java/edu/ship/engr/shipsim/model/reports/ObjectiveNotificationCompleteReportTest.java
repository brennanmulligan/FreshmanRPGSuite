package edu.ship.engr.shipsim.model.reports;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for ObjectiveNotifcationCompleteReport
 *
 * @author Ryan
 */
public class ObjectiveNotificationCompleteReportTest
{

    /**
     * Test init and getters for report
     */
    @Test
    public void testInitialization()
    {
        ObjectiveNotificationCompleteReport report = new ObjectiveNotificationCompleteReport(1, 2, 3);

        assertEquals(1, report.getPlayerID());
        assertEquals(2, report.getQuestID());
        assertEquals(3, report.getObjectiveID());
    }

    /**
     * Testing the equality of two instances of this class
     */
    @Test
    public void testEqualsContract()
    {
        EqualsVerifier.forClass(ObjectiveNotificationCompleteReport.class).verify();
    }
}
