package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test the behavior of the database manager.
 */
@GameTest("GameShared")
public class DatabaseManagerTest
{
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
        DatabaseManager.getSingleton().setTesting();
        assertNotSame(dm, DatabaseManager.getSingleton());
    }


}
