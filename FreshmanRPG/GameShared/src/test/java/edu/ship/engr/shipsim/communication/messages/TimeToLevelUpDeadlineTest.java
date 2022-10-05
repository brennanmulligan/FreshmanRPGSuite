package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests time to level up deadline message
 *
 * @author Chris, Marty, and Evan
 */
@GameTest("GameShared")
public class TimeToLevelUpDeadlineTest
{

    /**
     * Tests of getters and setters
     */
    @Test
    public void testCreation()
    {
        Date date = new GregorianCalendar(2016, 3, 4).getTime();
        TimeToLevelUpDeadlineMessage msg = new TimeToLevelUpDeadlineMessage(PlayersForTest.MARTY.getPlayerID(),
                new GregorianCalendar(2016, 3, 4).getTime(), "freemerchant");
        assertEquals(18, msg.getPlayerID());
        assertEquals(date, msg.getTimeToDeadline());
        assertEquals("freemerchant", msg.getNextLevel());
    }

}
