package communication.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.ClientModelFacade;


/**
 * @author Evan
 *
 */
public class TimeToLevelUpDeadlineHandlerTest
{

    /**
     * Resets singleton
     */
    @Before
    public void reset()
    {
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, false);
    }

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
