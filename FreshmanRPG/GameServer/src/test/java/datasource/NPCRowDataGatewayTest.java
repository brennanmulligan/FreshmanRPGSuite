package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import datatypes.NPCsForTest;

/**
 * Tests required of all player gateways
 *
 * @author Merlin
 *
 */
public abstract class NPCRowDataGatewayTest extends DatabaseTest
{

	protected NPCRowDataGateway gateway;

	/**
	 * Find a gateway for a given player ID. Allows these tests to be subclassed
	 * for each type of gateway ensuring they all work the same.
	 *
	 * @param playerID the player's unique ID
	 * @return the gateway
	 * @throws DatabaseException if the playerID can't be found int the
	 *             datasource
	 */
	abstract NPCRowDataGateway findGateway(int playerID) throws DatabaseException;

	/**
	 * Make sure any static information is cleaned up between tests
	 *
	 * @throws SQLException shouldn't
	 * @throws DatabaseException shouldn't
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void finder() throws DatabaseException
	{
		NPCsForTest merlin = NPCsForTest.NPC1;
		gateway = findGateway(merlin.getPlayerID());
		assertEquals(merlin.getBehaviorClass(), gateway.getBehaviorClass());
		assertEquals(merlin.getPlayerID(), gateway.getPlayerID());
	}

	/**
	 * make sure we get the right exception if we try to find someone who
	 * doesn't exist
	 *
	 * @throws DatabaseException should
	 */
	@Test(expected = DatabaseException.class)
	public void findNotExisting() throws DatabaseException
	{
		gateway = findGateway(11111);
	}

	/**
	 * Get gateways for all of the NPCs on a given map
	 *
	 * @param mapName the map we are interested
	 * @return the gateways we found
	 * @throws DatabaseException if the data source layer has trouble
	 */
	public abstract ArrayList<NPCRowDataGateway> getAllForMap(String mapName) throws DatabaseException;

	/**
	 * Make sure that we can find all of the NPCs for a given map
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void findsAllForMap() throws DatabaseException
	{
		// just make sure the data set is clean using a known playerID
		findGateway(NPCsForTest.NPC1.getPlayerID()).resetData();
		ArrayList<NPCRowDataGateway> gateways = getAllForMap("silly.tmx");
		assertEquals(2, gateways.size());
		assertTrue((NPCsForTest.NPC1.getPlayerID() == gateways.get(0).getPlayerID()
				|| NPCsForTest.NPC1.getPlayerID() == gateways.get(1).getPlayerID()));
		assertTrue((NPCsForTest.NPC3.getPlayerID() == gateways.get(0).getPlayerID()
				|| NPCsForTest.NPC3.getPlayerID() == gateways.get(1).getPlayerID()));
	}


}
