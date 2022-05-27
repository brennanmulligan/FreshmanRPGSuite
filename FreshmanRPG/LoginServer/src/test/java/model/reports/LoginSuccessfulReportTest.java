package model.reports;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 *
 * @author Merlin
 *
 */
public class LoginSuccessfulReportTest extends ServerSideTest
{
	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		LoginSuccessfulReport report = new LoginSuccessfulReport(42, "LLL", 56, 0.76);
		assertEquals(42, report.getPlayerID());
		assertEquals("LLL", report.getHostname());
		assertEquals(56, report.getPort());
		assertEquals(0.76, report.getPin(), 0.00001);
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(LoginSuccessfulReport.class).verify();
	}
}
