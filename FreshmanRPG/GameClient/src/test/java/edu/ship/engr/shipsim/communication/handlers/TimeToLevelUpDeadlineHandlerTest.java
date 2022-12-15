package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Evan
 */
@GameTest("GameClient")
@ResetClientModelFacade
public class TimeToLevelUpDeadlineHandlerTest
{
    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        TimeToLevelUpDeadlineHandler h = new TimeToLevelUpDeadlineHandler();
        assertEquals(TimeToLevelUpDeadlineHandler.class, h.getClass());
    }

}
