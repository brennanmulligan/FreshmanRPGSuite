package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ChatMessageToServer;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.QualifiedObservableConnector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing the ChatMessageHandler
 *
 * @author Josh
 */

public class ChatMessageToServerHandlerTest extends ServerSideTest
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
        ChatMessageToServerHandler h = new ChatMessageToServerHandler();
        assertEquals(ChatMessageToServer.class, h.getMessageTypeWeHandle());
    }

}
