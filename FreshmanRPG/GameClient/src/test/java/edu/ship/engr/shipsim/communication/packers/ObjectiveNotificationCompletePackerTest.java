package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.model.reports.ObjectiveNotificationCompleteReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for ObjectiveNotificationCompletePacker
 *
 * @author Ryan
 */
@GameTest("GameClient")
public class ObjectiveNotificationCompletePackerTest
{

    /**
     * Test report type we pack
     */
    @Test
    public void testReportTypeWePack()
    {
        ObjectiveNotificationCompletePacker packer = new ObjectiveNotificationCompletePacker();

        assertTrue(packer.getReportTypesWePack().contains(ObjectiveNotificationCompleteReport.class));
    }

}
