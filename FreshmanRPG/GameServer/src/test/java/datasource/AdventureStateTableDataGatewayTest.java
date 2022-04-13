package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataDTO.AdventureStateRecordDTO;
import datatypes.AdventureStateEnum;
import datatypes.AdventureStatesForTest;

/**
 * An abstract class that tests the table data gateways into the Adventure table
 *
 * @author merlin
 *
 */
public abstract class AdventureStateTableDataGatewayTest extends DatabaseTest
{

	protected AdventureStateTableDataGateway gateway;

	/**
	 * Makes sure to reset gateway
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
		this.getGateway().resetData();
	}

	/**
	 * Make sure any static information is cleaned up between tests
	 *
	 * @throws SQLException shouldn't
	 * @throws DatabaseException shouldn't
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * @return the gateway we should test
	 */
	public abstract AdventureStateTableDataGateway getGateway();

	/**
	 *
	 */
	@Test
	public void isASingleton()
	{
		AdventureStateTableDataGateway x = getGateway();
		AdventureStateTableDataGateway y = getGateway();
		assertSame(x, y);
		assertNotNull(x);
	}

	/**
	 * Make sure we can retrieve all pending adventures for a given player
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrieveAllPendingAdventuresForPlayer() throws DatabaseException
	{
		gateway = getGateway();
		ArrayList<AdventureStateRecordDTO> records = gateway.getPendingAdventuresForPlayer(1);
		assertEquals(6, records.size());
		// the records could be in either order
		AdventureStatesForTest first = AdventureStatesForTest.PLAYER1_QUEST2_ADV3;
		AdventureStatesForTest other = AdventureStatesForTest.PLAYER1_QUEST3_ADV1;
		AdventureStatesForTest third = AdventureStatesForTest.PLAYER1_QUEST3_ADV3;
		AdventureStatesForTest fourth = AdventureStatesForTest.PLAYER1_QUEST1_ADV1;
		AdventureStatesForTest fifth = AdventureStatesForTest.PLAYER1_QUEST4_ADV1;
		AdventureStatesForTest sixth = AdventureStatesForTest.PLAYER1_QUEST4_ADV2;
		for (AdventureStateRecordDTO record : records)
		{
			if ((record.getAdventureID() == first.getAdventureID() && (record.getQuestID() == first.getQuestID())))
			{
				assertEquals(first.getState(), record.getState());
				assertEquals(first.getQuestID(), record.getQuestID());
				assertEquals(first.isNeedingNotification(), record.isNeedingNotification());

			}
			else if ((record.getAdventureID() == other.getAdventureID()
					&& (record.getQuestID() == other.getQuestID())))
			{
				assertEquals(other.getAdventureID(), record.getAdventureID());
				assertEquals(other.getState(), record.getState());
				assertEquals(other.getQuestID(), record.getQuestID());
				assertEquals(other.isNeedingNotification(), record.isNeedingNotification());
			}
			else if ((record.getAdventureID() == third.getAdventureID()
					&& (record.getQuestID() == third.getQuestID())))
			{
				assertEquals(third.getAdventureID(), record.getAdventureID());
				assertEquals(third.getState(), record.getState());
				assertEquals(third.getQuestID(), record.getQuestID());
				assertEquals(third.isNeedingNotification(), record.isNeedingNotification());
			}
			else if ((record.getAdventureID() == fourth.getAdventureID()
					&& (record.getQuestID() == fourth.getQuestID())))
			{
				assertEquals(fourth.getAdventureID(), record.getAdventureID());
				assertEquals(fourth.getState(), record.getState());
				assertEquals(fourth.getQuestID(), record.getQuestID());
				assertEquals(fourth.isNeedingNotification(), record.isNeedingNotification());
			}
			else if ((record.getAdventureID() == fifth.getAdventureID()
					&& (record.getQuestID() == fifth.getQuestID())))
			{
				assertEquals(fifth.getAdventureID(), record.getAdventureID());
				assertEquals(fifth.getState(), record.getState());
				assertEquals(fifth.getQuestID(), record.getQuestID());
				assertEquals(fifth.isNeedingNotification(), record.isNeedingNotification());
			}
			else if ((record.getAdventureID() == sixth.getAdventureID()
					&& (record.getQuestID() == sixth.getQuestID())))
			{
				assertEquals(sixth.getAdventureID(), record.getAdventureID());
				assertEquals(sixth.getState(), record.getState());
				assertEquals(sixth.getQuestID(), record.getQuestID());
				assertEquals(sixth.isNeedingNotification(), record.isNeedingNotification());
			}
			else
			{
				fail("Unexpected adventure state");
			}
		}
	}

	/**
	 * Make sure that nothing goes wrong if we try to retrieve pending
	 * adventures and there aren't any
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrievePendingAdventuresForPlayerWithNone() throws DatabaseException
	{
		gateway = getGateway();
		ArrayList<AdventureStateRecordDTO> records = gateway.getPendingAdventuresForPlayer(3);
		assertEquals(0, records.size());
	}

	/**
	 * Make sure that nothing goes wrong if we try to retrieve uncompleted
	 * adventures and there aren't any
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrieveUncompletedAdventuresForPlayerWithNone() throws DatabaseException
	{
		gateway = getGateway();
		ArrayList<AdventureStateRecordDTO> records = gateway.getUncompletedAdventuresForPlayer(3);
		assertEquals(0, records.size());
	}

	/**
	 * Make sure we can retrieve all uncompleted adventures for a given player
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrieveAllUncompletedAdventuresForPlayer() throws DatabaseException
	{
		gateway = getGateway();
		ArrayList<AdventureStateRecordDTO> records = gateway.getPendingAdventuresForPlayer(1);

		for (AdventureStateRecordDTO record : records)
		{
			boolean yes = (record.getState() == AdventureStateEnum.TRIGGERED) ||
					(record.getState() == AdventureStateEnum.HIDDEN);

			assertTrue(yes);
		}
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrieveAllAdventuresForQuest() throws DatabaseException
	{
		gateway = getGateway();
		ArrayList<AdventureStateRecordDTO> records = gateway.getAdventureStates(1, 2);
		assertEquals(3, records.size());
		AdventureStateRecordDTO record = records.get(0);
		// the records could be in either order
		AdventureStatesForTest first = AdventureStatesForTest.PLAYER1_QUEST2_ADV1;
		AdventureStatesForTest other = AdventureStatesForTest.PLAYER1_QUEST2_ADV2;
		if (record.getAdventureID() == first.getAdventureID())
		{
			assertEquals(first.getState(), record.getState());
			assertEquals(first.getQuestID(), record.getQuestID());
			record = records.get(1);
			assertEquals(other.getState(), record.getState());
			assertEquals(other.getQuestID(), record.getQuestID());
		}
		else
		{
			assertEquals(other.getAdventureID(), record.getAdventureID());
			assertEquals(other.getState(), record.getState());
			assertEquals(other.getQuestID(), record.getQuestID());
			record = records.get(1);
			assertEquals(first.getState(), record.getState());
			assertEquals(first.getQuestID(), record.getQuestID());
		}
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
		ArrayList<AdventureStateRecordDTO> actual = gateway.getAdventureStates(109, 1);
		assertEquals(0, actual.size());
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
		AdventureStatesForTest adv = AdventureStatesForTest.PLAYER2_QUEST1_ADV3;
		gateway.updateState(adv.getPlayerID(), adv.getQuestID(), adv.getAdventureID(), AdventureStateEnum.COMPLETED,
				false);

		ArrayList<AdventureStateRecordDTO> actual = gateway.getAdventureStates(adv.getPlayerID(), adv.getQuestID());
		int count = 0;
		for (AdventureStateRecordDTO asRec : actual)
		{
			if (asRec.getQuestID() == adv.getQuestID() && asRec.getAdventureID() == adv.getAdventureID())
			{
				count = count + 1;
				assertEquals(AdventureStateEnum.COMPLETED, asRec.getState());
				assertFalse(asRec.isNeedingNotification());
			}
		}
		assertEquals(1, count);
	}

	/**
	 * Updating a non-existing record adds it to the data source
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canAddNew() throws DatabaseException
	{
		gateway = getGateway();
		gateway.updateState(13, 22, 11, AdventureStateEnum.TRIGGERED, true);
		ArrayList<AdventureStateRecordDTO> actual = gateway.getAdventureStates(13, 22);
		assertEquals(1, actual.size());
		AdventureStateRecordDTO asRec = actual.get(0);
		assertEquals(13, asRec.getPlayerID());
		assertEquals(22, asRec.getQuestID());
		assertEquals(11, asRec.getAdventureID());
		assertEquals(AdventureStateEnum.TRIGGERED, asRec.getState());
		assertTrue(asRec.isNeedingNotification());

	}
}
