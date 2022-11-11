package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.communication.ConnectionManager;
import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.handlers.MessageHandlerSet;
import edu.ship.engr.shipsim.communication.packers.MessagePackerSet;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.InteractObjectManager;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.restfulcommunication.RestfulServer;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A daemon that resides on the server listening to the gigabuds and to client
 * requests
 *
 * @author Merlin
 */
public class Server implements Runnable, AutoCloseable
{
    private final int port;
    private final String mapName;
    private final boolean runningLocal;
    private final String dbIdentifier;
    private final boolean restfulServer;
    private ServerSocket servSock;

    /**
     * Create a new Server listening on a given port
     *
     * @param map  The map that this server will serve
     * @param port The port to listen on
     */
    public Server(String map, int port)
    {
        this(map, port, false, null, false);
    }

    /**
     * Create a new Server listening on a given port
     *
     * @param mapName      The map that this server will serve
     * @param port         The port to listen on
     * @param runningLocal true if we are running with a host name of localhost
     * @param dbIdentifier if we are running local, this will be the number of the test db we should use
     * @param restfulServer whether or not this server should be instanced as a restful area server
     */
    public Server(String mapName, int port, boolean runningLocal, String dbIdentifier, boolean restfulServer)
    {
        this.mapName = mapName;
        this.port = port;
        this.runningLocal = runningLocal;
        this.dbIdentifier = dbIdentifier;
        this.restfulServer = restfulServer;
    }

    /**
     * Run like java -jar server.jar --port=1000 map=recCenter
     *
     * @param args Main runner
     * @throws IllegalArgumentException Thrown when the port is not given as an argument to the
     *                                  execution
     */
    public static void main(String[] args) throws Exception
    {
        String map = null;
        Integer port = null;
        boolean runningLocal = false;
        String dbIdentifier = null;
        boolean restfulServer = false;

        for (String arg : args)
        {
            String[] splitArg = arg.split("=");
            //noinspection IfCanBeSwitch
            if (splitArg[0].equals("--port"))
            {
                port = Integer.parseInt(splitArg[1]);
            }
            else if (splitArg[0].equals("--map"))
            {
                map = splitArg[1];
            }
            else if (splitArg[0].equals("--localhost"))
            {
                runningLocal = true;
            }
            else if (splitArg[0].equals("--db"))
            {
                dbIdentifier = splitArg[1];
            }
            else if (splitArg[0].equals("--production"))
            {
                OptionsManager.getSingleton().setUsingTestDB(false);
            }
            else if (splitArg[0].equals("--restfulServer"))
            {
                restfulServer = true;
                map = "RestfulMap.tmx";
                port = 1890;
            }
        }
        if (map == null)
        {
            throw new IllegalArgumentException(
                    "Map name is required to run the server. Use the --map=STRING option.");
        }
        else if (port == null && !OptionsManager.getSingleton().isTestMode())
        {
            throw new IllegalArgumentException(
                    "Port is required to run the server. Use the --port=INTEGER option.");
        }

        //noinspection ConstantConditions
        try (Server server = new Server(map, port, runningLocal, dbIdentifier, restfulServer))
        {
            server.run();
        }
    }

    @Override
    public void close() throws Exception
    {
        try
        {
            servSock.close();
        }
        catch (IOException e)
        {
            System.err.println("Could not close socket");
            System.exit(-1);
        }
    }

    /**
     * @return dbIdentifier
     */
    public String getDbIdentifier()
    {
        return dbIdentifier;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        int i = 0;
        try
        {
            OptionsManager om = OptionsManager.getSingleton();
            om.setDbIdentifier(dbIdentifier);
            LoggerManager.createLogger("" + mapName);
            String hostName;
            if (!runningLocal)
            {
                hostName = OptionsManager.getSingleton().getProductionHostName();
            }
            else
            {
                hostName = "localhost";
            }
            om.updateMapInformation(mapName, hostName, port);
            LoggerManager.getSingleton().getLogger().info("My map name is " +
                    OptionsManager.getSingleton().getMapFileTitle());
            PlayerManager.getSingleton().loadNpcs(false);
            InteractObjectManager.getSingleton();
            servSock = new ServerSocket(OptionsManager.getSingleton().getPortNumber(), 10);

            if (restfulServer)
            {
                SpringApplication application = new SpringApplication(RestfulServer.class);
                application.setBannerMode(Banner.Mode.OFF);
                application.run();
            }

            //noinspection InfiniteLoopStatement
            while (true)
            {
                Socket sock = servSock.accept();
                i++;
                MessagePackerSet messagePackerSet = new MessagePackerSet();
                StateAccumulator stateAccumulator =
                        new StateAccumulator(messagePackerSet);

                new ConnectionManager(sock, stateAccumulator,
                        new MessageHandlerSet(stateAccumulator), messagePackerSet, true);
            }

        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

}
