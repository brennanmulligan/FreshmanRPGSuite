package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import model.OptionsManager;

/**
 * @author Merlin
 *
 */
public class DatabaseManager
{
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

	private Connection openConnection() throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingTestDB())
		{
			System.err.println("Opening test database");
			String dbIdentifier = OptionsManager.getSingleton().getDbIdentifier();
			if (dbIdentifier == null)
			{
				dbIdentifier = System.getProperty("dbIdentifier");
			}
			System.err.println("Db#" + dbIdentifier);
			if (dbIdentifier == null)
			{
				dbIdentifier = "01";
			}
			return openConnectionTo("jdbc:mysql://db.cs.ship.edu:3306/swe420_" + dbIdentifier +
							"?autoReconnect=true&characterEncoding=latin1",
					"swe420_" + dbIdentifier, "Password_" + dbIdentifier);
		}
		else
		{
			Connection newConnection = openConnectionTo("jdbc:mysql://db.cs.ship.edu:3306/ShipSim?autoReconnect=true", "program",
					"pwd4ShipSim-F16");
			try
			{
				newConnection.setAutoCommit(true);
				return newConnection;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * @throws DatabaseException if we can't complete the commit
	 *
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
		long id = Thread.currentThread().getId();
		Connection connection = connections.get(id);

		if (connection == null)
		{
			try
			{
				addConnection(id);
				return getConnection();
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
		return connections.get(id);
	}

	private HashMap<Long, Connection> getConnections()
	{
		return connections;
	}

	void addConnection(long id) throws DatabaseException
	{
		connections.put(id, openConnection());
	}

	private Connection openConnectionTo(String url, String username, String passwd) throws DatabaseException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
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

	/**
	 * Roll back the current transaction
	 *
	 * @throws SQLException if the rollback fails
	 */
	public void rollBack() throws SQLException
	{
		Statement stmt = getConnection().createStatement();
		stmt.execute("ROLLBACK");
		getConnection().setAutoCommit(true);
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
		try
		{
			getConnection().setAutoCommit(false);
			Statement stmt = getConnection().createStatement();
			stmt.execute("START TRANSACTION");
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to start transation ", e);
		}
	}

	/**
	 * For testing purposes only
	 */
	public static void reset()
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
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		singleton = null;
	}

	/**
	 * Closes the database connection associated with id if there is one open.
	 * @param id The id for which to close a connection.
	 * @throws SQLException If the connection can not be closed.
	 */
	public void closeConnection(long id) throws SQLException
	{
		Connection connection = getConnection(id);
		if (connection != null && !connection.isClosed())
		{
			connection.close();
		}

	}

	/**
	 * Closes the database connection associated with the current thread.
	 * @throws SQLException If the connection can not be closed.
	 */
	public void closeConnection() throws SQLException
	{
		closeConnection(Thread.currentThread().getId());
	}

}
