package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.ContentLoader;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerRowDataGateway;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static edu.ship.engr.shipsim.model.MapToServerMapping.getDataGateway;

/**
 * Contain information about this server's mapping so it can be used easily in
 * the system
 *
 * @author Steve
 */
public class OptionsManager
{

    private static OptionsManager singleton;
    private boolean testMode;
    private String mapFileTitle;
    private String hostName;
    private int portNumber;
    private String loginHost;
    private boolean usingTestDB = true;

    private String dbIdentifier;
    private String dbPathName = "../GameShared/config.txt";
    private String fullyQualifiedMapPath;

    private static final String productionHostName = "rpgserv.engr.ship.edu";

    private final boolean runningInCI;
    private final boolean runningInDocker;
    private final boolean runningInIntelliJ;

    private final ServerRowDataGateway dataGateway;
    private MapToServerMapping mapping;

    /**
     * I'm a singleton
     */
    private OptionsManager()
    {
        hostName = "";

        runningInCI = (System.getenv("GITLAB_CI") != null);
        runningInDocker = (System.getenv("DOCKER_FRPG") != null);
        runningInIntelliJ = (System.getenv("INTELLIJ_MODE") != null);

        dataGateway = getDataGateway();
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
                Scanner s = new Scanner(new File(dbPathName));
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
     * @return The host we have mapped to
     */
    public String getHostName()
    {
        return hostName;
    }

    /**
     * Used when we are an area server
     *
     * @param hostName the hostname a server is running on
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
     * @param host the host that is managing logins
     */
    public synchronized void setLoginHost(String host)
    {
        this.loginHost = host;
    }

    /**
     * @return Our current map name
     */
    public String getMapFileTitle()
    {
        return mapFileTitle;
    }

    public String getFullyQualifiedMapPath()
    {
        return fullyQualifiedMapPath;
    }

    /**
     * @param mapFileTitle the name of the map file this server should manage
     */
    public void setMapFileTitle(String mapFileTitle)
    {
        this.mapFileTitle = mapFileTitle;
        this.fullyQualifiedMapPath = resolveFullMapPath(mapFileTitle);
    }

    /**
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
        return this.runningInCI;
    }

    /**
     * @return true if this code is running inside docker
     */
    public boolean isRunningInDocker()
    {
        return this.runningInDocker;
    }

    public boolean isRunningInIntelliJ()
    {
        return this.runningInIntelliJ;
    }

    public synchronized void setUsingTestDB(boolean usingTestDB)
    {
        this.usingTestDB = usingTestDB;

    }

    /**
     * This is used to set the file location of the config file (used in RunOneSequenceTest)
     *
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
     * @param mapFileTitle The new map name
     * @param hostName     The hostname of the server
     * @param port         The port this server is on
     * @throws DatabaseException When the DB operation fails
     */
    public synchronized void updateMapInformation(String mapFileTitle, String hostName, int port) throws DatabaseException
    {

        this.hostName = hostName;
        this.portNumber = port;

        setMapFileTitle(mapFileTitle);

        mapping = new MapToServerMapping(mapFileTitle);
        if (!hostName.equals("localhost"))
        {
            mapping.setHostName(hostName);
            mapping.setMapFileTitle(mapFileTitle);
            mapping.setPortNumber(port);
            mapping.persist();
        }
        else
        {
            mapping.setHostName("localhost");
            mapping.setMapFileTitle(mapFileTitle);
            mapping.setPortNumber(port);
            mapping.persist();
        }

    }

    public String resolveFullMapPath(String mapFileTitle)
    {
        return ContentLoader.getMapFile(mapFileTitle).getPath();
    }

    /**
     * @return a hostname for the production server
     */
    public String getProductionHostName()
    {
        return productionHostName;
    }

    public void setMapToServerMapping(MapToServerMapping mapToServerMapping)
    {
        this.mapping = mapToServerMapping;
    }

    public boolean isQuiet()
    {
        if (mapping == null)
        {
            return false;
        }
        return mapping.isQuiet();
    }
}
