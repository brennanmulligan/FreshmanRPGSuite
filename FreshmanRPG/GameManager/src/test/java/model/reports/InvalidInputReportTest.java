package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for InvalidInputReport
 * @author Abe Loscher and Chris Boyer
 *
 */
public class InvalidInputReportTest
{
	/**
	 * Tests initialization of the report
	 */
	@Test
	public void testMessageSaved()
	{
		ErrorReport report = new ErrorReport("Message");
		assertEquals("Message", report.getMessage());
	}
}
