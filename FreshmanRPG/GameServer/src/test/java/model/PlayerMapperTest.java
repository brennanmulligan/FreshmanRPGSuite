package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

import dataDTO.AdventureStateRecordDTO;
import dataDTO.PlayerDTO;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayerLoginRowDataGatewayMock;
import datasource.PlayerRowDataGatewayMock;
import datasource.QuestStateTableDataGatewayMock;
import datatypes.AdventureStateEnum;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import datatypes.AdventureStatesForTest;
import datatypes.QuestStatesForTest;

/**
 * Tests the PlayerMapper class
 *
 * @author Merlin
 *
 */
public class PlayerMapperTest
{
	/**
	 * @throws DatabaseException
	 *             shouldn't
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		new PlayerRowDataGatewayMock().resetData();
		new PlayerLoginRowDataGatewayMock().resetData();
		QuestStateTableDataGatewayMock.getSingleton().resetData();
		AdventureStateTableDataGatewayMock.getSingleton().resetData();
		QuestManager.resetSingleton();
		new PlayerConnectionRowDataGatewayMock(1).resetData();
	}

	/**
	 * make sure the mapper retrieves all of the necessary information for the
	 * player it is finding Added Section to the test
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 */
	@Test
	public void canFindExisting() throws DatabaseException, IllegalQuestChangeException
	{
		PlayerMapper pm = getMapper();
		Player p = pm.getPlayer();
		PlayersForTest testPlayer = getPlayerWeAreTesting();
		assertPlayersEqual(testPlayer, p);
		assertEquals(testPlayer.getBuffPool(), p.getBuffPool());

		for (QuestStatesForTest qs : QuestStatesForTest.values())
		{
			if (qs.getPlayerID() == testPlayer.getPlayerID())
			{
				QuestState playerQuestState = QuestManager.getSingleton().getQuestStateByID(testPlayer.getPlayerID(),
						qs.getQuestID());
				assertEquals(qs.getState(), playerQuestState.getStateValue());
				assertEquals(qs.isNeedingNotification(), playerQuestState.isNeedingNotification());
				for (AdventureStatesForTest as : AdventureStatesForTest.values())
				{
					ArrayList<AdventureState> adventureList = playerQuestState.getAdventureList();
					if ((as.getPlayerID() == testPlayer.getPlayerID()) && (as.getQuestID() == playerQuestState.getID()))
					{
						AdventureState expected = new AdventureState(as.getAdventureID(), as.getState(),
								as.isNeedingNotification());
						expected.setParentQuest(playerQuestState);
						assertTrue("questID " + qs.getQuestID() + " adventureID " + as.getAdventureID() + " state "
								+ as.getState(), adventureList.contains(expected));
					}
				}
			}
		}
	}

	/**
	 * Check to make sure that a newly-created mapper has the correct values.
	 *
	 * @throws DatabaseException
	 *             If there is an error creating the mapper.
	 */
	@Test
	public void canCreateNew() throws DatabaseException
	{
		PlayerDTO player = getPlayerWeAreCreating();
		PlayerMapper mapper = createMapperForPlayer(player);
		assertPlayersEqual(player, mapper.getPlayer());
	}

	/**
	 * Check to make sure that a newly-created mapper actually saves to the database
	 *
	 * @throws DatabaseException
	 *             If there is an error when creating the mapper
	 */
	@Test
	public void canCreateNewPersist() throws DatabaseException
	{
		PlayerDTO player = getPlayerWeAreCreating();
		PlayerMapper created = createMapperForPlayer(player);
		PlayerMapper found = findMapperForID(created.getPlayerInfo().getPlayerID());
		assertPlayersEqual(player, found.getPlayer());
	}

	protected PlayerDTO getPlayerWeAreCreating()
	{
		return new PlayerDTO(-1, "the player name", "the player password", "the appearance type", 12,
				new Position(2, 4), "sortingRoom.tmx", 34, Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING, 1, new ArrayList<>());
	}

	protected PlayerMapper findMapperForID(int playerID) throws DatabaseException
	{
		return new PlayerMapper(playerID);
	}

	protected PlayerMapper createMapperForPlayer(PlayerDTO player) throws DatabaseException
	{
		return new PlayerMapper(player.getPosition(), player.getAppearanceType(), player.getKnowledgePoints(),
				player.getExperiencePoints(), player.getCrew(), player.getMajor(), player.getSection(),
				player.getPlayerName(), player.getPlayerPassword());
	}

	protected void assertPlayersEqual(PlayerDTO expected, PlayerDTO actual)
	{
		assertEquals(expected.getAppearanceType(), actual.getAppearanceType());
		assertEquals(expected.getPlayerName(), actual.getPlayerName());
		assertEquals(expected.getPosition(), actual.getPosition());
		assertEquals(expected.getKnowledgePoints(), actual.getKnowledgePoints());
		assertEquals(expected.getMapName(), actual.getMapName());
		assertEquals(expected.getExperiencePoints(), actual.getExperiencePoints());
		assertEquals(expected.getCrew(), actual.getCrew());
		assertEquals(expected.getSection(), actual.getSection());
	}

	protected void assertPlayersEqual(PlayerDTO expected, Player actual)
	{
		assertPlayersEqual(expected, actual.getPlayerInfo());
	}

	protected void assertPlayersEqual(Player expected, Player actual)
	{
		assertPlayersEqual(expected.getPlayerInfo(), actual.getPlayerInfo());
	}

