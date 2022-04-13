package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

/**
 * Tests for the mock version of the gateway
 *
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayMockTest extends PlayerRowDataGatewayTest
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
	 * @see datasource.PlayerRowDataGatewayTest#findGateway(int)
	 */
	@Override
	PlayerRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new PlayerRowDataGatewayMock(playerID);
	}

	/**
	 * @see datasource.PlayerRowDataGatewayTest#createGateway(java.lang.String,
	 *      java.lang.String, int, int, Crew, major, int)
	 */
	@Override
	PlayerRowDataGateway createGateway(String appearanceType, int quizScore, int experiencePoints,
									   Crew crew, Major major, int section, int buffPool, boolean online) throws DatabaseException
	{
		return new PlayerRowDataGatewayMock(appearanceType, quizScore, experiencePoints, crew, major, section,
				buffPool, online);
	}
}
