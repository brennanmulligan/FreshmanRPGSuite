package datasource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;

import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datatypes.Position;

/**
 * Tests for the mock version of the gateway
 *
 * @author Merlin
 *
 */
public class QuestRowDataGatewayMockTest extends QuestRowDataGatewayTest
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
	 *
	 * @see datasource.QuestRowDataGatewayTest#findGateway(int)
	 */
	@Override
	QuestRowDataGateway findGateway(int questionID) throws DatabaseException
	{
		return new QuestRowDataGatewayMock(questionID);
	}

	/**
	 * @see datasource.QuestRowDataGatewayTest#findQuestsForMapLocation(java.lang.String,
	 *      datatypes.Position)
	 */
	@Override
	ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position) throws DatabaseException
	{
		return QuestRowDataGatewayMock.findQuestsForMapLocation(mapName, position);
	}

	@Override
	int createGateway(String title, String description, String mapName, Position position, int experiencedGained,
					  int adventuresForFullfillment, QuestCompletionActionType completionActionType,
					  QuestCompletionActionParameter completionActionParameter, Date startDate, Date endDate)
			throws DatabaseException
	{
		QuestRowDataGatewayMock gateway = new QuestRowDataGatewayMock(title, description, mapName, position,
				experiencedGained, adventuresForFullfillment, completionActionType, completionActionParameter,
				startDate, endDate);

		return gateway.getQuestID();
	}
}
