package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.Position;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test the equality of the ChangeMapReport
 * 
 * @author Steve
 *
 */
public class ChangeMapReportTest
{

	/**
	 * Basic test of getters and constructing
	 */
	@Test
	public void creation()
	{
		ChangeMapReport report = new ChangeMapReport(1,new Position(0,0), "destination");
		assertEquals(new Position(0, 0), report.getPosition());
		assertEquals("destination", report.getMapName());
	}
	
	/**
	 * Testing the equality of two instances of this class
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(ChangeMapReport.class).verify();
	}
}
