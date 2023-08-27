package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ObjectiveStateRecordDTO;
import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DefaultItemsTableDataGateway;
import edu.ship.engr.shipsim.datasource.ObjectiveStateTableDataGateway;
import edu.ship.engr.shipsim.datatypes.*;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the PlayerMapper class
 *
 * @author Merlin
 */
@GameTest("GameServer")
@ResetQuestManager
public class PlayerMapperTest
{
    @BeforeEach
    public void localSetUp()
    {
        ObjectiveStateTableDataGateway.getSingleton();
    }

    /**
     * Check to make sure that a newly-created mapper has the correct values.
     *
     * @throws DatabaseException If there is an error creating the mapper.
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
     * @throws DatabaseException If there is an error when creating the mapper
     */
    @Test
    public void canCreateNewPersist() throws DatabaseException
    {
        PlayerDTO player = getPlayerWeAreCreating();
        PlayerMapper created = createMapperForPlayer(player);
        PlayerMapper found = findMapperForID(created.getPlayerDTO().getPlayerID());
        assertPlayersEqual(player, found.getPlayer());
    }

    /**
     * make sure the mapper retrieves all of the necessary information for the
     * player it is finding Added Section to the test
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canFindExisting() throws DatabaseException
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
                QuestState playerQuestState = QuestManager.getSingleton()
                        .getQuestStateByID(testPlayer.getPlayerID(),
                                qs.getQuestID());
                assertEquals(qs.getState(), playerQuestState.getStateValue());
                assertEquals(qs.isNeedingNotification(),
                        playerQuestState.isNeedingNotification());
                for (ObjectiveStatesForTest as : ObjectiveStatesForTest.values())
                {
                    ArrayList<ObjectiveState> objectiveList =
                            playerQuestState.getObjectiveList();
                    if ((as.getPlayerID() == testPlayer.getPlayerID()) &&
                            (as.getQuestID() == playerQuestState.getID()))
                    {
                        ObjectiveState expected =
                                new ObjectiveState(as.getObjectiveID(), as.getState(),
                                        as.isNeedingNotification());
                        expected.setParentQuest(playerQuestState);
                        assertTrue(objectiveList.contains(expected), "questID " + qs.getQuestID() + " objectiveID " +
                                                        as.getObjectiveID() + " state "
                                                        + as.getState());
                    }
                }
            }
        }
    }

    /**
     * An exception should be thrown if we are trying to create a mapper for a
     * player that doesn't exist
     */
    @Test
    public void cantFindNonExisting()
    {
        assertThrows(DatabaseException.class, () ->
        {
            new PlayerMapper(PlayersForTest.values().length + 10);
        });
    }

    /**
     * When we tell a mapper to remove a player, its quests should be removed from
     * the quest manager
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void cleansUpQuestManager() throws DatabaseException
    {
        PlayerMapper pm = getMapper();
        pm.removeQuestStates();
        assertNull(QuestManager.getSingleton()
                .getQuestList(getPlayerWeAreTesting().getPlayerID()));
    }

    /**
     * If quests are loaded multiple times (such as if a player doesn't log out
     * properly), there should be no duplicates.
     */
    @Test
    public void testNoDuplicateQuestsOnLoad() throws DatabaseException
    {
        PlayerMapper pm = getMapper();
        QuestManager qm = QuestManager.getSingleton();

        int playerID = pm.getPlayer().getPlayerID();

        for (int i = 0; i < 2; i++)
        {
            pm.loadQuestStates();
            if (qm.getQuestList(playerID) != null)
            {
                assertEquals(4, qm.getQuestList(playerID).size());
            }
        }
    }

    @Test
    public void getAllPlayersTest()
    {
        ArrayList<PlayerDTO> playersList = PlayerMapper.getAllPlayers();

        boolean allFound = true;

        for (PlayersForTest player : PlayersForTest.values())
        {
            boolean foundOne = false;

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
                break;
            }
        }

