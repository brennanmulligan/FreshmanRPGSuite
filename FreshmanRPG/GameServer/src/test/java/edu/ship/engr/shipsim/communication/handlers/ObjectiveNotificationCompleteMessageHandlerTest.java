package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ObjectiveNotificationCompleteMessage;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.model.PlayerManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for ObjectiveNotificationCompleteMessageHandler
 *
 * @author Ryan
 */
public class ObjectiveNotificationCompleteMessageHandlerTest extends ServerSideTest
{

    /**
     * Reset the PlayerManager
     */
    @Before
    public void reset()
    {
        PlayerManager.resetSingleton();
        ModelFacade.resetSingleton();
        OptionsManager.resetSingleton();
        OptionsManager.getSingleton().setTestMode(true);
    }

    /**
     * Test what message type we handle
     */
    @Test
    public void testMessageWeHandle()
    {
        ObjectiveNotificationCompleteMessageHandler h = new ObjectiveNotificationCompleteMessageHandler();

        assertEquals(ObjectiveNotificationCompleteMessage.class, h.getMessageTypeWeHandle());
    }
}
