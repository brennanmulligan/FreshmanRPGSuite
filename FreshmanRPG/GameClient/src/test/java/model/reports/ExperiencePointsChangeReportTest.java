package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datasource.LevelRecord;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * @author nk3668
 *
 */
public class ExperiencePointsChangeReportTest 
{

	/**
	 * Basic Initialization of the report containing the experience points 
	 * and the level record.
	 */
	@Test
	public void testReportInitialization() 
	{
		int exp = 1000;
		LevelRecord rec = new LevelRecord("Master Exploder", 100, 10, 7);
		ExperiencePointsChangeReport report = new ExperiencePointsChangeReport(exp, rec);
	
		assertEquals(exp, report.getExperiencePoints());
		assertEquals(rec, report.getLevelRecord());
	}
	
	/**
	 * Testing the equality of two instances of this class
	 */
	@Test
	public void testEqualsContract()
	{
		EqualsVerifier.forClass(DoubloonChangeReport.class).verify();
	}
}
