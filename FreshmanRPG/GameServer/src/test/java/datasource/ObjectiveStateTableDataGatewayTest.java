package datasource;

import dataDTO.ObjectiveStateRecordDTO;
import datatypes.ObjectiveStateEnum;
import datatypes.ObjectiveStatesForTest;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * An abstract class that tests the table data gateways into the Objective table
 *
 * @author merlin
 */
public class ObjectiveStateTableDataGatewayTest extends ServerSideTest
{

    protected ObjectiveStateTableDataGateway gateway;

    /**
     * Updating a non-existing record adds it to the data source
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canAddNew() throws DatabaseException
    {
        gateway = getGateway();
        gateway.updateState(13, 22, 11, ObjectiveStateEnum.TRIGGERED, true);
        ArrayList<ObjectiveStateRecordDTO> actual = gateway.getObjectiveStates(13, 22);
        assertEquals(1, actual.size());
        ObjectiveStateRecordDTO asRec = actual.get(0);
        assertEquals(13, asRec.getPlayerID());
        assertEquals(22, asRec.getQuestID());
        assertEquals(11, asRec.getObjectiveID());
        assertEquals(ObjectiveStateEnum.TRIGGERED, asRec.getState());
        assertTrue(asRec.isNeedingNotification());

    }

    /**
     * Should be able to change the state of an existing record
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canChangeExisting() throws DatabaseException
    {
        gateway = getGateway();
        ObjectiveStatesForTest adv = ObjectiveStatesForTest.PLAYER2_QUEST1_ADV3;
        gateway.updateState(adv.getPlayerID(), adv.getQuestID(), adv.getObjectiveID(),
                ObjectiveStateEnum.COMPLETED,
                false);

        ArrayList<ObjectiveStateRecordDTO> actual =
                gateway.getObjectiveStates(adv.getPlayerID(), adv.getQuestID());
        int count = 0;
        for (ObjectiveStateRecordDTO asRec : actual)
        {
            if (asRec.getQuestID() == adv.getQuestID() &&
                    asRec.getObjectiveID() == adv.getObjectiveID())
            {
                count = count + 1;
                assertEquals(ObjectiveStateEnum.COMPLETED, asRec.getState());
                assertFalse(asRec.isNeedingNotification());
            }
        }
        assertEquals(1, count);
    }

    /**
     * @return the gateway we should test
     */
    public ObjectiveStateTableDataGateway getGateway()
    {
        return ObjectiveStateTableDataGateway.getSingleton();
    }

    /**
     *
     */
    @Test
    public void isASingleton()
    {
        ObjectiveStateTableDataGateway x = getGateway();
        ObjectiveStateTableDataGateway y = getGateway();
        assertSame(x, y);
        assertNotNull(x);
    }

    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void retrieveAllObjectivesForQuest() throws DatabaseException
    {
        gateway = getGateway();
        ArrayList<ObjectiveStateRecordDTO> records = gateway.getObjectiveStates(1, 2);
        assertEquals(3, records.size());
        ObjectiveStateRecordDTO record = records.get(0);
        // the records could be in either order
        ObjectiveStatesForTest first = ObjectiveStatesForTest.PLAYER1_QUEST2_ADV1;
        ObjectiveStatesForTest other = ObjectiveStatesForTest.PLAYER1_QUEST2_ADV2;
        if (record.getObjectiveID() == first.getObjectiveID())
        {
            assertEquals(first.getState(), record.getState());
            assertEquals(first.getQuestID(), record.getQuestID());
            record = records.get(1);
            assertEquals(other.getState(), record.getState());
            assertEquals(other.getQuestID(), record.getQuestID());
        }
        else
        {
            assertEquals(other.getObjectiveID(), record.getObjectiveID());
            assertEquals(other.getState(), record.getState());
            assertEquals(other.getQuestID(), record.getQuestID());
            record = records.get(1);
            assertEquals(first.getState(), record.getState());
            assertEquals(first.getQuestID(), record.getQuestID());
        }
    }

