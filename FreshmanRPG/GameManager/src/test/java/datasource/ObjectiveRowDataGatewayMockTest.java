package datasource;

import java.sql.SQLException;

import dataENUM.ObjectiveCompletionType;
import org.junit.Before;

import criteria.ObjectiveCompletionCriteria;
import model.OptionsManager;

/**
 * Tests for mock version of gateway.
 */
public class ObjectiveRowDataGatewayMockTest extends ObjectiveRowDataGatewayTest
{

	/**
	 *
	 */
	@Before
	public void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * @see datasource.DatabaseTest#tearDown()
	 */
	public void tearDown() throws DatabaseException, SQLException
	{

	}

	/**
	 * @see ObjectiveRowDataGatewayTest#createGateway(int,
	 *      java.lang.String, int, int, ObjectiveCompletionType,
	 *      ObjectiveCompletionCriteria)
	 */
	@Override
	void createGateway(int objectiveId, String objectiveDescription, int questId, int experiencePointsGained,
                       ObjectiveCompletionType objectiveCompletionType, ObjectiveCompletionCriteria objectiveCompletionCriteria)
			throws DatabaseException
	{
		new ObjectiveRowDataGatewayMock(objectiveId, objectiveDescription, questId, experiencePointsGained,
                objectiveCompletionType, objectiveCompletionCriteria);
	}

	/**
	 * @see ObjectiveRowDataGatewayTest#createGatewayNoID(java.lang.String,
	 *      int, int, ObjectiveCompletionType,
	 *      ObjectiveCompletionCriteria)
	 */
	@Override
	int createGatewayNoID(String objectiveDescription, int questId, int experiencePointsGained,
                          ObjectiveCompletionType objectiveCompletionType, ObjectiveCompletionCriteria objectiveCompletionCriteria)
			throws DatabaseException
	{
		final ObjectiveRowDataGatewayMock gateway = new ObjectiveRowDataGatewayMock(objectiveDescription, questId,
				experiencePointsGained, objectiveCompletionType, objectiveCompletionCriteria);
		return gateway.getObjectiveID();
	}

	/**
	 * @see ObjectiveRowDataGatewayTest#findGateway(int, int)
	 */
	@Override
    ObjectiveRowDataGateway findGateway(int questId, int objectiveId) throws DatabaseException
	{
		return new ObjectiveRowDataGatewayMock(questId, objectiveId);
	}

}
