package datasource;

import dataENUM.ObjectiveCompletionType;
import org.junit.Before;

import criteria.ObjectiveCompletionCriteria;
import model.OptionsManager;

/**
 * Tests for RDS version of gateway.
 */
public class ObjectiveRowDataGatewayRDSTest extends ObjectiveRowDataGatewayTest
{

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Before
	public void setup() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		DatabaseManager.getSingleton().setTesting();
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
		new ObjectiveRowDataGatewayRDS(objectiveId, objectiveDescription, questId, experiencePointsGained,
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
		final ObjectiveRowDataGatewayRDS gateway = new ObjectiveRowDataGatewayRDS(objectiveDescription, questId,
				experiencePointsGained, objectiveCompletionType, objectiveCompletionCriteria);
		return gateway.getObjectiveID();
	}

	/**
	 * @see ObjectiveRowDataGatewayTest#findGateway(int, int)
	 */
	@Override
    ObjectiveRowDataGateway findGateway(int questId, int objectiveId) throws DatabaseException
	{
		return new ObjectiveRowDataGatewayRDS(questId, objectiveId);
	}

}
