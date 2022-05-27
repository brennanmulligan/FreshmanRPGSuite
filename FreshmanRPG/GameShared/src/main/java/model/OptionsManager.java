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
	 * I'm a singleton
	 *
	 */
	private OptionsManager()
	{
		hostName = "";
	}

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

	public void assertTestMode()
	{
		assert this.testMode;
	}

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

	public boolean isTestMode()
	{
		return testMode;
	}

	/**
	 * @param dbIdentifier defines which db we should use
	 */
	public void setDbIdentifier(String dbIdentifier)
	{
		this.dbIdentifier = dbIdentifier;
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
	 * @return the host that is managing logins
	 */
	public String getLoginHost()
	{
		return loginHost;
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
	 *
	 * @return Our current map name
	 */
	public String getMapName()
	{
		return mapName;
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
	 *
	 * @return The port we have mapped to
	 */
	public int getPortNumber()
	{
		return portNumber;
	}

	/**
	 * @return true if we are not supposed to use the production database
	 */
	public boolean isUsingTestDB()
	{
		return usingTestDB;
	}

	/**
	 * @return true if this code is running in Gitlab's CI
	 */
	public boolean isRunningInCI()
	{
		return System.getenv("GITLAB_CI") != null;
	}

	/**
	 * @return true if this code is running inside docker
	 */
	public boolean isRunningInDocker()
	{
		return System.getenv("DOCKER_FRPG") != null;
	}


	public synchronized void setUsingTestDB(boolean usingTestDB)
	{
		this.usingTestDB = usingTestDB;

	}

	/**
	 * This is used to set the file location of the config file (used in RunOneSequenceTest)
	 * @param dbPathName the path name to be specified for the config file
	 */
	public void setDbFilePath(String dbPathName)
	{
		this.dbPathName = dbPathName;
	}



	public void setTestMode(boolean b)
	{
		this.testMode = b;
		if (b)
		{
			setLoginHost("localhost");
		}
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
}
