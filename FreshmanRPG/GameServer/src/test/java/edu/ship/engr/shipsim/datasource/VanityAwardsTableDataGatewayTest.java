package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityAwardsForTest;
import edu.ship.engr.shipsim.datatypes.VanityItemsForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the VanityAwardsTableDataGateway
 */
@GameTest("GameServer")
public class VanityAwardsTableDataGatewayTest
{
    protected VanityAwardsTableDataGateway gateway;

    /**
     * Get the right gateway and set up the gateway
     *
     * @throws DatabaseException shouldnt
     */
    @BeforeEach
    public void setup() throws DatabaseException
    {
        gateway = findGateway();
    }

    /**
     * Tests to make sure we cannot add a duplicate vanity award
     */
    @Test
    public void cannotAddDuplicateItem() throws DatabaseException
    {

        try
        {
            gateway.addVanityAward(4, 1);
        }
        catch (DatabaseException e)
        {
            fail("Could not add the first!");
        }
       assertThrows( DatabaseException.class, () -> gateway.addVanityAward(4, 1));

    }

    /**
     * Tests to make sure we cannot add an invalid vanity award
     */
    @Test
    public void cannotAddInvalidVanityAward()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.addVanityAward(1, -1);
        });
    }

    /**
     * Tests to make sure we cannot add a vanity award to an invalid quest id
     */
    @Test
    public void cannotAddVanityAwardForInvalidQuest()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.addVanityAward(-1, 1);
        });
    }

    /**
     * Tests to make sure we cannot remove an invalid vanity award
     */
    @Test
    public void cannotRemoveInvalidAward()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.removeVanityAward(1, -1);
        });
    }

    /**
     * Tests to make sure singleton pattern works
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void isASingleton() throws DatabaseException
    {
        VanityAwardsTableDataGateway a = findGateway();
        VanityAwardsTableDataGateway b = findGateway();
        assertEquals(a, b);
        assertNotNull(a);
    }

    /**
     * Tests to make sure we can add a vanity award
     * REQUIRES: Merlin hat to not be a vanity award
     */
    @Test
    public void testAddItem() throws DatabaseException
    {
        ArrayList<Integer> awardsFromGateway =
                getIDsFromVanityDTO(gateway.getVanityAwardsForQuest(5));
        assertFalse(awardsFromGateway.contains(VanityItemsForTest.MERLIN_HAT.getId()));

        gateway.addVanityAward(5, VanityItemsForTest.MERLIN_HAT.getId());

        awardsFromGateway = getIDsFromVanityDTO(gateway.getVanityAwardsForQuest(5));

        assertTrue(awardsFromGateway.contains(VanityItemsForTest.MERLIN_HAT.getId()));
    }

    /**
     * Test to make sure we can get an award for a quest id
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void testGetAItemForQuest() throws DatabaseException
    {
        ArrayList<VanityDTO> awardsFromGateway = gateway.getVanityAwardsForQuest(1);
        ArrayList<Integer> awardsFromGatewayIDs;
        ArrayList<Integer> testAwards = new ArrayList<>();

        for (VanityAwardsForTest item : VanityAwardsForTest.values())
        {
            if (item.getQuestID() == 1)
            {
                testAwards.add(item.getVanityID());
            }
        }

        awardsFromGatewayIDs = getIDsFromVanityDTO(awardsFromGateway);
        assertTrue(testAwards.containsAll(awardsFromGatewayIDs) &&
                awardsFromGatewayIDs.containsAll(testAwards));
    }

    /**
     * Tests to make sure we can get all the vanity awards
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void testGetAllItems() throws DatabaseException
    {
        ArrayList<VanityDTO> awardsFromGateway = gateway.getVanityAwards();
        ArrayList<Integer> awardsFromGatewayIDs;
        ArrayList<Integer> testAwards = new ArrayList<>();

        for (VanityAwardsForTest item : VanityAwardsForTest.values())
        {
            testAwards.add(item.getVanityID());
        }

        awardsFromGatewayIDs = getIDsFromVanityDTO(awardsFromGateway);
        assertTrue(testAwards.containsAll(awardsFromGatewayIDs) &&
                awardsFromGatewayIDs.containsAll(testAwards));
    }

    /**
     * Test to make sure we can get multiple awards for a quest id
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void testGetMultipleItemsForQuest() throws DatabaseException
    {
        ArrayList<VanityDTO> awardsFromGateway = gateway.getVanityAwardsForQuest(4);
        ArrayList<Integer> awardsFromGatewayIDs;
        ArrayList<Integer> testAwards = new ArrayList<>();

        for (VanityAwardsForTest item : VanityAwardsForTest.values())
        {
            if (item.getQuestID() == 4)
            {
                testAwards.add(item.getVanityID());
            }
        }

        awardsFromGatewayIDs = getIDsFromVanityDTO(awardsFromGateway);
        assertTrue(testAwards.containsAll(awardsFromGatewayIDs) &&
                awardsFromGatewayIDs.containsAll(testAwards));
    }

    /**
     * Tests to make sure we can remove a vanity award
     */
    @Test
    public void testRemoveAward() throws DatabaseException
    {
        gateway.addVanityAward(4, VanityItemsForTest.MERLIN_HAT.getId());
        ArrayList<Integer> itemsFromGateway =
                getIDsFromVanityDTO(gateway.getVanityAwards());
        assertTrue(itemsFromGateway.contains(VanityItemsForTest.MERLIN_HAT.getId()));

        gateway.removeVanityAward(4, VanityItemsForTest.MERLIN_HAT.getId());

        itemsFromGateway = getIDsFromVanityDTO(gateway.getVanityAwards());
        assertFalse(itemsFromGateway.contains(VanityItemsForTest.MERLIN_HAT.getId()));
    }

    /**
     * @param awardsFromGateway the vanity awards from the gateway
     * @return a list of the awards ids
     */
    private ArrayList<Integer> getIDsFromVanityDTO(ArrayList<VanityDTO> awardsFromGateway)
    {
        ArrayList<Integer> awardsFromGatewayIDs = new ArrayList<>();
        for (VanityDTO dto : awardsFromGateway)
        {
            awardsFromGatewayIDs.add(dto.getID());
        }
        return awardsFromGatewayIDs;
    }

    VanityAwardsTableDataGateway findGateway()
    {
        return VanityAwardsTableDataGateway.getSingleton();
    }
}
