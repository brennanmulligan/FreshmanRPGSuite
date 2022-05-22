package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataDTO.QuestStateRecordDTO;
import datatypes.QuestStateEnum;
import datatypes.QuestStatesForTest;

/**
 * An abstract class that tests the table data gateways into the Objective table
 *
 * @author merlin
 *
 */
public abstract class QuestStateTableDataGatewayTest
{

	protected QuestStateTableDataGateway gateway;


	/**
	 * @return the gateway we should test
	 */
	public abstract QuestStateTableDataGateway findGateway();

	/**
	 *
	 */
	@Test
	public void isASingleton()
	{
		QuestStateTableDataGateway x = findGateway();
		QuestStateTableDataGateway y = findGateway();
		assertSame(x, y);
		assertNotNull(x);
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrieveAllObjectivesForQuest() throws DatabaseException
	{
		setup();
		ArrayList<QuestStateRecordDTO> records = gateway.getQuestStates(1);
		assertEquals(8, records.size());
		// the records could be in any order
		for (int i = 0; i < 5; i++)
		{
			QuestStateRecordDTO record = records.get(i);

			if (record.getQuestID() == QuestStatesForTest.PLAYER1_QUEST2.getQuestID())
			{
				QuestStatesForTest expected = QuestStatesForTest.PLAYER1_QUEST2;
				assertEquals("Invalid state for quest id " + record.getQuestID(), expected.getState(),
						record.getState());
			}
		}

	}

	/**
	 * make sure the data is initialized
	 */
	@Before
	public void setup()
	{
		gateway = findGateway();
		gateway.resetTableGateway();
	}

	/**
	 * We should be able to add new rows to the table
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canInsertARecord() throws DatabaseException
	{
		gateway.createRow(QuestStatesForTest.PLAYER1_QUEST1.getPlayerID(), 4, QuestStateEnum.TRIGGERED, true);
		ArrayList<QuestStateRecordDTO> actual = gateway.getQuestStates(QuestStatesForTest.PLAYER1_QUEST1.getPlayerID());
		assertEquals(9, actual.size());
		assertTrue(actual.contains(new QuestStateRecordDTO(QuestStatesForTest.PLAYER1_QUEST1.getPlayerID(), 4,
				QuestStateEnum.TRIGGERED, true)));

	}

	/**
	 * The combination of player ID and quest ID should be unique in the table.
	 * If we try to add a duplicate, we should get a database exception
	 *
	 * @throws DatabaseException should!
	 */
	@Test(expected = DatabaseException.class)
	public void cannotInsertDuplicateData() throws DatabaseException
	{
		gateway.createRow(QuestStatesForTest.PLAYER1_QUEST1.getPlayerID(),
				QuestStatesForTest.PLAYER1_QUEST1.getQuestID(), QuestStateEnum.TRIGGERED, true);
	}

	/**
	 * If a player has no quests, we should return an empty list
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void returnsEmptyListIfNone() throws DatabaseException
	{
		ArrayList<QuestStateRecordDTO> actual = gateway.getQuestStates(10);
		assertEquals(0, actual.size());
	}

	/**
	 * @throws DatabaseException shouldn't
	 *
	 */
	@Test
	public void canChangeExistingState() throws DatabaseException
	{
		int playerID = QuestStatesForTest.PLAYER1_QUEST1.getPlayerID();
		int questID = QuestStatesForTest.PLAYER1_QUEST1.getQuestID();
		gateway.udpateState(playerID, questID, QuestStateEnum.COMPLETED, true);

		ArrayList<QuestStateRecordDTO> actual = gateway.getQuestStates(playerID);
		for (QuestStateRecordDTO qsRec : actual)
		{
			if ((qsRec.getPlayerID() == playerID) && (qsRec.getQuestID() == questID))
			{
				assertEquals(QuestStateEnum.COMPLETED, qsRec.getState());
				assertTrue(qsRec.isNeedingNotification());
			}
		}
	}

	/**
	 * If we try to update a quest state that doesn't exist, it should be added
	 *
	 * @throws DatabaseException should
	 */
	@Test
	public void updatingNonExistentQuestException() throws DatabaseException
	{
		int playerID = 10;
		int questID = QuestStatesForTest.PLAYER1_QUEST1.getQuestID();
		gateway.udpateState(playerID, questID, QuestStateEnum.COMPLETED, true);
		ArrayList<QuestStateRecordDTO> actual = gateway.getQuestStates(playerID);
		assertEquals(1, actual.size());
		for (QuestStateRecordDTO qsRec : actual)
		{
			if ((qsRec.getPlayerID() == playerID) && (qsRec.getQuestID() == questID))
			{
				assertEquals(QuestStateEnum.COMPLETED, qsRec.getState());
				assertTrue(qsRec.isNeedingNotification());
			}
		}
	}

	/**
	 * Tests retrieving all quest states from the gateway
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testRetrievingAllQuestStates() throws DatabaseException
	{
		QuestStateTableDataGateway questState =
				(QuestStateTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
						"QuestState");
		ArrayList<QuestStateRecordDTO> quest = questState.retrieveAllQuestStates();
		assertEquals(QuestStatesForTest.values().length, quest.size());
	}

	/**
	 * Tests that the first and last items in the list are correct
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testCorrectDataInList() throws DatabaseException
	{
		QuestStateTableDataGateway gateway =
				(QuestStateTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
						"QuestState");
		ArrayList<QuestStateRecordDTO> quest = gateway.retrieveAllQuestStates();
		ArrayList<QuestStateRecordDTO> enumQuestStates = QuestStatesForTest.getAllQuestStateRecordDTOs();
		assertTrue(quest.containsAll(enumQuestStates));
	}

}
