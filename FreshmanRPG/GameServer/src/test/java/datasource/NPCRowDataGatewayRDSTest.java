package datasource;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import datatypes.NPCsForTest;
import datatypes.PlayersForTest;

/**
 * Tests for the RDS version of the gateway
 *
 * @author Merlin
 *
 */
public class NPCRowDataGatewayRDSTest extends NPCRowDataGatewayTest
{

	/**
	 * @see datasource.NPCRowDataGatewayTest#findGateway(int)
	 */
	@Override
	NPCRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new NPCRowDataGatewayRDS(playerID);
	}

	/**
	 * @see datasource.NPCRowDataGatewayTest#getAllForMap(java.lang.String)
	 */
	@Override
	public ArrayList<NPCRowDataGateway> getAllForMap(String mapName) throws DatabaseException
	{
		return NPCRowDataGatewayRDS.getNPCsForMap(mapName);
	}

	/**
	 * Test that a NPC gateway is created
	 * @throws DatabaseException Shouldn't
	 */
	@Test
	public void testCreateConstructor() throws DatabaseException
	{
		PlayersForTest testPlayer = PlayersForTest.FRANK;

		new NPCRowDataGatewayRDS(testPlayer.getPlayerID(), "model.RedHatBehavior");

		NPCRowDataGateway finderTester = new NPCRowDataGatewayRDS(testPlayer.getPlayerID());

		assertEquals(testPlayer.getPlayerID(), finderTester.getPlayerID());
		assertEquals("model.RedHatBehavior", finderTester.getBehaviorClass());
	}

	/**
	 * Test that an exception is thrown
	 * @throws DatabaseException Should
	 */
	@Test(expected = DatabaseException.class)
	public void testDuplicateNotAdded() throws DatabaseException
	{
		NPCsForTest testPlayer = NPCsForTest.NPC1;

		new NPCRowDataGatewayRDS(testPlayer.getPlayerID(), testPlayer.getBehaviorClass());
	}


}
