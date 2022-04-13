package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests user key input report
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputSentReportTest
{

	/**
	 * Tests creation of a KeyInputSentReport
	 */
	@Test
	public void testInitialization()
	{
		String input = "q";
		ClientKeyInputSentReport report = new ClientKeyInputSentReport(input);
		assertEquals(input, report.getInput());
	}

}
