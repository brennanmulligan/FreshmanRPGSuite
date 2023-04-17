package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@GameTest("Watchdog")
public class TestWatchdog
{
    @BeforeEach
    public void setUp()
    {
        Watchdog.getSingleton().getServerMap().clear();
    }

    @Test
    public void testSomeDeadServers()
    {
        long currentTime = System.currentTimeMillis();

        Watchdog.getSingleton().addServer("server1", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server2", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server3", currentTime - Watchdog.TIMEOUT_DURATION * 2);
        Watchdog.getSingleton().addServer("server4", currentTime - Watchdog.TIMEOUT_DURATION * 2);
        Watchdog.getSingleton().addServer("server5", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server6", currentTime - Watchdog.TIMEOUT_DURATION);

        Map<String, Long> deadServers = Watchdog.getDeadServers();

        assertEquals(2, deadServers.size());
        assertTrue(deadServers.containsKey("server3"));
        assertTrue(deadServers.containsKey("server4"));
    }

    @Test
    public void testNoDeadServers()
    {
        long currentTime = System.currentTimeMillis();

        Watchdog.getSingleton().addServer("server1", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server2", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server3", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server4", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server5", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server6", currentTime - Watchdog.TIMEOUT_DURATION);

        Map<String, Long> deadServers = Watchdog.getDeadServers();

        assertEquals(0, deadServers.size());
    }

    @Test
    public void testAllDeadServers()
    {
        long currentTime = System.currentTimeMillis();

        Watchdog.getSingleton().addServer("server1", currentTime - Watchdog.TIMEOUT_DURATION * 2);
        Watchdog.getSingleton().addServer("server2", currentTime - Watchdog.TIMEOUT_DURATION * 2);
        Watchdog.getSingleton().addServer("server3", currentTime - Watchdog.TIMEOUT_DURATION * 2);
        Watchdog.getSingleton().addServer("server4", currentTime - Watchdog.TIMEOUT_DURATION * 2);
        Watchdog.getSingleton().addServer("server5", currentTime - Watchdog.TIMEOUT_DURATION * 2);
        Watchdog.getSingleton().addServer("server6", currentTime - Watchdog.TIMEOUT_DURATION * 2);

        Map<String, Long> deadServers = Watchdog.getDeadServers();

        assertEquals(6, deadServers.size());
    }

    // test border cases for 1 second less and 1 second greater than 1 minute
    @Test
    public void testBorderCases()
    {
        long currentTime = System.currentTimeMillis();

        // 1 second less than 1 minute
        Watchdog.getSingleton().addServer("server1", currentTime - Watchdog.TIMEOUT_DURATION - 1);

        // 1 minute
        Watchdog.getSingleton().addServer("server2", currentTime - Watchdog.TIMEOUT_DURATION);

        // 1 second greater than 1 minute
        Watchdog.getSingleton().addServer("server3", currentTime - Watchdog.TIMEOUT_DURATION + 1);

        Map<String, Long> deadServers = Watchdog.getDeadServers();

        assertEquals(1, deadServers.size());

        // check that server3 is in the map
        assertTrue(Watchdog.getSingleton().getServerMap().containsKey("server3"));
    }
}