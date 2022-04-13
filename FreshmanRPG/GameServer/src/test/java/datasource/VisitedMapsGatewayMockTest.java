package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import datatypes.PlayersForTest;

/**
 * test the visited map mock gateway
 *
 */
public class VisitedMapsGatewayMockTest
{
	/**
	 * test that we can get the maps a player has visited
	 */
	@Test
	public void testGetVisitedMaps()
	{
		VisitedMapsGatewayMock gateway = new VisitedMapsGatewayMock(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(PlayersForTest.MERLIN.getMapsVisited(), gateway.getMaps());
	}

	/**
	 * test that we can add a map to a player
	 */
	@Test
	public void testAddMapToPlayer()
	{
		ArrayList<String> list = new ArrayList<>(PlayersForTest.MERLIN.getMapsVisited());
		list.add("FunnyRoom");
		assertFalse(list.equals(PlayersForTest.MERLIN.getMapsVisited()));

		VisitedMapsGatewayMock gateway = new VisitedMapsGatewayMock(PlayersForTest.MERLIN.getPlayerID(), "FunnyRoom");
		assertTrue(list.equals(gateway.getMaps()));

		PlayersForTest.MERLIN.removeMapVisited("FunnyRoom");
	}


}
