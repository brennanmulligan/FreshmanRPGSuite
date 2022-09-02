package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.model.OptionsManager;
import org.junit.After;
import org.junit.Before;

import java.sql.SQLException;

/**
 * Defines set up and tear down methods that set up the DB for testing and roll
 * back changes when the test is over
 *
 * @author Merlin
 */
public abstract class ServerSideTest
{

    /**
     * set up the database and other singletons for a test
     *
     * @throws DatabaseException shouldn't
     */
    @Before
    public final void setUp() throws DatabaseException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        OptionsManager.getSingleton().setTestMode(true);
        DatabaseManager.getSingleton().setTesting();
    }

    /**
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn't
     */
    @After
    public final void tearDown() throws DatabaseException, SQLException
    {
        DatabaseManager.getSingleton().rollBackAllConnections();
    }
}
