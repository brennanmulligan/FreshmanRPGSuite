package datasource;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import datatypes.QuestStatesForTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataDTO.QuestStateRecordDTO;

/**
 * Tests the mock implementation
 *
 * @author merlin
 *
 */
public class QuestStateTableDataGatewayMockTest extends QuestStateTableDataGatewayTest
{
	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp()
	{

	}

	/**
	 * Make sure any static information is cleaned up between tests
	 *
	 * @throws SQLException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * @see datasource.QuestStateTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public QuestStateTableDataGateway getGatewaySingleton()
	{
		return QuestStateTableDataGatewayMock.getSingleton();
	}

	/**
	 * Tests the retrieveAllQuestStates through the mock table data gateway
	 *
	 * @throws DatabaseException
	 *             if there is an issue in retrieveing all of the states
	 */
	@Test
	public void retrieveAllQuestStates() throws DatabaseException
	{
		ArrayList<QuestStateRecordDTO> quest = QuestStateTableDataGatewayMock.getSingleton().retrieveAllQuestStates();
		assertEquals(QuestStatesForTest.values().length, quest.size());

	}

}