        assertTrue(allFound);
    }

    /**
     * Make sure that all of the relevant information gets persisted to the data
     * source
     *
     * @throws DatabaseException               shouldn't
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     */
    @Test
    public void persists() throws DatabaseException, IllegalObjectiveChangeException,
            IllegalQuestChangeException
    {
        PlayerMapper pm = getMapper();
        Player p = pm.getPlayer();
        p.setAppearanceType("silly");
        p.setPositionWithoutNotifying(new Position(42, 24));
        p.setDoubloons(666);
        p.setMapName("sillyMap");
        p.setExperiencePoints(424);
        p.setCrew(Crew.FORTY_PERCENT);
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
            QuestState retrievedQuestState =
                    QuestManager.getSingleton().getQuestStateByID(p2.getPlayerID(),
                            QuestStatesForTest.PLAYER2_QUEST1.getQuestID());
            assertEquals(questState.getStateValue(), retrievedQuestState.getStateValue());
            for (ObjectiveState a : retrievedQuestState.getObjectiveList())
            {
                assertEquals(ObjectiveStateEnum.TRIGGERED, a.getState());
            }
        }

    }

    /**
     * Should be able to coordinate the removal of a player from the data source
     * layer.
     */
    @Test
    public void removeTest()
    {
        assertThrows(DatabaseException.class, () ->
        {
            PlayersForTest player = PlayersForTest.LOSER;
            PlayerMapper mapper = new PlayerMapper(player.getPlayerID());

            mapper.remove();

            new PlayerMapper(player.getPlayerID());
        });
    }

    /**
     * Tests that we get the correct list of maps a player has visited.
     *
     * @throws DatabaseException - shouldn't
     */
    @Test
    public void testAddMapsToPlayer() throws DatabaseException
    {

        ArrayList<String> expected = getPlayerWeAreTesting().getMapsVisited();
        expected.add("Ducktopia");
        PlayerMapper pm = new PlayerMapper(getPlayerWeAreTesting().getPlayerID());
        pm.addNewVisitedMap("Ducktopia.tmx");
        assertEquals(expected, pm.getPlayer().getPlayerVisitedMaps());

    }

    /**
     * tests that the mapper returns an arraylist of all incomplete objectives from
     * the gateway
     *
     * @throws DatabaseException - shouldn't
     */
    @Test
    public void testGetIncompleteObjectives() throws DatabaseException
    {
        PlayerMapper pm = getMapper();
        Player p = pm.getPlayer();
        ArrayList<ObjectiveStateRecordDTO> testList;
        ObjectiveStateTableDataGateway objStateGateway =
                ObjectiveStateTableDataGateway.getSingleton();
        testList = objStateGateway.getUncompletedObjectivesForPlayer(p.getPlayerID());

        for (ObjectiveStateRecordDTO objective : testList)
        {

            assertNotEquals(objective.getState(), ObjectiveStateEnum.COMPLETED);
            assertNotEquals(objective.getState(), ObjectiveStateEnum.HIDDEN);
            assertNotEquals(objective.getState(), ObjectiveStateEnum.EXPIRED);
        }
    }

    private void checkForMatchingContents(ArrayList<VanityDTO> first,
                                          ArrayList<VanityDTO> second)
    {
        assertEquals(first.size(), second.size());
        for (VanityDTO f : first)
        {
            assertTrue(second.contains(f));
        }
    }

    protected PlayerDTO getPlayerWeAreCreating() throws DatabaseException
    {
        DefaultItemsTableDataGateway gateway =
                DefaultItemsTableDataGateway.getSingleton();
        return new PlayerDTO(-1, "the player name", "the player password",
                "the appearance type", 12,
                new Position(2, 4), "sortingRoom.tmx", 34, Crew.FORTY_PERCENT,
                Major.COMPUTER_ENGINEERING, 1, new ArrayList<>(),
                gateway.getDefaultItems());
    }

    protected PlayerMapper findMapperForID(int playerID) throws DatabaseException
    {
        return new PlayerMapper(playerID);
    }

    protected PlayerMapper createMapperForPlayer(PlayerDTO player)
            throws DatabaseException
    {
        return new PlayerMapper(player.getPosition(), player.getAppearanceType(),
                player.getDoubloons(),
                player.getExperiencePoints(), player.getCrew(), player.getMajor(),
                player.getSection(),
                player.getPlayerName(), player.getPassword());
    }

    protected void assertPlayersEqual(PlayerDTO expected, PlayerDTO actual)
    {
        assertEquals(expected.getAppearanceType(), actual.getAppearanceType());
        assertEquals(expected.getPlayerName(), actual.getPlayerName());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getDoubloons(), actual.getDoubloons());
        assertEquals(expected.getMapName(), actual.getMapName());
        assertEquals(expected.getExperiencePoints(), actual.getExperiencePoints());
        assertEquals(expected.getCrew(), actual.getCrew());
        assertEquals(expected.getSection(), actual.getSection());
        checkForMatchingContents(expected.getVanityItems(), actual.getVanityItems());
    }

    protected void assertPlayersEqual(PlayerDTO expected, Player actual)
    {
        assertPlayersEqual(expected, actual.getPlayerDTO());
    }

    protected void assertPlayersEqual(Player expected, Player actual)
    {
        assertPlayersEqual(expected.getPlayerDTO(), actual.getPlayerDTO());
    }

    protected void assertPlayersEqual(PlayersForTest expected, Player actual)
    {
        PlayerDTO dto = new PlayerDTO(expected.getPlayerID(), expected.getPlayerName(),
                expected.getPlayerPassword(),
                expected.getAppearanceType(), expected.getDoubloons(),
                expected.getPosition(),
                expected.getMapName(), expected.getExperiencePoints(), expected.getCrew(),
                expected.getMajor(),
                expected.getSection(), expected.getMapsVisited(),
                (ArrayList<VanityDTO>) expected.getOwnedItems());

        assertPlayersEqual(dto, actual);
    }

    /**
     * This must be player 2 for the quest and objective states to match up
     *
     * @return the player whose mapper we are testing
     */
    protected PlayersForTest getPlayerWeAreTesting()
    {
        return PlayersForTest.MERLIN;
    }

    /**
     * @return the mapper we are testing
     * @throws DatabaseException if we can't create the mapper
     */
    protected PlayerMapper getMapper() throws DatabaseException
    {
        return findMapperForID(getPlayerWeAreTesting().getPlayerID());
    }

}
