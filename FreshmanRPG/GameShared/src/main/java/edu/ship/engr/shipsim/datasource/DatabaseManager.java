package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.model.OptionsManager;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author Merlin
 */
public class DatabaseManager
{
    private static final long TESTING_ID = 1;
    private static DatabaseManager singleton;
    private final HashMap<Long, Connection> connections;

    /**
     * @return the only one
     * @throws DatabaseException if we are unable to connect to the db
     */
    public static DatabaseManager getSingleton() throws DatabaseException
    {
        if (singleton == null)
        {
            singleton = new DatabaseManager();
        }
        return singleton;
    }

    private boolean testing;

    private DatabaseManager()
    {
        connections = new HashMap<>();
    }

    public void touchConnection() throws DatabaseException
    {
        for (Connection connection : connections.values())
        {
            if (connection == null)
            {
                LoggerManager.getSingleton().getLogger().warning("Found a null connection to the database. Skipping for now");
                continue;
            }
            try (PreparedStatement pstm = connection.prepareStatement("SELECT now() as time;"))
            {
                pstm.execute();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private Connection openConnection() throws DatabaseException
    {
        if (OptionsManager.getSingleton().isRunningInCI())
        {
            String dbIdentifier = OptionsManager.getSingleton().getDbIdentifier();

            System.out.println("Connecting to database: #" + dbIdentifier);
            if (dbIdentifier == null)
            {
                dbIdentifier = "01";
            }

            return openConnectionTo(
                    "jdbc:mysql://db.cs.ship.edu:3306/swe420_" + dbIdentifier +
                            "?autoReconnect=true&characterEncoding=latin1",
                    "swe420_" + dbIdentifier, "Password_" + dbIdentifier);
        }
        else if (OptionsManager.getSingleton().isRunningInDocker())
        {
            Connection connection = openConnectionTo(
                    "jdbc:mysql://frpgmysql:3306/frpg?autoReconnect=true",
                    "frpg", "Database_Password");

            System.out.println("Connected");

            return connection;
        }
        else
        {
            Connection connection = openConnectionTo(
                    "jdbc:mysql://127.0.0.1:3308/frpg?autoReconnect=true",
                    "frpg", "Database_Password");

            System.out.println("Connected");

            return connection;
        }
    }

    /**
     * @throws DatabaseException if we can't complete the commit
     */
    public void commit() throws DatabaseException
    {
        try
        {
            if (!testing)
            {
                getConnection().commit();
                getConnection().setSavepoint();
            }
            else
            {
                getConnection().rollback();
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to commit changes ", e);
        }
    }

    /**
     * @return the connection to the db
     */
    public Connection getConnection()
    {
        long id;
        if (testing)
        {
            id = TESTING_ID;
        }
        else
        {
            id = Thread.currentThread().getId();
        }
        Connection connection = connections.get(id);

        if (connection == null)
        {
            try
            {
                connection = openConnection();
                connections.put(id, connection);
                if (testing)
                {
                    startTransaction();
                }
            }
            catch (DatabaseException e)
            {
                return null;
            }
        }

        return connection;
    }

    Connection getConnection(long id)
    {
        if (!testing)
        {
            return connections.get(id);
        }
        else
        {
            return connections.get(TESTING_ID);
        }
    }

    private HashMap<Long, Connection> getConnections()
    {
        return connections;
    }

    private Connection openConnectionTo(String url, String username, String passwd)
            throws DatabaseException
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            return DriverManager.getConnection(url, username, passwd);
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to connect to database ", e);
        }
    }

    public void rollBackAllConnections() throws SQLException, DatabaseException
    {
        for (Connection c : connections.values())
        {
            rollBack(c);
        }
    }

    private void rollBack(Connection c) throws SQLException, DatabaseException
    {
        if (!c.isClosed())
        {
            try (Statement statement = c.createStatement())
            {
                statement.execute("ROLLBACK");
                c.commit();
                startTransaction(c);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void startTransaction(Connection c) throws DatabaseException
    {
        try
        {
            c.setAutoCommit(false);
            try (Statement stmt = c.createStatement())
            {
                stmt.execute("START TRANSACTION");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to start transaction ", e);
        }
    }

    /**
     * Roll back the current transaction
     *
     * @throws SQLException if the rollback fails
     */
    public void rollBack() throws SQLException, DatabaseException
    {
        rollBack(getConnection());
    }

    /**
     * When we are testing, use a different db
     *
     * @throws DatabaseException if we can't connect
     */
    public void setTesting() throws DatabaseException
    {
        startTransaction();
        testing = true;
    }

    /**
     * remember a rollback point in case a series of transactions doesn't all
     * work
     *
     * @throws DatabaseException if the save point cannot be created
     */
    public void startTransaction() throws DatabaseException
    {
        startTransaction(getConnection());
    }

    /**
     * For testing purposes only
     */
    public static void resetSingleton()
    {
        try
        {
            if (singleton != null)
            {
                singleton.rollBack();

                for (Connection connection : singleton.getConnections().values())
                {
                    connection.close();
                }
            }
        }
        catch (SQLException | DatabaseException e)
        {
            e.printStackTrace();
        }
        singleton = null;
    }

    /**
     * Closes the database connection associated with id if there is one open.
     *
     * @param id The id for which to close a connection.
     * @throws SQLException If the connection can not be closed.
     */
    public void closeConnection(long id) throws SQLException
    {
        try (Connection connection = connections.remove((id)))
        {
            if (connection != null && !connection.isClosed())
            {
                connection.close();
            }
        }
    }

    /**
     * Closes the database connection associated with the current thread.
     *
     * @throws SQLException If the connection can not be closed.
     */
    public void closeConnection() throws SQLException
    {
        closeConnection(Thread.currentThread().getId());
    }

    public int executeUpdate(@Language("MySQL") String query, Object... params) throws DatabaseException
    {
        try (PreparedStatement pstm = getConnection().prepareStatement(query))
        {
            for (int i = 0; i < params.length; i++)
            {
                pstm.setObject(i + 1, params[i]);
            }

            return pstm.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("executeUpdate Failed: ", e);
        }
    }

    public ResultSet executeQuery(@Language("MySQL") String query, Object... params) throws DatabaseException
    {
        try (PreparedStatement pstm = getConnection().prepareStatement(query))
        {
            for (int i = 0; i < params.length; i++)
            {
                pstm.setObject(i + 1, params[i]);
            }

            return pstm.executeQuery();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("executeQuery Failed: ", e);
        }
    }
}
