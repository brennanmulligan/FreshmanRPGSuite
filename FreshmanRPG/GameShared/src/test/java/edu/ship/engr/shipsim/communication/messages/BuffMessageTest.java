package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit tests for BuffMessage
 *
 * @author TrucChau
 */
@GameTest("GameShared")
public class BuffMessageTest
{

    /**
     * Tests that BuffMessage returns the correct player's id
     */
    @Test
    public void testGetPlayerID()
    {
        int playerID = 1;
        BuffMessage msg = new BuffMessage(1,false, 10);
        assertEquals(msg.getRelevantPlayerID(), playerID);

    }

    /**
     * Tests that BuffMessage returns the correct player's experience points
     */
    @Test
    public void testGetPlayerExperiencePoints()
    {
        int playerID = 1;
        int experiencePointPool = 25;
        BuffMessage msg = new BuffMessage(playerID, false, experiencePointPool);
        assertEquals(experiencePointPool, msg.getExperiencePointPool());
    }

    /**
     * Tests 2 different objects
     * Should return false because they carry different values
     */
    @Test
    public void testNotEqualObjectComparison()
    {
        BuffMessage msg1 = new BuffMessage(1,false, 10);
        BuffMessage msg2 = new BuffMessage(2,false, 20);
        assertFalse(msg1.equals(msg2));
    }

    /**
     * Tests 2 objects contain the same values
     * They carry the same values so it should return true
     */
    @Test
    public void testEqualObjectComparison()
    {
        BuffMessage msg1 = new BuffMessage(1,false, 10);
        BuffMessage msg2 = new BuffMessage(1,false, 10);
        assertTrue(msg1.equals(msg2));
    }

    /**
     * Tests null object
     * should return false
     */
    @Test
    public void testNullObject()
    {
        BuffMessage msg1 = new BuffMessage(0,false, 0);
        assertFalse(msg1.equals(null));
    }

    /**
     * Different ids, same experience points
     * Should return false
     */
    @Test
    public void testNotEqualPlayerID()
    {
        BuffMessage msg1 = new BuffMessage(1,false, 1);
        BuffMessage msg2 = new BuffMessage(2,false, 1);
        assertFalse(msg1.equals(msg2));

    }

    /**
     * Same ids, different experience points
     * Should return false
     */
    @Test
    public void testNotEqualExperiencePoint()
    {
        BuffMessage msg1 = new BuffMessage(1,false, 1);
        BuffMessage msg2 = new BuffMessage(1,false, 100);
        assertFalse(msg1.equals(msg2));

    }

    /**
     * Testing quietness of the message
     */
    @Test
    public void testisQuiet()
    {
        int playerID = 1;
        BuffMessage msg = new BuffMessage(1,false, 10);
        assertFalse(msg.isQuietMessage());
    }
}
