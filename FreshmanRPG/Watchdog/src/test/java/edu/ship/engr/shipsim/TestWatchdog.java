package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@GameTest("Watchdog")
public class TestWatchdog
{
    public static final int DEAD_MULTIPLIER = 2;

    @BeforeEach
    public void setUp()
    {
        Watchdog.getSingleton().getServerMap().clear();
    }

    @Test
    public void testSomeDeadServers()
    {
        Instant now = Instant.now();
        long currentTime = now.toEpochMilli();

        Watchdog.getSingleton().addServer("server1", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server2", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server3", currentTime - Watchdog.TIMEOUT_DURATION * DEAD_MULTIPLIER);
        Watchdog.getSingleton().addServer("server4", currentTime - Watchdog.TIMEOUT_DURATION * DEAD_MULTIPLIER);
        Watchdog.getSingleton().addServer("server5", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server6", currentTime - Watchdog.TIMEOUT_DURATION);

        try (MockedStatic<Instant> instant = mockStatic(Instant.class))
        {
            instant.when(Instant::now).thenReturn(now);

            Map<String, Long> deadServers = Watchdog.getDeadServers();

            assertEquals(2, deadServers.size());
            assertTrue(deadServers.containsKey("server3"));
            assertTrue(deadServers.containsKey("server4"));
        }
    }

    @Test
    public void testNoDeadServers()
    {
        Instant now = Instant.now();
        long currentTime = now.toEpochMilli();

        Watchdog.getSingleton().addServer("server1", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server2", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server3", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server4", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server5", currentTime - Watchdog.TIMEOUT_DURATION);
        Watchdog.getSingleton().addServer("server6", currentTime - Watchdog.TIMEOUT_DURATION);

        try (MockedStatic<Instant> instant = mockStatic(Instant.class))
        {
            instant.when(Instant::now).thenReturn(now);

            Map<String, Long> deadServers = Watchdog.getDeadServers();

            assertEquals(0, deadServers.size());
        }
    }

    @Test
    public void testAllDeadServers()
    {
        Instant now = Instant.now();
        long currentTime = now.toEpochMilli();

        Watchdog.getSingleton().addServer("server1", currentTime - Watchdog.TIMEOUT_DURATION * DEAD_MULTIPLIER);
        Watchdog.getSingleton().addServer("server2", currentTime - Watchdog.TIMEOUT_DURATION * DEAD_MULTIPLIER);
        Watchdog.getSingleton().addServer("server3", currentTime - Watchdog.TIMEOUT_DURATION * DEAD_MULTIPLIER);
        Watchdog.getSingleton().addServer("server4", currentTime - Watchdog.TIMEOUT_DURATION * DEAD_MULTIPLIER);
        Watchdog.getSingleton().addServer("server5", currentTime - Watchdog.TIMEOUT_DURATION * DEAD_MULTIPLIER);
        Watchdog.getSingleton().addServer("server6", currentTime - Watchdog.TIMEOUT_DURATION * DEAD_MULTIPLIER);

        try (MockedStatic<Instant> instant = mockStatic(Instant.class))
        {
            instant.when(Instant::now).thenReturn(now);

            Map<String, Long> deadServers = Watchdog.getDeadServers();

            assertEquals(6, deadServers.size());
        }
    }

    // test border cases for 1 second less and 1 second greater than 1 minute
    @Test
    public void testBorderCases()
    {
        Instant now = Instant.now();
        long currentTime = now.toEpochMilli();

        // 1 second less than 1 minute
        Watchdog.getSingleton().addServer("server1", currentTime - Watchdog.TIMEOUT_DURATION - 1);

        // 1 minute
        Watchdog.getSingleton().addServer("server2", currentTime - Watchdog.TIMEOUT_DURATION);

        // 1 second greater than 1 minute
        Watchdog.getSingleton().addServer("server3", currentTime - Watchdog.TIMEOUT_DURATION + 1);

        try (MockedStatic<Instant> instant = mockStatic(Instant.class))
        {
            instant.when(Instant::now).thenReturn(now);

            Map<String, Long> deadServers = Watchdog.getDeadServers();

            assertEquals(1, deadServers.size());

            // check that server3 is in the map
            assertTrue(Watchdog.getSingleton().getServerMap().containsKey("server3"));
        }
    }
}