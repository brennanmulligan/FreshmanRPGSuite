package model;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.Position;

/**
 * Test that a player is persisted
 *
 * @author Steve
 *
 */
public class CommandPersistPlayerTest extends ServerSideTest
{

	/**
	 * Reset singletons
	 */
	@Before
	public void localSetUp()
	{
		PlayerManager.resetSingleton();
	}

	/**
	 * Test that persistence happens
	 *
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testPersists() throws DatabaseException, IllegalQuestChangeException
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		player.setPlayerPositionWithoutNotifying(new Position(101, 101));
		player.setAppearanceType("appearance");
		PlayerManager.getSingleton().persistPlayer(player.getPlayerID());

		PlayerManager.resetSingleton();

		Player fetched = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(player.getPlayerPosition(), fetched.getPlayerPosition());
		assertEquals(player.getAppearanceType(), fetched.getAppearanceType());
	}

}
