package model.reports;

import static org.junit.Assert.assertEquals;

import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import datatypes.Position;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * @author Merlin
 *
 */
public class PlayerMovedReportTest
{

	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		ClientPlayerMovedReport report = new ClientPlayerMovedReport(1, new Position(3, 2), true);
		assertEquals(new Position(3, 2), report.getNewPosition());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(ClientPlayerMovedReport.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}
}
