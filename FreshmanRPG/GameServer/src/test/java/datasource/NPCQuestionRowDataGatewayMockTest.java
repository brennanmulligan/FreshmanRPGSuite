package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

/**
 * Tests for the mock version of the gateway
 *
 * @author Merlin
 *
 */
public class NPCQuestionRowDataGatewayMockTest extends NPCQuestionRowDataGatewayTest
{

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp()
	{

	}

	/**
	 * @see datasource.NPCQuestionRowDataGatewayTest#tearDown()
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
	 * @see datasource.NPCQuestionRowDataGatewayTest#findGateway(int)
	 */
	@Override
	NPCQuestionRowDataGateway findGateway(int questionID) throws DatabaseException
	{
		return new NPCQuestionRowDataGatewayMock(questionID);
	}

	/**
	 * @see datasource.NPCQuestionRowDataGatewayTest#findRandomGateway()
	 */
	@Override
	public NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException
	{
		return NPCQuestionRowDataGatewayMock.findRandomGateway();
	}

}