	protected void assertPlayersEqual(PlayersForTest expected, Player actual)
	{
		PlayerDTO dto = new PlayerDTO(expected.getPlayerID(), expected.getPlayerName(), expected.getPlayerPassword(),
				expected.getAppearanceType(), expected.getKnowledgeScore(), expected.getPosition(),
				expected.getMapName(), expected.getExperiencePoints(), expected.getCrew(), expected.getMajor(),
				expected.getSection(), expected.getMapsVisited());

		assertPlayersEqual(dto, actual);
	}

	/**
	 * This must be player 2 for the quest and adventure states to match up
	 *
	 * @return the player whose mapper we are testing
	 */
	protected PlayersForTest getPlayerWeAreTesting()
	{
		return PlayersForTest.MERLIN;
	}

	/**
	 * @return the mapper we are testing
	 * @throws DatabaseException
	 *             if we can't create the mapper
	 */
	protected PlayerMapper getMapper() throws DatabaseException
	{
		return findMapperForID(getPlayerWeAreTesting().getPlayerID());
	}

	/**
	 * When we tell a mapper to remove a player, its quests should be removed from
	 * the quest manager
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void cleansUpQuestManager() throws DatabaseException
	{
		PlayerMapper pm = getMapper();
		pm.removeQuestStates();
		assertNull(QuestManager.getSingleton().getQuestList(getPlayerWeAreTesting().getPlayerID()));
	}

	/**
	 * An exception should be thrown if we are trying to create a mapper for a
	 * player that doesn't exist
	 *
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void cantFindNonExisting() throws DatabaseException
	{
		new PlayerMapper(PlayersForTest.values().length + 10);
	}

	/**
	 * Should be able to coordinate the removal of a player from the data source
	 * layer.
	 *
	 * @throws DatabaseException
	 *             - should
	 */
	@Test(expected = DatabaseException.class)
	public void removeTest() throws DatabaseException
	{
		PlayersForTest player = PlayersForTest.LOSER;
		PlayerMapper mapper = new PlayerMapper(player.getPlayerID());

		mapper.remove();

		new PlayerMapper(player.getPlayerID());
	}

	/**
	 * Make sure that all of the relevant information gets persisted to the data
	 * source
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void persists() throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		PlayerMapper pm = getMapper();
		Player p = pm.getPlayer();
		p.setAppearanceType("silly");
		p.setPlayerPositionWithoutNotifying(new Position(42, 24));
		p.setQuizScore(666);
		p.setMapName("sillyMap");
		p.setExperiencePoints(424);
		p.setCrew(Crew.NULL_POINTER);
		p.setSection(1);
		p.setBuffPool(44);
		p.setPlayerOnline(true);
		QuestState questState = null;
		if (p.getClass() == Player.class)
		{
			questState = QuestManager.getSingleton().getQuestStateByID(p.getPlayerID(),
					QuestStatesForTest.PLAYER2_QUEST2.getQuestID());
			questState.trigger();
		}
		p.persist();


		PlayerMapper pm2 = new PlayerMapper(p.getPlayerID());

		Player p2 = pm2.getPlayer();
		assertEquals(p, p2);

		if (p.getClass() == Player.class)
		{
			QuestState retrievedQuestState = QuestManager.getSingleton().getQuestStateByID(p2.getPlayerID(),
					QuestStatesForTest.PLAYER2_QUEST1.getQuestID());
			assertEquals(questState.getStateValue(), retrievedQuestState.getStateValue());
			for (AdventureState a : retrievedQuestState.getAdventureList())
			{
				assertEquals(AdventureStateEnum.TRIGGERED, a.getState());
			}
		}

	}

	/**
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void getAllPlayersTest() throws DatabaseException
	{
		ArrayList<PlayerDTO> playersList = PlayerMapper.getAllPlayers();

		boolean allFound = true;

		for (PlayersForTest player : PlayersForTest.values())
		{
			boolean foundOne = true;

			for (PlayerDTO returnedPlayer : playersList)
			{
				if (player.getPlayerID() == returnedPlayer.getPlayerID())
				{
					foundOne = true;
					break;
				}
			}

			if (!foundOne)
			{
				allFound = false;
			}
		}

		assertTrue(allFound);
	}


	/**
	 * tests that the mapper returns an arraylist of all incomplete adventures from
	 * the gateway
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testGetIncompleteAdventures() throws DatabaseException
	{
		PlayerMapper pm = getMapper();
		Player p = pm.getPlayer();
		ArrayList<AdventureStateRecordDTO> testList = new ArrayList<>();
		testList = AdventureStateTableDataGatewayMock.getSingleton().getUncompletedAdventuresForPlayer(p.getPlayerID());

		for (AdventureStateRecordDTO adventure : testList)
		{

			assertFalse(adventure.getState().equals(AdventureStateEnum.COMPLETED));
			assertFalse(adventure.getState().equals(AdventureStateEnum.HIDDEN));
			assertFalse(adventure.getState().equals(AdventureStateEnum.EXPIRED));
		}
	}

	/**
	 * Tests that we get the correct list of maps a player has visited.
	 * @throws DatabaseException - shouldn't
	 */
	@Test
	public void testAddMapsToPlayer() throws DatabaseException
	{

		ArrayList<String> expected = getPlayerWeAreTesting().getMapsVisited();
		expected.add("Map1");
		PlayerMapper pm = new PlayerMapper(getPlayerWeAreTesting().getPlayerID());
		pm.addMapToPlayer("map1.tmx");
		assertEquals(expected, pm.getPlayer().getPlayerVisitedMaps());

	}

}
