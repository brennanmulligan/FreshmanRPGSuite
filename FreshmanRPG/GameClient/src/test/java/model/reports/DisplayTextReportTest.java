package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * @author sk5587
 *
 */
public class DisplayTextReportTest
{

	/**
	 * Make sure report hold right information.
	 */
	@Test
	public void create() 
	{
		
		DisplayTextReport report = new DisplayTextReport("text");
		assertEquals("text", report.getText());
	}

}
