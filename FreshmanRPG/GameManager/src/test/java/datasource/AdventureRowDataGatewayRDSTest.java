package datasource;

import org.junit.Before;

import criteria.AdventureCompletionCriteria;
import dataENUM.AdventureCompletionType;
import model.OptionsManager;

/**
 * Tests for RDS version of gateway.
 */
public class AdventureRowDataGatewayRDSTest extends AdventureRowDataGatewayTest
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
	 * @see datasource.AdventureRowDataGatewayTest#createGateway(int,
	 *      java.lang.String, int, int, dataENUM.AdventureCompletionType,
	 *      criteria.AdventureCompletionCriteria)
	 */
	@Override
	void createGateway(int adventureId, String adventureDescription, int questId, int experiencePointsGained,
					   AdventureCompletionType adventureCompletionType, AdventureCompletionCriteria adventureCompletionCriteria)
			throws DatabaseException
	{
		new AdventureRowDataGatewayRDS(adventureId, adventureDescription, questId, experiencePointsGained,
				adventureCompletionType, adventureCompletionCriteria);
	}

	/**
	 * @see datasource.AdventureRowDataGatewayTest#createGatewayNoID(java.lang.String,
	 *      int, int, dataENUM.AdventureCompletionType,
	 *      criteria.AdventureCompletionCriteria)
	 */
	@Override
	int createGatewayNoID(String adventureDescription, int questId, int experiencePointsGained,
						  AdventureCompletionType adventureCompletionType, AdventureCompletionCriteria adventureCompletionCriteria)
			throws DatabaseException
	{
		final AdventureRowDataGatewayRDS gateway = new AdventureRowDataGatewayRDS(adventureDescription, questId,
				experiencePointsGained, adventureCompletionType, adventureCompletionCriteria);
		return gateway.getAdventureId();
	}

	/**
	 * @see datasource.AdventureRowDataGatewayTest#findGateway(int, int)
	 */
	@Override
	AdventureRowDataGateway findGateway(int questId, int adventureId) throws DatabaseException
	{
		return new AdventureRowDataGatewayRDS(questId, adventureId);
	}

}
