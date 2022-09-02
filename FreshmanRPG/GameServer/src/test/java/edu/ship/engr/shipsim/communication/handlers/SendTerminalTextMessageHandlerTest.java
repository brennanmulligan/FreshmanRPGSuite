package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.SendTerminalTextMessage;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.QualifiedObservableConnector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Denny Fleagle
 * @author Ben Lehman
 * @author Austin Smale
 */
public class SendTerminalTextMessageHandlerTest extends ServerSideTest
{
    /**
     * Reset the ModelFacade
     */
    @Before
    public void reset()
    {
        ModelFacade.resetSingleton();
        QualifiedObservableConnector.resetSingleton();
    }

    /**
     * Tests that getTypeWeHandle method returns correct type.
     */
    @Test
    public void testTypeWeHandle()
    {
        SendTerminalTextMessageHandler h = new SendTerminalTextMessageHandler();
        assertEquals(SendTerminalTextMessage.class, h.getMessageTypeWeHandle());
    }

}
