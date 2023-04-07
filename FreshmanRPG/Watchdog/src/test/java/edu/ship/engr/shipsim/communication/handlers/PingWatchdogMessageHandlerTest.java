package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.Watchdog;
import edu.ship.engr.shipsim.communication.messages.PingWatchdogMessage;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@GameTest("Watchdog")
public class PingWatchdogMessageHandlerTest
{
    @Test
    public void testHandlesCorrectType()
    {
        PingWatchdogMessageHandler handler = new PingWatchdogMessageHandler();

        assertSame(PingWatchdogMessage.class, handler.getMessageTypeWeHandle());
    }

    @Test
    public void testGetsMessage()
    {
        PingWatchdogMessageHandler handler = new PingWatchdogMessageHandler();

        handler.process(new PingWatchdogMessage("server1"));

        assertTrue(Watchdog.getSingleton().getServerMap().containsKey("server1"));
    }
}
