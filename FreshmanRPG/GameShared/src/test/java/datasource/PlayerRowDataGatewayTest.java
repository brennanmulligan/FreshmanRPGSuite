package datasource;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Test;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import datatypes.PlayersForTest;

/**
 * Tests required of all player gateways
 *
 * @author Merlin
 *
 */
public abstract class PlayerRowDataGatewayTest extends DatabaseTest
{

	protected PlayerRowDataGateway gateway;

	/**
	 * Find the gateway for a given player
	 *
	 * @param playerID the ID of the player we are testing
	 * @return the gateway
	 * @throws DatabaseException if the playerID can't be found in the data
	 *             source
	 */
	abstract PlayerRowDataGateway findGateway(int playerID) throws DatabaseException;

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
		PlayersForTest john = PlayersForTest.JOHN;
		gateway = findGateway(john.getPlayerID());
		assertEquals(john.getPlayerID(), gateway.getPlayerID());

		assertEquals(john.getAppearanceType(), gateway.getAppearanceType());
		assertEquals(john.getExperiencePoints(), gateway.getExperiencePoints());
		assertEquals(john.getCrew(), gateway.getCrew());
		assertEquals(john.getMajor(), gateway.getMajor());
		assertEquals(john.getSection(), gateway.getSection());
		assertEquals(john.getBuffPool(), gateway.getBuffPool());
		assertEquals(john.getOnline(), gateway.getOnline());
	}

	/**
	 * Make sure we can add a new user to the system
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		gateway = createGateway("Warrior", 2, 13, Crew.OFF_BY_ONE,
				Major.SOFTWARE_ENGINEERING, 1, 42, false);

		PlayerRowDataGateway after = findGateway(gateway.getPlayerID());
		assertEquals("Warrior", after.getAppearanceType());
		assertEquals(2, after.getQuizScore());
		assertEquals(13, after.getExperiencePoints());
		assertEquals(Crew.OFF_BY_ONE, after.getCrew());
		assertEquals(Major.SOFTWARE_ENGINEERING, after.getMajor());
		assertEquals(1, after.getSection());
		assertEquals(42, after.getBuffPool());
		assertEquals(false, after.getOnline());
	}

	/**
	 * Get a gateway that creates a row in the data source with the given
	 * information
	 *
	 * @param mapName the name of the map
	 * @param appearanceType the appearance type of the player
	 * @param quizScore This player's quiz score
	 * @param experiencePoints this player's experience points
	 * @param crew the crew to which this player belongs
	 * @param major the major of this player
	 * @param buffPool The size of the buff pool this player has
	 * @param i
	 * @return the gateway
	 * @throws DatabaseException if we fail to create the row
	 */
	abstract PlayerRowDataGateway createGateway(String appearanceType, int quizScore,
												int experiencePoints, Crew crew, Major major, int section, int buffPool, boolean online) throws DatabaseException;

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
	 * Should remove a row from the data source.
	 *
	 * @throws DatabaseException - should
	 */
	@Test(expected = DatabaseException.class)
	public void removeTest() throws DatabaseException
	{
		PlayersForTest player = PlayersForTest.MATT;
		PlayerRowDataGateway gateway = findGateway(player.getPlayerID());

		gateway.delete();

		// should throw an exception because player now removed
		findGateway(player.getPlayerID());
	}


	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void changeQuizScore() throws DatabaseException
	{
		gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.setQuizScore(666);
		gateway.persist();
		PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(666, after.getQuizScore());
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void changeExperiencePoints() throws DatabaseException
	{
		gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.setExperiencePoints(424);
		gateway.persist();

		PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(424, after.getExperiencePoints());

		gateway.resetData();
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void changeAppearanceType() throws DatabaseException
	{
		gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.setAppearanceType("Ugly!");
		gateway.persist();
		PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN.getPlayerID());
		assertEquals("Ugly!", after.getAppearanceType());

		gateway.resetData();
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void changeBuff() throws DatabaseException
	{
		this.gateway = this.findGateway(PlayersForTest.MERLIN.getPlayerID());

		assertEquals(PlayersForTest.MERLIN.getBuffPool(), this.gateway.getBuffPool());

		this.gateway.setBuffPool(PlayersForTest.MERLIN.getBuffPool() + 10);
		this.gateway.persist();

		PlayerRowDataGateway after = this.findGateway(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(PlayersForTest.MERLIN.getBuffPool() + 10, after.getBuffPool());
	}

	/**
	 * Tests that online status can be toggled on and off
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void flipFlopOnline() throws DatabaseException
	{
		this.gateway = this.findGateway(PlayersForTest.MERLIN.getPlayerID());

		//should start online
		assertEquals(PlayersForTest.MERLIN.getOnline(), this.gateway.getOnline());

		//set to offline
		this.gateway.setOnline(false);
		this.gateway.persist();

		//make sure she's offline
		PlayerRowDataGateway offline = this.findGateway(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(false, offline.getOnline());

		//set back to online
		this.gateway.setOnline(true);
		this.gateway.persist();

		PlayerRowDataGateway online = this.findGateway(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(true, online.getOnline());
	}

	//TODO : get all players online test
}
