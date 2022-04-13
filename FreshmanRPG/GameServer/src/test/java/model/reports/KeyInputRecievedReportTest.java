package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Ian Keefer
 * @author TJ Renninger
 *
 */
public class KeyInputRecievedReportTest
{

	/**
	 * Test to see that a key input report is created.
	 */
	@Test
	public void testInitialization()
	{
		String input = "q";
		int id = 1;
		KeyInputRecievedReport report = new KeyInputRecievedReport(input, id);
		assertEquals(input, report.getInput());
	}

}
