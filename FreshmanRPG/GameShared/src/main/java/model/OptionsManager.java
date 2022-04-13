package model;

import java.io.*;
import java.util.Scanner;

import datasource.DatabaseException;

import javax.swing.text.html.Option;

/**
 * Contain information about this server's mapping so it can be used easily in
 * the system
 *
 * @author Steve
 *
 */
public class OptionsManager
{

	/**
	 * Used to get an existing singleton (it must have already been created). If it
	 * hasn't been created, you must use the getSingleton where you specify the
	 * testing mode
	 *
	 * @return the existing singleton
	 */
	public static synchronized OptionsManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new OptionsManager();
		}
		return singleton;
	}

	/**
	 * Reset our instance
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}

	private static OptionsManager singleton;
	private boolean testMode;
	private String mapName;
	private String hostName;
	private int portNumber;

	private String loginHost;

	private boolean usingTestDB = true;
	private String dbIdentifier;
	private String dbPathName = "../GameShared/config.txt";

	/**
	 * @return get the identifier of the DB we should be using
	 */
	public String getDbIdentifier()
	{
		if (dbIdentifier == null)
		{
			try
			{
				Scanner s = new Scanner( new File(dbPathName));
				dbIdentifier = s.nextLine().trim();
				s.close();
			}
			catch (FileNotFoundException e)
			{
				return null;
			}
		}
		return dbIdentifier;
	}

	/**
	 * @param dbIdentifier defines which db we should use
	 */
	public void setDbIdentifier(String dbIdentifier)
	{
		this.dbIdentifier = dbIdentifier;
	}

	/**
	 * I'm a singleton
	 *
	 */
	private OptionsManager()
	{
		hostName = "";
	}

	/**
	 *
	 * @return The host we have mapped to
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * @return the host that is managing logins
	 */
	public String getLoginHost()
	{
		return loginHost;
	}

	/**
	 *
	 * @return Our current map name
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 *
	 * @return The port we have mapped to
	 */
	public int getPortNumber()
	{
		return portNumber;
	}

	/**
	 * returns true if this server is running on mock data for testing purposes
	 * where appropriate
	 *
	 * @return local mode
	 */
	public boolean isUsingMockDataSource()
	{
		return testMode;
	}

	/**
	 * @return true if we are not supposed to use the production database
	 */
	public boolean isUsingTestDB()
	{
		return usingTestDB;
	}

	/**
	 * Used when we are an area server
	 *
	 * @param hostName
	 *            the hostname a server is running on
	 */
	public synchronized void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * @param host
	 *            the host that is managing logins
	 */
	public synchronized void setLoginHost(String host)
	{
		this.loginHost = host;
	}

	/**
	 * @param mapName
	 *            the name of the map file this server should manage
	 */
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	}

	/**
	 * @param b
	 *            if true, we will use mock data whenever possible
	 */
	public void setUsingMocKDataSource(boolean b)
	{
		this.testMode = b;
		if (b)
		{
			setLoginHost("localhost");
		}
	}

	/**
	 * @param usingTestDB
	 *            set to true if we are not supposed to use the production database
	 */
	public synchronized void setUsingTestDB(boolean usingTestDB)
	{
		this.usingTestDB = usingTestDB;
	}

	/**
	 *
	 * @param mapName
	 *            The new map name
	 * @param hostName
	 *            The hostname of the server
	 * @param port
	 *            The port this server is on
	 * @throws DatabaseException
	 *             When the DB operation fails
	 */
	public synchronized void updateMapInformation(String mapName, String hostName, int port) throws DatabaseException
	{
		MapToServerMapping mapping;
		this.mapName = mapName;
		this.hostName = hostName;
		this.portNumber = port;

		mapping = new MapToServerMapping(mapName);
		if (!hostName.equals("localhost"))
		{
			mapping.setHostName(hostName);
			mapping.setMapName(mapName);
			mapping.setPortNumber(port);
			mapping.persist();
		}
		else
		{
			mapping.setHostName("localhost");
			mapping.setMapName(mapName);
			mapping.setPortNumber(port);
		}

	}

	/**
	 * This is used to set the file location of the config file (used in RunOneSequenceTest)
	 * @param dbPathName the path name to be specified for the config file
	 */
	public void setDbFilePath(String dbPathName)
	{
		this.dbPathName = dbPathName;
	}
}
