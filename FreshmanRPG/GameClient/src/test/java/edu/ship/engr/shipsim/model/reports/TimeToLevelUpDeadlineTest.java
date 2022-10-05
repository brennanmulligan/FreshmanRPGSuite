package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests time to level up deadline report
 *
 * @author Chris, Marty, and Evan
 */
@GameTest("GameClient")
public class TimeToLevelUpDeadlineTest
{

    /**
     * Tests of getters and setters
     */
    @Test
    public void testCreation()
    {
        Date date = new GregorianCalendar(2016, 3, 4).getTime();
        ClientTimeToLevelUpDeadlineReport report = new ClientTimeToLevelUpDeadlineReport(PlayersForTest.MARTY.getPlayerID(), new GregorianCalendar(2016, 3, 4).getTime(), "freemerchant");
        assertEquals(18, report.getPlayerID());
        assertEquals(date, report.getTimeToDeadline());
        assertEquals("freemerchant", report.getNextLevel());
    }


}
