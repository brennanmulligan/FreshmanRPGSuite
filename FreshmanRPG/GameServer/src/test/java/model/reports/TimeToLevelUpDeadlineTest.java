package model.reports;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import datatypes.PlayersForTest;

/**
 * Tests time to level up deadline report
 *
 * @author Chris, Marty, and Evan
 *
 */
public class TimeToLevelUpDeadlineTest
{

	/**
	 * Tests of getters and setters
	 */
	@Test
	public void testCreation()
	{
		Date date = new GregorianCalendar(2016, 3, 4).getTime();
		TimeToLevelUpDeadlineReport report = new TimeToLevelUpDeadlineReport(PlayersForTest.MARTY.getPlayerID(),
				new GregorianCalendar(2016, 3, 4).getTime(), "freemerchant");
		assertEquals(18, report.getPlayerID());
		assertEquals(date, report.getTimeToDeadline());
		assertEquals("freemerchant", report.getNextLevel());
	}

}
