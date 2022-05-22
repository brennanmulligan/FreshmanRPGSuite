package datasource;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import datatypes.QuestStatesForTest;
import model.OptionsManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
	@BeforeClass
	public static void hardReset()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	@Before
	public void setUp() throws DatabaseException
	{
		QuestStateTableDataGatewayMock gateway =
				(QuestStateTableDataGatewayMock) findGateway();
		gateway.resetTableGateway();
	}

	/**
	 * @see datasource.QuestStateTableDataGatewayTest#findGateway()
	 */
	@Override
	public QuestStateTableDataGateway findGateway()
	{
		return (QuestStateTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"QuestState");
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
		ArrayList<QuestStateRecordDTO> quest =
				((QuestStateTableDataGateway)TableDataGatewayManager.getSingleton().getTableGateway(
						"QuestState")).retrieveAllQuestStates();
		assertEquals(QuestStatesForTest.values().length, quest.size());

	}

}
