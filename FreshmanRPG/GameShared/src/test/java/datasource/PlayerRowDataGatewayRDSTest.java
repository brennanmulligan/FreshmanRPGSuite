package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import datatypes.PlayersForTest;

/**
 * Tests for the RDS version of the gateway
 *
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayRDSTest extends PlayerRowDataGatewayTest
{
	/**
	 * @see datasource.PlayerRowDataGatewayTest#findGateway(int)
	 */
	@Override
	PlayerRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new PlayerRowDataGatewayRDS(playerID);
	}

	/**
	 * @see datasource.PlayerRowDataGatewayTest#createGateway(java.lang.String,
	 *      java.lang.String, int, int, datatypes.Crew)
	 */
	@Override
	PlayerRowDataGateway createGateway(String appearanceType, int quizScore,
									   int experiencePoints, Crew crew, Major major, int section, int buffPool, boolean online) throws DatabaseException
	{
		return new PlayerRowDataGatewayRDS(appearanceType, quizScore, experiencePoints, crew, major, section, buffPool, online);
	}


	/**
	 * Tests the delete method in PlayerRowDataGateway
	 * @throws DatabaseException should
	 */
	@Test
	public void testDeletePlayerByIdDeletesCorrectPlayer() throws DatabaseException
	{
		PlayerRowDataGateway firstMerlin = findGateway(PlayersForTest.MERLIN.getPlayerID());
		firstMerlin.delete();

		boolean found = false;
		try
		{
			findGateway(PlayersForTest.MERLIN.getPlayerID());
		}
		catch (DatabaseException e)
		{
			found = true;
		}
		assertTrue(found);
	}


}
