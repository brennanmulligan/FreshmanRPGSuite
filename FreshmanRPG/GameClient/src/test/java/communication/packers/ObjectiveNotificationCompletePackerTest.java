package communication.packers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.reports.ObjectiveNotificationCompleteReport;

/**
 * Test for ObjectiveNotificationCompletePacker
 * @author Ryan
 *
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

		assertTrue( packer.getReportTypesWePack().contains(ObjectiveNotificationCompleteReport.class) );
	}

}
