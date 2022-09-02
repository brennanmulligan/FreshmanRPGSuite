package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.model.reports.ObjectiveNotificationCompleteReport;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test for ObjectiveNotificationCompletePacker
 *
 * @author Ryan
 */
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
