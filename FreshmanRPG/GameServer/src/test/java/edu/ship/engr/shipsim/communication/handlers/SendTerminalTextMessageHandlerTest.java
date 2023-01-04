package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.SendTerminalTextMessage;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Denny Fleagle
 * @author Ben Lehman
 * @author Austin Smale
 */
@GameTest("GameServer")
@ResetModelFacade
@ResetReportObserverConnector
public class SendTerminalTextMessageHandlerTest
{
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
