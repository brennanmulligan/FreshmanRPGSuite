package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import datatypes.PlayersForTest;

/**
 *
 * @author Merlin
 *
 */
public class CommandAddPlayerTest extends ServerSideTest
{

	/**
	 *
	 */
	@Before
	public void localSetup()
	{
		PlayerManager.resetSingleton();

	}

	/**
	 * If we add a player, the playermanager should know about it
	 *
	 * @throws PlayerNotFoundException shouldn't
	 */
	@Test
	public void test() throws PlayerNotFoundException
	{
		CommandAddPlayer cmd = new CommandAddPlayer(PlayersForTest.JOHN.getPlayerID(), PlayersForTest.JOHN.getPin());
		cmd.execute();
		Player player = PlayerManager.getSingleton().getPlayerFromID(1);
		assertNotNull(player);
		assertEquals("John", player.getPlayerName());
	}

}
