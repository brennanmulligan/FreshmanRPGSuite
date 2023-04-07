package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for ObjectiveNotifcationCompleteReport
 *
 * @author Ryan
 */
@GameTest("GameClient")
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
        EqualsVerifier.forClass(ObjectiveNotificationCompleteReport.class).withRedefinedSuperclass().verify();
    }
}
