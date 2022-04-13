package communication.packers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.reports.AdventureNotificationCompleteReport;

/**
 * Test for AdventureNotificationCompletePacker
 * @author Ryan
 *
 */
public class AdventureNotificationCompletePackerTest
{

	/**
	 * Test report type we pack
	 */
	@Test
	public void testReportTypeWePack()
	{
		AdventureNotificationCompletePacker packer = new AdventureNotificationCompletePacker();

		assertTrue( packer.getReportTypesWePack().contains(AdventureNotificationCompleteReport.class) );
	}

}