    /**
     * Make sure we can retrieve all pending objectives for a given player
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void retrieveAllPendingObjectivesForPlayer() throws DatabaseException
    {
        gateway = getGateway();
        ArrayList<ObjectiveStateRecordDTO> records =
                gateway.getPendingObjectivesForPlayer(1);
        assertEquals(9, records.size());
        // the records could be in either order
        ObjectiveStatesForTest first = ObjectiveStatesForTest.PLAYER1_QUEST2_ADV3;
        ObjectiveStatesForTest other = ObjectiveStatesForTest.PLAYER1_QUEST3_ADV1;
        ObjectiveStatesForTest third = ObjectiveStatesForTest.PLAYER1_QUEST3_ADV3;
        ObjectiveStatesForTest fourth = ObjectiveStatesForTest.PLAYER1_QUEST1_ADV1;
        ObjectiveStatesForTest fifth = ObjectiveStatesForTest.PLAYER1_QUEST4_ADV1;
        ObjectiveStatesForTest sixth = ObjectiveStatesForTest.PLAYER1_QUEST4_ADV2;
        ObjectiveStatesForTest seventh = ObjectiveStatesForTest.PLAYER1_QUEST100_ADV10;
        ObjectiveStatesForTest eighth = ObjectiveStatesForTest.PLAYER1_QUEST100_ADV11;
        ObjectiveStatesForTest ninth = ObjectiveStatesForTest.PLAYER1_QUEST17_ADV1;

        for (ObjectiveStateRecordDTO record : records)
        {
            if ((record.getObjectiveID() == first.getObjectiveID() &&
                    (record.getQuestID() == first.getQuestID())))
            {
                assertEquals(first.getState(), record.getState());
                assertEquals(first.getQuestID(), record.getQuestID());
                assertEquals(first.isNeedingNotification(),
                        record.isNeedingNotification());

            }
            else if ((record.getObjectiveID() == other.getObjectiveID()
                    && (record.getQuestID() == other.getQuestID())))
            {
                assertEquals(other.getObjectiveID(), record.getObjectiveID());
                assertEquals(other.getState(), record.getState());
                assertEquals(other.getQuestID(), record.getQuestID());
                assertEquals(other.isNeedingNotification(),
                        record.isNeedingNotification());
            }
            else if ((record.getObjectiveID() == third.getObjectiveID()
                    && (record.getQuestID() == third.getQuestID())))
            {
                assertEquals(third.getObjectiveID(), record.getObjectiveID());
                assertEquals(third.getState(), record.getState());
                assertEquals(third.getQuestID(), record.getQuestID());
                assertEquals(third.isNeedingNotification(),
                        record.isNeedingNotification());
            }
            else if ((record.getObjectiveID() == fourth.getObjectiveID()
                    && (record.getQuestID() == fourth.getQuestID())))
            {
                assertEquals(fourth.getObjectiveID(), record.getObjectiveID());
                assertEquals(fourth.getState(), record.getState());
                assertEquals(fourth.getQuestID(), record.getQuestID());
                assertEquals(fourth.isNeedingNotification(),
                        record.isNeedingNotification());
            }
            else if ((record.getObjectiveID() == fifth.getObjectiveID()
                    && (record.getQuestID() == fifth.getQuestID())))
            {
                assertEquals(fifth.getObjectiveID(), record.getObjectiveID());
                assertEquals(fifth.getState(), record.getState());
                assertEquals(fifth.getQuestID(), record.getQuestID());
                assertEquals(fifth.isNeedingNotification(),
                        record.isNeedingNotification());
            }
            else if ((record.getObjectiveID() == sixth.getObjectiveID()
                    && (record.getQuestID() == sixth.getQuestID())))
            {
                assertEquals(sixth.getObjectiveID(), record.getObjectiveID());
                assertEquals(sixth.getState(), record.getState());
                assertEquals(sixth.getQuestID(), record.getQuestID());
                assertEquals(sixth.isNeedingNotification(),
                        record.isNeedingNotification());
            }
            else if ((record.getObjectiveID() == seventh.getObjectiveID()
                    && (record.getQuestID() == seventh.getQuestID())))
            {
                assertEquals(seventh.getObjectiveID(), record.getObjectiveID());
                assertEquals(seventh.getState(), record.getState());
                assertEquals(seventh.getQuestID(), record.getQuestID());
                assertEquals(seventh.isNeedingNotification(),
                        record.isNeedingNotification());
            }
            else if ((record.getObjectiveID() == eighth.getObjectiveID()
                    && (record.getQuestID() == eighth.getQuestID())))
            {
                assertEquals(eighth.getObjectiveID(), record.getObjectiveID());
                assertEquals(eighth.getState(), record.getState());
                assertEquals(eighth.getQuestID(), record.getQuestID());
                assertEquals(eighth.isNeedingNotification(),
                        record.isNeedingNotification());
            }
            else if ((record.getObjectiveID() == ninth.getObjectiveID()
                    && (record.getQuestID() == ninth.getQuestID())))
            {
                assertEquals(ninth.getObjectiveID(), record.getObjectiveID());
                assertEquals(ninth.getState(), record.getState());
                assertEquals(ninth.getQuestID(), record.getQuestID());
                assertEquals(ninth.isNeedingNotification(),
                        record.isNeedingNotification());
            }
            else
            {
                fail("Unexpected objective state");
            }
        }
    }

    /**
     * Make sure we can retrieve all uncompleted objectives for a given player
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void retrieveAllUncompletedObjectivesForPlayer() throws DatabaseException
    {
        gateway = getGateway();
        ArrayList<ObjectiveStateRecordDTO> records =
                gateway.getPendingObjectivesForPlayer(1);

        for (ObjectiveStateRecordDTO record : records)
        {
            boolean yes = (record.getState() == ObjectiveStateEnum.TRIGGERED) ||
                    (record.getState() == ObjectiveStateEnum.HIDDEN);

            assertTrue(yes);
        }
    }

    /**
     * Make sure that nothing goes wrong if we try to retrieve pending
     * objectives and there aren't any
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void retrievePendingObjectivesForPlayerWithNone() throws DatabaseException
    {
        gateway = getGateway();
        ArrayList<ObjectiveStateRecordDTO> records =
                gateway.getPendingObjectivesForPlayer(3);
        assertEquals(0, records.size());
    }

    /**
     * Make sure that nothing goes wrong if we try to retrieve uncompleted
     * objectives and there aren't any
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void retrieveUncompletedObjectivesForPlayerWithNone() throws DatabaseException
    {
        gateway = getGateway();
        ArrayList<ObjectiveStateRecordDTO> records =
                gateway.getUncompletedObjectivesForPlayer(3);
        assertEquals(0, records.size());
    }

    /**
     * If a quest has no quests, we should return an empty list
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void returnsEmptyListIfNone() throws DatabaseException
    {
        gateway = getGateway();
        ArrayList<ObjectiveStateRecordDTO> actual = gateway.getObjectiveStates(109, 1);
        assertEquals(0, actual.size());
    }
}
