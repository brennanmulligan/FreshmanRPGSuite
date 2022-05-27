package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.Position;
import datatypes.InteractableItemsForTest;
import datatypes.PlayersForTest;

/**
 * Test for InteractObjectManager
 * @author Andy Kim, Truc Chau, Jacob Knight, and Emmanuel Douge
 *
 */
public class InteractObjectManagerTest extends ServerSideTest
{

	/**
	 * Reset singleton before test
	 */
	@Before
	public void reset()
	{
		QualifiedObservableConnector.resetSingleton();
		InteractObjectManager.resetSingleton();
	}

	/**
	 * Tests that getSingleton() returns the same InteractObjectManager object
	 */
	@Test
	public void testSingleton()
	{
		InteractObjectManager interactObject = InteractObjectManager.getSingleton();
		InteractObjectManager interactObjectSecond = InteractObjectManager.getSingleton();

		assertSame(interactObject, interactObjectSecond);
		InteractObjectManager.resetSingleton();
		assertNotSame(interactObject, InteractObjectManager.getSingleton());

	}

	/**
	 * Tests execute returns false if no player on map
	 */
	@Test
	public void testExecuteWithoutPlayer()
	{
		InteractObjectManager man = InteractObjectManager.getSingleton();
		assertFalse(man.execute(PlayersForTest.ANDY.getPlayerID(), InteractableItemsForTest.BOOK.getItemID()));

	}

	/**
	 * Tests that if the player position is NOT near the object locations
	 * Should return false
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPlayerIsNOTInObjectRange() throws DatabaseException
	{
		//Marty's default position is 10,19
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
		Position playerPosition = new Position(100, 100); //set up new position for the player
		player.setPlayerPosition(playerPosition);

		int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MARTY.getPlayerID());
		assertEquals(-1, result);
	}

	/**
	 * Tests that if the player position is near the object locations
	 * Should return true
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPlayerIsInObjectRange() throws DatabaseException
	{
		//Marty's default position is 10,19
		//Default position of interactable item on the same map with Marty in the mock version is at (1,1)
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
		Position playerPosition = new Position(1, 1); //set new position for the player at (1,1)
		player.setPlayerPosition(playerPosition);

		int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MARTY.getPlayerID());
		assertTrue(result >= 0);

	}

	/**
	 * Tests the collision between player at position (0,1) and other objects on the same map
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPlayerPosition01() throws DatabaseException
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
		Position playerPosition = new Position(0, 1);
		player.setPlayerPosition(playerPosition);

		int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MARTY.getPlayerID());
		assertTrue(result >= 0);
	}

	/**
	 * Tests the collision between player at position(0,2) and other objects on the same map
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPlayerPosition02() throws DatabaseException
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
		Position playerPosition = new Position(0, 2);
		player.setPlayerPosition(playerPosition);

		int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MARTY.getPlayerID());
		assertTrue(result >= 0);
	}


	/**
	 * Tests the collision between player at position(0,3) and other objects on the same map
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPlayerPosition03() throws DatabaseException
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
		Position playerPosition = new Position(0, 3);
		player.setPlayerPosition(playerPosition);

		int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MARTY.getPlayerID());
		assertFalse(result >= 0);
	}

	/**
	 * Tests the collision between player at position(1,0) and other objects on the same map
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPlayerPosition10() throws DatabaseException
	{

		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
		Position playerPosition = new Position(1, 0);
		player.setPlayerPosition(playerPosition);

		int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MARTY.getPlayerID());
		assertTrue(result >= 0);
	}

	/**
	 * Tests the collision between player at position(2,0) and other objects on the same map
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPlayerPosition20() throws DatabaseException
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
		Position playerPosition = new Position(2, 0);
		player.setPlayerPosition(playerPosition);

		int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MARTY.getPlayerID());
		assertTrue(result >= 0);
	}

	/**
	 * Tests the collision between player at position(3,0) and other objects on the same map
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPlayerPosition30() throws DatabaseException
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
		Position playerPosition = new Position(3, 0);
		player.setPlayerPosition(playerPosition);

		int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MARTY.getPlayerID());
		assertFalse(result >= 0);
	}

	/**
	 * Tests that an item can pass an Buff Pool into a Player.
	 * Using test item 4 for this purpose which has a buff pool size of 50.
	 */
	@Test
	public void testPlayerBuffPool()
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
		Position playerPosition = new Position(3, 0);
		player.setPlayerPosition(playerPosition);

		InteractObjectManager.getSingleton().execute(player.getPlayerID(), 4);

		assertEquals(player.getBuffPool(), 50);
	}

}
