package datasource;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import datatypes.PlayersForTest;

/**
 * test the visited maps gateway RDS
 *
 */
public class VisitedMapsGatewayRDSTest extends DatabaseTest
{
	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
	}

	/**
	 * @see datasource.DatabaseTest#tearDown()
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
	}

	/**
	 * test that we can get the maps a player has visited
	 * @throws DatabaseException if error occurs during database access
	 */
	@Test
	public void testGetVisitedMaps() throws DatabaseException
	{
		ArrayList<String> testList = new ArrayList<>();
		testList.add("Quiznasium");
		testList.add("TheGreen");

		VisitedMapsGatewayRDS gateway = new VisitedMapsGatewayRDS(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(testList, gateway.getMaps());
	}

	/**
	 * test that we can add a map to a player
	 * @throws DatabaseException if error occurs during database access
	 */
	@Test
	public void testAddMapToPlayer() throws DatabaseException
	{
		ArrayList<String> testList = new ArrayList<>();
		testList.add("Quiznasium");
		testList.add("TheGreen");
		testList.add("Homework");

		VisitedMapsGatewayRDS gateway = new VisitedMapsGatewayRDS(PlayersForTest.MERLIN.getPlayerID(), "homeWork");

		assertEquals(testList, gateway.getMaps());

	}
}
