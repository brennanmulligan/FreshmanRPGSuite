package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ChatMessageToServer;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing the ChatMessageHandler
 *
 * @author Josh
 */
@GameTest("GameServer")
@ResetModelFacade
@ResetReportObserverConnector
public class ChatMessageToServerHandlerTest
{
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
