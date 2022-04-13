package datasource;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

/**
 * Tests for the mock version of the gateway
 *
 * @author Merlin
 *
 */
public class NPCRowDataGatewayMockTest extends NPCRowDataGatewayTest
{

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp()
	{
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * @see datasource.DatabaseTest#tearDown()
	 */
	@After
	public void tearDown()
	{
	}

	/**
	 * @see datasource.NPCRowDataGatewayTest#findGateway(int)
	 */
	@Override
	NPCRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new NPCRowDataGatewayMock(playerID);
	}

	/**
	 * @see datasource.NPCRowDataGatewayTest#getAllForMap(java.lang.String)
	 */
	@Override
	public ArrayList<NPCRowDataGateway> getAllForMap(String mapName) throws DatabaseException
	{
		return NPCRowDataGatewayMock.getNPCsForMap(mapName);
	}
}
