package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the Npc Class
 *
 * @author Steve
 */
@GameTest("GameServer")
public class NPCTest
{
    /**
     * Test that the npc hits the behavior like it should
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void testBehaviorFires()
            throws InterruptedException, DatabaseException
    {
        NPCMockBehavior mb = new NPCMockBehavior(42);
        NPC npc = new NPC(3);
        npc.setBehavior(mb);
        npc.start();

        // Need to sleep for a known count + a grace period because threads are
        // unpredictable
        Thread.sleep(mb.getPollingInterval() * 3 + (mb.getPollingInterval() / 5));

        npc.stop();

        assertTrue(mb.getCount() > 0);
    }


    /**
     * Test that the npc polling is optional
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void testBehaviorPollingIsOptional()
            throws InterruptedException, DatabaseException
    {
        NPCMockBehavior mb = new NPCMockBehavior(42);
        mb.pollingInterval = 0;
        NPC npc = new NPC(3);
        npc.setBehavior(mb);
        npc.start();

        // Need to sleep for a known count + a grace period because threads are
        // unpredictable
        Thread.sleep(mb.getPollingInterval() * 3 + (mb.getPollingInterval() / 5));

        npc.stop();

        assertEquals(0, mb.getCount());
    }

}