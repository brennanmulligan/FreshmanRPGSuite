package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.model.OptionsManager;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Test the behavior of the database manager.
 */
public class DatabaseManagerTest
{

    /**
     * @throws DatabaseException shouldn't
     */
    @BeforeClass
    public static void hardReset() throws DatabaseException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        OptionsManager.getSingleton().setTestMode(true);
        DatabaseManager.getSingleton().setTesting();
    }

    @After
    public void tearDown() throws DatabaseException
    {
        DatabaseManager.resetSingleton();
    }

    /**
     * Tests if the singleton is the same
     */
    @Test
    public void isSingleton() throws DatabaseException
    {
        DatabaseManager dm = DatabaseManager.getSingleton();
        DatabaseManager dm1 = DatabaseManager.getSingleton();

        assertSame(dm, dm1);
        DatabaseManager.resetSingleton();
        assertNotSame(dm, DatabaseManager.getSingleton());
    }


}
