package edu.ship.shipsim.areaserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import communication.ConnectionManager;
import communication.StateAccumulator;
import communication.handlers.MessageHandlerSet;
import communication.packers.MessagePackerSet;
import datasource.LoggerManager;
import model.InteractObjectManager;
import model.OptionsManager;
import model.PlayerManager;
import model.ServerMapManager;

/**
 * A daemon that resides on the server listening to the gigabuds and to client
 * requests
 *
 * @author Merlin
 *
 */
public class Server implements Runnable
{
	private ServerSocket servSock;
	private int port;
	private String mapName;
	private boolean runningLocal;
	private String dbIdentifier;

	/**
	 * @return dbIdentifier
	 */
	public String getDbIdentifier()
	{
		return dbIdentifier;
	}

	/**
	 * Create a new Server listening on a given port
	 *
	 * @param map
	 *            The map that this server will serve
	 * @param port
	 *            The port to listen on
	 */
	public Server(String map, int port)
	{
		this(map, port, false, null);
	}

	/**
	 * Create a new Server listening on a given port
	 *
	 * @param map
	 *            The map that this server will serve
	 * @param port
	 *            The port to listen on
	 * @param runningLocal
	 *            true if we are running with a host name of localhost
	 * @param dbNumber
	 * 			  if we are running local, this will be the number of the test db we should use
	 */
	public Server(String map, int port, boolean runningLocal, String dbNumber)
	{
		this.port = port;
		this.mapName = map;
		this.runningLocal = runningLocal;
		this.dbIdentifier = dbNumber;
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
				hostName = InetAddress.getLocalHost().getHostName();

			}
			else
			{
				hostName = "localhost";
			}
			om.updateMapInformation(mapName, hostName, port);
			PlayerManager.getSingleton().loadNpcs(false);
			InteractObjectManager.getSingleton();
			servSock = new ServerSocket(OptionsManager.getSingleton().getPortNumber(), 10);
			while (true)
			{
				Socket sock = servSock.accept();
				i++;
				MessagePackerSet messagePackerSet = new MessagePackerSet();
				StateAccumulator stateAccumulator = new StateAccumulator(messagePackerSet);

				new ConnectionManager(sock, stateAccumulator, new MessageHandlerSet(stateAccumulator), messagePackerSet,
						true);
			}

		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize()
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
	 * Run like java -jar server.jar --port=1000 map=recCenter
	 *
	 * @param args
	 *            Main runner
	 * @throws IllegalArgumentException
	 *             Thrown when the port is not given as an argument to the
	 *             execution
	 */
	public static void main(String args[]) throws IllegalArgumentException
	{
		String map = null;
		Integer port = null;
		boolean runningLocal = false;
		String dbIdentifier = null;
		for (String arg : args)
		{
			String[] splitArg = arg.split("=");
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
				OptionsManager.getSingleton().setUsingTestDB(true);
			}
		}
		if (map == null)
		{
			throw new IllegalArgumentException("Map name is required to run the server. Use the --map=STRING option.");
		}
		else if (port == null && !OptionsManager.getSingleton().isUsingMockDataSource())
		{
			throw new IllegalArgumentException("Port is required to run the server. Use the --port=INTEGER option.");
		}
		Server S;
		if (!runningLocal)
		{
			S = new Server(map, port);
		}
		else
		{
			S = new Server(map, port, runningLocal, dbIdentifier);
		}
		S.run();
	}
}
