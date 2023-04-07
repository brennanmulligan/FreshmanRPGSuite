package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.Watchdog;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PingWatchdogMessage;

public class PingWatchdogMessageHandler extends MessageHandler
{
    @Override
    public void process(Message msg)
    {
        PingWatchdogMessage pingMessage = (PingWatchdogMessage) msg;

        Watchdog.getSingleton().addServer(pingMessage.getHostName());
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return PingWatchdogMessage.class;
    }
}
