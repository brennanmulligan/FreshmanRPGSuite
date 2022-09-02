package edu.ship.engr.shipsim.communication;

import edu.ship.engr.shipsim.communication.packers.MessagePackerSet;
import edu.ship.engr.shipsim.datasource.DatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 * Test the parts of the outgoing connection that can be tested without a true
 * socket connection
 */
public class ConnectionOutgoingTest
{

    @Before
    public void setup()
    {
        DatabaseManager.resetSingleton();
    }

    /**
     * Check to make sure that different threads get different database connections.
     *
     * @throws IOException          If there is an error creating the runnable classes.
     * @throws InterruptedException If there is a threading error.
     */
    @Test
    public void testDatabaseConnectionsAreDifferent() throws IOException, InterruptedException
    {
        MessagePackerSet messagePackerSet = new MessagePackerSet();
        MockConnectionOutgoing c1 = new MockConnectionOutgoing(null, null, messagePackerSet);
        MockConnectionOutgoing c2 = new MockConnectionOutgoing(null, null, messagePackerSet);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertNotNull(c1.manager);
        assertNotNull(c2.manager);
        assertNotSame(c1.manager, c2.manager);
    }
}


