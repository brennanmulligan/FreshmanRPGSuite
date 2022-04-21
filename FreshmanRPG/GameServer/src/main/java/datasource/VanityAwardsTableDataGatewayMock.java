package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityAwardsForTest;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * A class for the mock data source of the VanityAwards table
 */
public class VanityAwardsTableDataGatewayMock implements VanityAwardsTableDataGateway
{

    private HashMap<Integer, ArrayList<MockVanityAwardsRow>> vanityAwardsTable;
    private static VanityAwardsTableDataGateway singleton;
    private VanityItemsTableDataGatewayInterface vanityItemsGateway;

    private class MockVanityAwardsRow
    {
        int questID;
        int vanityID;

        public MockVanityAwardsRow(int questID, int vanityID)
        {
            this.questID = questID;
            this.vanityID = vanityID;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }
            MockVanityAwardsRow that = (MockVanityAwardsRow) o;
            return questID == that.questID && vanityID == that.vanityID;
        }
    }

    /**
     * Gets the instance of this gateway
     * @return the instance
     * @throws DatabaseException shouldn't
     */
    public static synchronized VanityAwardsTableDataGateway getSingleton() throws DatabaseException
    {
        if (singleton == null)
        {
            singleton = new VanityAwardsTableDataGatewayMock();
        }
        return singleton;
    }

    private VanityAwardsTableDataGatewayMock() throws DatabaseException
    {
        resetData();
    }

    /**
     * Gets all the vanity awards stored in the database
     *
     * @return a list of all the vanity awards
     * @throws DatabaseException shouldn't
     */
    @Override
    public ArrayList<VanityDTO> getVanityAwards() throws DatabaseException
    {
        ArrayList<VanityDTO> dtoList = new ArrayList<>();
        for (ArrayList<MockVanityAwardsRow> m : vanityAwardsTable.values())
        {
            for (MockVanityAwardsRow r : m)
            {
                dtoList.add(vanityItemsGateway.getVanityItemByID(r.vanityID));
            }
        }
        return dtoList;
    }

    /**
     * Gets all the vanity awards for a given questID
     *
     * @param questID the quest
     * @return a list of all the vanity awards given by that quest
     * @throws DatabaseException shouldnt
     */
    @Override
    public ArrayList<VanityDTO> getVanityAwardsForQuest(int questID) throws DatabaseException
    {
        ArrayList<VanityDTO> dtoList = new ArrayList<>();
        if (vanityAwardsTable.containsKey(questID))
        {
           ArrayList<MockVanityAwardsRow> row = vanityAwardsTable.get(questID);
           for (MockVanityAwardsRow m : row)
           {
               dtoList.add(vanityItemsGateway.getVanityItemByID(m.vanityID));
           }
        }
        return dtoList;
    }

    /**
     * Adds a vanity award to the vanity awards list so it can be given as a quest reward
     *
     * @param awardID the ID of the vanity award to add
     * @throws DatabaseException shouldn't
     */
    @Override
    public void addVanityAward(int questID, int awardID) throws DatabaseException
    {
        try
        {
            new QuestRowDataGatewayMock(questID);
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("Vanity award could not be added because quest (" + questID + ") does not exist");
        }
        if (vanityItemsGateway.getVanityItemByID(awardID) == null)
        {
            throw new DatabaseException("Vanity ID (" + awardID + ") can't be added because it is invalid");
        }

        if (vanityAwardsTable.containsKey(questID))
        {
            ArrayList<MockVanityAwardsRow> rows = vanityAwardsTable.get(questID);
            for (MockVanityAwardsRow row : rows)
            {
                if (row.vanityID == awardID)
                {
                    throw new DatabaseException("Duplicate award (" + awardID + ") given for quest (" + questID + ")");
                }
            }
            rows.add(new MockVanityAwardsRow(questID, awardID));
        }
        else
        {
            ArrayList<MockVanityAwardsRow> row = new ArrayList<>();
            row.add(new MockVanityAwardsRow(questID, awardID));
            vanityAwardsTable.put(questID, row);
        }
    }

    /**
     * Removes a vanity award from the vanity awards list so it cant be given out anymore
     *
     * @param awardID the id of the award to be removed
     * @throws DatabaseException shouldnt
     */
    @Override
    public void removeVanityAward(int questID, int awardID) throws DatabaseException
    {
        if (vanityAwardsTable.containsKey(questID))
        {
            ArrayList<MockVanityAwardsRow> rows = vanityAwardsTable.get(questID);
            if (!rows.remove(new MockVanityAwardsRow(questID, awardID)))
            {
                throw new DatabaseException("Could not remove vanity award from quest (" + questID +
                        ") because it did not award that vanity to begin with");
            }
            if (rows.size() == 0)
            {
                vanityAwardsTable.remove(questID);
            }
        }
        else
        {
            throw new DatabaseException("Could not remove vanity award from quest (" + questID +
                    ") because it did not award that vanity to begin with");
        }
    }

    /**
     * Resets the data
     *
     * @throws DatabaseException shouldnt
     */
    @Override
    public void resetData() throws DatabaseException
    {
        vanityItemsGateway = VanityItemsTableDataGatewayMock.getSingleton();
        vanityAwardsTable = new HashMap<>();

        for (VanityAwardsForTest v : VanityAwardsForTest.values())
        {
            addVanityAward(v.getQuestID(), v.getVanityID());
        }
    }
}
