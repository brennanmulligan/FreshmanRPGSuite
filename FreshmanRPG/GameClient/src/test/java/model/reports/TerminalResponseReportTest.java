package model.reports;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Nate and Ben
 *
 */
public class TerminalResponseReportTest
{

	/**
	 * test creation of the TerminalResponseReport
	 */
	@Test
	public void testCreation()
	{
		int playerID = 1;
		String terminalText = "text";
		
		TerminalResponseReport report = new TerminalResponseReport(playerID, terminalText);
		assertEquals(playerID, report.getPlayerID());
		assertEquals(terminalText, report.getTerminalResult());
	}

}
