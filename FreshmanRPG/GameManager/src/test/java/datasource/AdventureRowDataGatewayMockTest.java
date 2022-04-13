package datasource;

import java.sql.SQLException;

import org.junit.Before;

import criteria.AdventureCompletionCriteria;
import dataENUM.AdventureCompletionType;
import model.OptionsManager;

/**
 * Tests for mock version of gateway.
 */
public class AdventureRowDataGatewayMockTest extends AdventureRowDataGatewayTest
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
	 * @see datasource.AdventureRowDataGatewayTest#createGateway(int,
	 *      java.lang.String, int, int, dataENUM.AdventureCompletionType,
	 *      criteria.AdventureCompletionCriteria)
	 */
	@Override
	void createGateway(int adventureId, String adventureDescription, int questId, int experiencePointsGained,
					   AdventureCompletionType adventureCompletionType, AdventureCompletionCriteria adventureCompletionCriteria)
			throws DatabaseException
	{
		new AdventureRowDataGatewayMock(adventureId, adventureDescription, questId, experiencePointsGained,
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
		final AdventureRowDataGatewayMock gateway = new AdventureRowDataGatewayMock(adventureDescription, questId,
				experiencePointsGained, adventureCompletionType, adventureCompletionCriteria);
		return gateway.getAdventureId();
	}

	/**
	 * @see datasource.AdventureRowDataGatewayTest#findGateway(int, int)
	 */
	@Override
	AdventureRowDataGateway findGateway(int questId, int adventureId) throws DatabaseException
	{
		return new AdventureRowDataGatewayMock(questId, adventureId);
	}

}
