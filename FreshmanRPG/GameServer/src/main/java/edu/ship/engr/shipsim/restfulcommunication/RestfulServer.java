package edu.ship.engr.shipsim.restfulcommunication;

import edu.ship.engr.shipsim.communication.ConnectionManager;
import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.handlers.MessageHandlerSet;
import edu.ship.engr.shipsim.communication.packers.MessagePackerSet;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.OptionsManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Derek
 */
@SpringBootApplication
public class RestfulServer
{
    public static AutoClosingConnectionManager createConnectionToLoginServer() throws IOException
    {
        String host = OptionsManager.getSingleton().getLoginHost();

        LoggerManager.getSingleton().getLogger().info("[RestfulServer] Connecting to login server at " + host);

        Socket socket = new Socket(host, 1871);
        MessagePackerSet messagePackerSet = new MessagePackerSet();
        StateAccumulator stateAccumulator = new StateAccumulator(messagePackerSet);

        return new AutoClosingConnectionManager(socket, stateAccumulator, new MessageHandlerSet(stateAccumulator), messagePackerSet, false);
    }

    public static class AutoClosingConnectionManager extends ConnectionManager implements AutoCloseable
    {
        public AutoClosingConnectionManager(Socket socket, StateAccumulator stateAccumulator,
                                            MessageHandlerSet messageHandlerSet, MessagePackerSet messagePackerSet,
                                            boolean b) throws IOException
        {
            super(socket, stateAccumulator, messageHandlerSet, messagePackerSet, b);
        }

        @Override
        public void close() throws Exception
        {
            disconnect();
        }
    }
}
