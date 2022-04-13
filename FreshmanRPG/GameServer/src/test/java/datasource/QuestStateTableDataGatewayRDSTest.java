package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import dataDTO.QuestStateRecordDTO;
import datatypes.QuestStateEnum;
import datatypes.QuestStatesForTest;

/**
 * Tests the mock implementation
 *
 * @author merlin
 */
public class QuestStateTableDataGatewayRDSTest extends QuestStateTableDataGatewayTest
{

	/**
	 * @see datasource.QuestStateTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public QuestStateTableDataGateway getGatewaySingleton()
	{
		return QuestStateTableDataGatewayRDS.getSingleton();
	}

	/**
	 * Tests retrieving all quest states from the gateway
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testRetrievingAllQuestStates() throws DatabaseException
	{
		QuestStateTableDataGateway questState = QuestStateTableDataGatewayRDS.getSingleton();
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
		QuestStateTableDataGateway gateway = QuestStateTableDataGatewayRDS.getSingleton();
		ArrayList<QuestStateRecordDTO> quest = gateway.retrieveAllQuestStates();
		ArrayList<QuestStateRecordDTO> enumQuestStates = QuestStatesForTest.getAllQuestStateRecordDTOs();
		assertTrue(quest.containsAll(enumQuestStates));
	}

	/**
	 * Tests the functionality of deleting a quest state
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testDeleteQuestState() throws DatabaseException
	{
		QuestStateTableDataGateway gateway = QuestStateTableDataGatewayRDS.getSingleton();
		ArrayList<QuestStateRecordDTO> listOfQuestStates = gateway.retrieveAllQuestStates();

		int startSize = listOfQuestStates.size();
		gateway.deleteQuestState(100);
		listOfQuestStates = gateway.retrieveAllQuestStates();

		assertEquals(startSize - 1, listOfQuestStates.size());
		assertEquals(QuestStateEnum.AVAILABLE, listOfQuestStates.get(0).getState());
		assertEquals(QuestStateEnum.COMPLETED, listOfQuestStates.get(21).getState());
	}
}
