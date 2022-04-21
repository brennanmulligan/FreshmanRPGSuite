package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityAwardsForTest;
import datatypes.VanityForTest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public abstract class VanityAwardsTableDataGatewayTest  extends DatabaseTest
{
    protected VanityAwardsTableDataGateway gateway;
    abstract VanityAwardsTableDataGateway findGateway() throws DatabaseException;

    @Before
    public void setup() throws DatabaseException
    {
        gateway = findGateway();
        gateway.resetData();
    }

    /**
     * Tests to make sure singleton pattern works
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
        assertTrue(testAwards.containsAll(awardsFromGatewayIDs) && awardsFromGatewayIDs.containsAll(testAwards));
    }

    @Test
    public void testGetAItemForQuest() throws DatabaseException
    {
        ArrayList<VanityDTO> awardsFromGateway = gateway.getVanityAwardsForQuest(1);
        ArrayList<Integer> awardsFromGatewayIDs;
        ArrayList<Integer> testAwards = new ArrayList<>();

        for (VanityAwardsForTest item : VanityAwardsForTest.values())
        {
            if(item.getQuestID() == 1) {
                testAwards.add(item.getVanityID());
            }
        }

        awardsFromGatewayIDs = getIDsFromVanityDTO(awardsFromGateway);
        assertTrue(testAwards.containsAll(awardsFromGatewayIDs) && awardsFromGatewayIDs.containsAll(testAwards));
    }

    @Test
    public void testGetMultipleItemsForQuest() throws DatabaseException
    {
        ArrayList<VanityDTO> awardsFromGateway = gateway.getVanityAwardsForQuest(4);
        ArrayList<Integer> awardsFromGatewayIDs;
        ArrayList<Integer> testAwards = new ArrayList<>();

        for (VanityAwardsForTest item : VanityAwardsForTest.values())
        {
            if(item.getQuestID() == 4) {
                testAwards.add(item.getVanityID());
            }
        }

        awardsFromGatewayIDs = getIDsFromVanityDTO(awardsFromGateway);
        assertTrue(testAwards.containsAll(awardsFromGatewayIDs) && awardsFromGatewayIDs.containsAll(testAwards));
    }

    /**
     * Tests to make sure we can add an item
     * REQUIRES: Merlin hat to not be a vanity award
     */
    @Test
    public void testAddItem() throws DatabaseException
    {
        ArrayList<Integer> awardsFromGateway = getIDsFromVanityDTO(gateway.getVanityAwardsForQuest(5));
        assertFalse(awardsFromGateway.contains(VanityForTest.MerlinHat.getId()));

        gateway.addVanityAward(5, VanityForTest.MerlinHat.getId());

        awardsFromGateway = getIDsFromVanityDTO(gateway.getVanityAwardsForQuest(5));

        assertTrue(awardsFromGateway.contains(VanityForTest.MerlinHat.getId()));
    }

    @Test (expected = DatabaseException.class)
    public void cannotAddDuplicateItem() throws DatabaseException
    {
        ArrayList<VanityDTO> awardsFromGateway = gateway.getVanityAwardsForQuest(4);
        gateway.addVanityAward(4, awardsFromGateway.get(0).getID());
    }

    @Test (expected = DatabaseException.class)
    public void cannotAddInvalidVanityAward() throws DatabaseException
    {
        gateway.addVanityAward(1, -1);
    }

    @Test (expected = DatabaseException.class)
    public void cannotAddVanityAwardForInvalidQuest() throws DatabaseException
    {
        gateway.addVanityAward(-1, 1);
    }

    @Test (expected = DatabaseException.class)
    public void cannotRemoveInvalidAward() throws DatabaseException
    {
        gateway.removeVanityAward(-1, -1);
    }

    @Test
    public void testRemoveAward() throws DatabaseException
    {
        gateway.addVanityAward(4, VanityForTest.MerlinHat.getId());
        ArrayList<Integer> itemsFromGateway = getIDsFromVanityDTO(gateway.getVanityAwards());
        assertTrue(itemsFromGateway.contains(VanityForTest.MerlinHat.getId()));

        gateway.removeVanityAward(4, VanityForTest.MerlinHat.getId());

        itemsFromGateway = getIDsFromVanityDTO(gateway.getVanityAwards());
        assertFalse(itemsFromGateway.contains(VanityForTest.MerlinHat.getId()));
    }

    private ArrayList<Integer> getIDsFromVanityDTO(ArrayList<VanityDTO> awardsFromGateway)
    {
        ArrayList<Integer> awardsFromGatewayIDs = new ArrayList<>();
        for (VanityDTO dto : awardsFromGateway)
        {
            awardsFromGatewayIDs.add(dto.getID());
        }
        return awardsFromGatewayIDs;
    }
}
