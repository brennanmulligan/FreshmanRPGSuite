package edu.ship.engr.shipsim;

import com.google.common.collect.Maps;
import edu.ship.engr.shipsim.communication.ConnectionManager;
import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.handlers.MessageHandlerSet;
import edu.ship.engr.shipsim.communication.packers.MessagePackerSet;
import edu.ship.engr.shipsim.model.OptionsManager;
import org.jetbrains.annotations.TestOnly;

import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.Map;

public class Watchdog implements Runnable
{
    private final Map<String, Long> serverMap = Maps.newConcurrentMap();
    public static final int TIMEOUT_DURATION = 60000;

    private static final Watchdog singleton = new Watchdog();
    public static Watchdog getSingleton()
    {
        return singleton;
    }

    public static void main(String[] args)
    {
        for (String arg : args)
        {
            String[] splitArg = arg.split("=");

            if (splitArg[0].equals("--localhost"))
            {
                OptionsManager.getSingleton().setHostName("localhost");
            }
            else if (splitArg[0].equals("--production"))
            {
                OptionsManager.getSingleton().setUsingTestDB(false);
            }
        }

        Watchdog watchdog = new Watchdog();
        watchdog.run();
    }

    public void addServer(String hostName)
    {
        serverMap.put(hostName, System.currentTimeMillis());
    }

    /**
     * Used for testing only
     */
    @TestOnly
    public void addServer(String hostName, long time)
    {
        serverMap.put(hostName, time);
    }

    @TestOnly
    public Map<String, Long> getServerMap()
    {
        return serverMap;
    }

    public static Map<String, Long> getDeadServers()
    {
        Map<String, Long> deadServers = Maps.newHashMap();

        for (Map.Entry<String, Long> entry : getSingleton().serverMap.entrySet())
        {
            if (Instant.now().toEpochMilli() - entry.getValue() > TIMEOUT_DURATION) // 1 minute
            {
                deadServers.put(entry.getKey(), entry.getValue());
            }
        }

        return deadServers;
    }

    @Override
    public void run()
    {
        try (ServerSocket servSock = new ServerSocket(1891, 10))
        {
            while (true)
            {
                Socket sock = servSock.accept();
                MessagePackerSet packers = new MessagePackerSet();
                StateAccumulator stateAccumulator = new StateAccumulator(packers);

                MessageHandlerSet handlers = new MessageHandlerSet(stateAccumulator);
                new ConnectionManager(sock, stateAccumulator, handlers, packers, true);
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
