package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.FriendTableDataGateway;
import edu.ship.engr.shipsim.datatypes.*;
import edu.ship.engr.shipsim.model.reports.*;
import edu.ship.engr.shipsim.testing.annotations.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test for the quest manager getting quests and objectives from database
 *
 * @author lavonnediller
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetQuestManager
@ResetInteractObjectManager
@ResetReportObserverConnector
public class QuestManagerTest
{
    /**
     * When a player moves to the right place, should complete objective
     */
    @Test
    public void alreadyCompletedObjectiveOnPlayerMovement()
    {
        // We need to add a quest and objective that are triggered on location
        // but not already completed
        int questID = ObjectivesForTest.QUEST2_OBJECTIVE2.getQuestID();
        int advID = ObjectivesForTest.QUEST2_OBJECTIVE2.getObjectiveID();

        GameLocationDTO location =
                (GameLocationDTO) (ObjectivesForTest.QUEST2_OBJECTIVE2.getCompletionCriteria());
        Position pos = location.getPosition();
        Player paul = PlayerManager.getSingleton().addPlayer(8);
        paul.setPosition(pos);
        paul.setPosition(new Position(0, 0));
        paul.setPosition(pos);

        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton()
                        .getObjectiveStateByID(paul.getPlayerID(), questID, advID)
                        .getState());

    }

    /**
     * We should be able to clear out all of the quest states for a given player
     */
    @Test
    public void canRemoveAPlayersQuestStates()
    {
        QuestManager.getSingleton()
                .addQuestState(4, new QuestState(4, 1, QuestStateEnum.AVAILABLE, false));
        QuestManager.getSingleton().removeQuestStates(4);
        assertNull(QuestManager.getSingleton().getQuestList(4));
    }

    /**
     * When a player moves to the right place, should complete objective
     */
    @Test
    public void completeObjectiveOnPlayerMovement()
    {
        // We need to add a quest and objective that are triggered on location
        // but not already completed
        int questID = ObjectivesForTest.QUEST2_OBJECTIVE2.getQuestID();
        int advID = ObjectivesForTest.QUEST2_OBJECTIVE2.getObjectiveID();

        GameLocationDTO location =
                (GameLocationDTO) (ObjectivesForTest.QUEST2_OBJECTIVE2.getCompletionCriteria());
        Position pos = location.getPosition();
        Player paul = PlayerManager.getSingleton().addPlayer(8);
        paul.setPosition(pos);
        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton()
                        .getObjectiveStateByID(paul.getPlayerID(), questID, advID)
                        .getState());
    }

    /**
     * When a player moves to the right place, we should get a list of objectives
     * completed at that location
     *
     * @throws DatabaseException the state changed illegally
     */
    @Test
    public void getCompletedObjectivesOnPlayerMovement() throws DatabaseException
    {
        GameLocationDTO location =
                (GameLocationDTO) (ObjectivesForTest.QUEST2_OBJECTIVE2.getCompletionCriteria());
        String mapName = location.getMapName();
        Position pos = location.getPosition();

        ArrayList<ObjectiveRecord> list =
                QuestManager.getSingleton().getObjectivesByPosition(pos, mapName);
        assertEquals(ObjectivesForTest.QUEST2_OBJECTIVE2.getObjectiveDescription(),
                list.get(0).getObjectiveDescription());
    }

    /**
     * If there are no quests, we should get an empty arraylist - not null
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void notNullIfThereAreNoQuestsAtAPosition() throws DatabaseException
    {
        QuestManager qm = QuestManager.getSingleton();

        Position pos = new Position(4, 4);
        ArrayList<Integer> actual =
                qm.getQuestsByPosition(pos, QuestsForTest.ONE_BIG_QUEST.getMapName());
        assertEquals(0, actual.size());
    }

    /**
     * We should be able to clear out all of the quest states for a given player
     */
    @Test
    public void removeAPlayersQuestStatesWhenLeaves()
    {
        QuestManager.getSingleton()
                .addQuestState(4, new QuestState(4, 1, QuestStateEnum.AVAILABLE, false));
        ReportObserverConnector.getSingleton().sendReport(new PlayerLeaveReport(4));
        assertNull(QuestManager.getSingleton().getQuestList(4));
    }

    /**
     * When we add a quest state to a player, we should tell it which player it
     * belongs to
     */
    @Test
    public void setPlayerIDWhenQuestStateAdded()
    {
        QuestState questState = new QuestState(4, 1, QuestStateEnum.AVAILABLE, false);
        QuestManager.getSingleton().addQuestState(4, questState);
        assertEquals(4, questState.getPlayerID());
    }

    /**
     * This tests simulates taking a quest from triggered to fulfilled to finished
     */
    @Test
    public void simulateCompletingAQuest()
    {
        Player hersh = PlayerManager.getSingleton().addPlayer(PlayersForTest.HERSH.getPlayerID());

        // completing the first objective will fulfill the quest
        GameLocationDTO completionCriteria =
                (GameLocationDTO) ObjectivesForTest.QUEST6_OBJECTIVE_1
                        .getCompletionCriteria();
        hersh.setPosition(completionCriteria.getPosition());
        ObjectiveState objectiveState =
                QuestManager.getSingleton().getObjectiveStateByID(hersh.getPlayerID(),
                        ObjectivesForTest.QUEST6_OBJECTIVE_1.getQuestID(),
                        ObjectivesForTest.QUEST6_OBJECTIVE_1.getObjectiveID());
        assertEquals(ObjectiveStateEnum.COMPLETED, objectiveState.getState());
        QuestState questState =
                QuestManager.getSingleton().getQuestStateByID(hersh.getPlayerID(),
                        ObjectivesForTest.QUEST6_OBJECTIVE_1.getQuestID());
        assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
        assertEquals(
                QuestsForTest.TELEPORT_QUEST.getExperienceGained()
                        +
                        ObjectivesForTest.QUEST6_OBJECTIVE_1.getExperiencePointsGained(),
                hersh.getExperiencePoints());

        // completing the second objective will finish the quest
        completionCriteria =
                (GameLocationDTO) ObjectivesForTest.QUEST6_OBJECTIVE_2.getCompletionCriteria();
        hersh.setPosition(completionCriteria.getPosition());

        objectiveState =
                QuestManager.getSingleton().getObjectiveStateByID(hersh.getPlayerID(),
                        ObjectivesForTest.QUEST6_OBJECTIVE_2.getQuestID(),
                        ObjectivesForTest.QUEST6_OBJECTIVE_2.getObjectiveID());
        assertEquals(ObjectiveStateEnum.COMPLETED, objectiveState.getState());

        questState = QuestManager.getSingleton().getQuestStateByID(hersh.getPlayerID(),
                ObjectivesForTest.QUEST6_OBJECTIVE_2.getQuestID());
        assertEquals(QuestStateEnum.COMPLETED, questState.getStateValue());
        assertEquals(
                QuestsForTest.TELEPORT_QUEST.getExperienceGained()
                        + ObjectivesForTest.QUEST6_OBJECTIVE_1.getExperiencePointsGained()
                        +
                        ObjectivesForTest.QUEST6_OBJECTIVE_2.getExperiencePointsGained(),
                hersh.getExperiencePoints());
    }

    /**
     * Test simple functionality of setting quests to a player.
     */
    @Test
    public void testAddQuests()
    {
        Player p = PlayerManager.getSingleton().addPlayer(1);
        QuestState quest = new QuestState(1, 1, QuestStateEnum.AVAILABLE, false);
        QuestManager.getSingleton().addQuestState(p.getPlayerID(), quest);

        QuestState questStateByID = QuestManager.getSingleton().getQuestStateByID(1, 1);
        assertEquals(QuestStateEnum.AVAILABLE, questStateByID.getStateValue());
        assertFalse(questStateByID.isNeedingNotification());

    }

    /**
     * @throws DatabaseException               shouldn't
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     */
    @Test
    public void testCompleteObjective()
            throws DatabaseException, IllegalObjectiveChangeException,
            IllegalQuestChangeException
    {
        int playerID = 1;
        int questID = 3;
        int objectiveID = 1;

        Player p = PlayerManager.getSingleton().addPlayer(playerID);
        QuestState qs =
                QuestManager.getSingleton().getQuestStateByID(p.getPlayerID(), questID);
        ObjectiveState as = QuestManager.getSingleton()
                .getObjectiveStateByID(p.getPlayerID(), questID, objectiveID);
        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(as);
        qs.addObjectives(objectiveList);
        qs.setPlayerID(playerID);
        QuestManager.getSingleton().completeObjective(playerID, questID, objectiveID);
        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton()
                        .getObjectiveStateByID(playerID, questID, objectiveID)
                        .getState());
    }

    /**
     * Completes an objective that has the completion criteria of talking to a NPC.
     */
    @Test
    public void testCompleteObjectiveByChatting()
    {
        int playerToTest = PlayersForTest.MARTY.getPlayerID();
        Player playerOne = PlayerManager.getSingleton().addPlayer(playerToTest);

        Player toTalkTo = PlayerManager.getSingleton().addPlayer(PlayersForTest.QUIZBOT.getPlayerID());
        Position playerOnePosition =
                new Position(toTalkTo.getPosition().getRow() + 1,
                        toTalkTo.getPosition().getColumn() + 1);
        playerOne.setPosition(playerOnePosition);

        ChatMessageReceivedReport csmr =
                new ChatMessageReceivedReport(playerOne.getPlayerID(), 0, "Hello",
                        playerOne.getPosition(), ChatType.Local);

        ReportObserverConnector.getSingleton().sendReport(csmr);

        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton().getQuestStateByID(playerToTest, 5)
                        .getObjectiveList().get(0).getState());
    }

    @Test
    public void testCompleteObjectiveByReceivingChat()
    {
        int playerToTest = PlayersForTest.JEFF.getPlayerID();
        int questID = ObjectivesForTest.RESPONSE_FROM_MOWREY_NPC.getQuestID();
        int objectiveID = ObjectivesForTest.RESPONSE_FROM_MOWREY_NPC.getObjectiveID();
        Player playerOne = PlayerManager.getSingleton().addPlayer(playerToTest);

        // Sets the quest state
        QuestState qs =
                new QuestState(playerToTest, questID, QuestStateEnum.TRIGGERED, true);
        QuestManager.getSingleton().addQuestState(playerToTest, qs);

        // Sets up and adds the chat objective
        ObjectiveState as =
                new ObjectiveState(objectiveID, ObjectiveStateEnum.TRIGGERED, true);
        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(as);
        qs.addObjectives(objectiveList);
        ObjectiveState objectiveStateByID =
                QuestManager.getSingleton().getObjectiveStateByID(playerToTest, questID,
                        objectiveID);

        // Verified that the objective state is still set to triggered
        assertEquals(ObjectiveStateEnum.TRIGGERED, objectiveStateByID.getState());

        Player toTalkTo = PlayerManager.getSingleton().addPlayer(
                PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID());
        playerOne.setMapName("mowrey.tmx");
        Position playerOnePosition =
                new Position(toTalkTo.getPosition().getRow() + 1,
                        toTalkTo.getPosition().getColumn() + 1);
        playerOne.setPosition(playerOnePosition);

        ChatMessageToClientReport csmr =
                new ChatMessageToClientReport(toTalkTo.getPlayerID(),
                        playerOne.getPlayerID(),
                        "Hello, student",
                        playerOne.getPosition(), ChatType.Local);

        ReportObserverConnector.getSingleton().sendReport(csmr);

        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton().getQuestStateByID(playerToTest, 16)
                        .getObjectiveList().get(0).getState());
    }

    /**
     * This test makes sure that, at the moment an objective completes, the quest
     * state changes to fulfilled and the experience points are updated when that is
     * appropriate
     *
     * @throws DatabaseException               shouldn't
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     */
    @Test
    public void testCompletingObjective()
            throws DatabaseException, IllegalObjectiveChangeException,
            IllegalQuestChangeException
    {
        int playerID = PlayersForTest.JOSH.getPlayerID();
        int questID = 4;
        Player p = PlayerManager.getSingleton().addPlayer(playerID);
        int initialExp = p.getExperiencePoints();

        ObjectiveState as = new ObjectiveState(2, ObjectiveStateEnum.TRIGGERED, false);
        ArrayList<ObjectiveState> objectives = new ArrayList<>();
        objectives.add(as);

        QuestState qs =
                new QuestState(playerID, questID, QuestStateEnum.FULFILLED, false);
        qs.addObjectives(objectives);
        QuestManager.getSingleton().addQuestState(playerID, qs);
        as.complete();

        int objectiveExperienceGained =
                QuestManager.getSingleton().getQuest(questID).getObjectiveXP(1);

        assertEquals(QuestStateEnum.COMPLETED, qs.getStateValue());
        assertEquals(initialExp + objectiveExperienceGained, p.getExperiencePoints());
    }

    /**
     * Tests completing an objective by user input
     */
    @Test
    public void testCompletingObjectiveByInput()
    {
        int playerID = PlayersForTest.NEWBIE.getPlayerID();
        int questID = ObjectivesForTest.ONRAMPING_PRESS_Q.getQuestID();
        int objectiveID = ObjectivesForTest.ONRAMPING_PRESS_Q.getObjectiveID();

        Player p = PlayerManager.getSingleton().addPlayer(playerID);
        QuestState qs =
                QuestManager.getSingleton().getQuestStateByID(p.getPlayerID(), questID);
        qs.setPlayerID(playerID);

        assertEquals(ObjectiveStateEnum.TRIGGERED,
                QuestManager.getSingleton()
                        .getObjectiveStateByID(playerID, questID, objectiveID)
                        .getState());
        CommandKeyInputMessageReceived command =
                new CommandKeyInputMessageReceived("q", playerID);
        command.execute();

        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton()
                        .getObjectiveStateByID(playerID, questID, objectiveID)
                        .getState());
    }

    /**
     * Tests that we finishing a quest changes its state to finished
     *
     * @throws IllegalQuestChangeException thrown if illegal state change
     * @throws DatabaseException           the state changed illegally
     *
     * @author Derek Williams
     */
    @Test
    public void testFinishQuest() throws IllegalQuestChangeException, DatabaseException
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a TeleportOnQuestCompletionReport is sent
        connector.registerObserver(observer, TeleportOnQuestCompletionReport.class);

        // retrieve objects from database
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        QuestRecord quest = QuestManager.getSingleton().getQuest(QuestsForTest.THE_LITTLE_QUEST.getQuestID());

        // setup the expected TeleportationOnQuestCompletionReport
        GameLocationDTO gameLocation = (GameLocationDTO) QuestsForTest.THE_LITTLE_QUEST.getCompletionActionParameter();
        MapToServerMapping mapping = new MapToServerMapping(gameLocation.getMapName());
        TeleportOnQuestCompletionReport expectedReport = new TeleportOnQuestCompletionReport(player.getPlayerID(), quest.getQuestID(), gameLocation, mapping.getHostName(), mapping.getPortNumber());

        // setup the quest state to be completed
        QuestState questState = QuestManager.getSingleton().getQuestStateByID(player.getPlayerID(), quest.getQuestID());
        questState.setPlayerID(player.getPlayerID());

        // complete the quest for the player, which should send out a TeleportationOnQuestCompletionReport
        // at the end of "The Little Quest", the player is teleported
        QuestManager.getSingleton().completeQuest(player.getPlayerID(), questState.getID());

        // since the player completed the question, verify that the observer was notified with a TeleportationOnQuestCompletionReport
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * Should be able to change the state of a quest to fulfilled if enough
     * objectives are completed. Player 4 quest 3 in the mock data has enough
     * objectives to be fulfilled, but still looks pending. We just want to test the
     * behavior of checking for fulfillment (without an associated objective state
     * change)
     *
     * @throws DatabaseException           shouldn't
     * @throws IllegalQuestChangeException thrown if illegal state change
     */
    @Test
    public void testFulfillQuest() throws DatabaseException, IllegalQuestChangeException
    {
        int playerID = PlayersForTest.JOSH.getPlayerID();
        int questID = 3;
        Player p = PlayerManager.getSingleton().addPlayer(playerID);
        int initialExp = p.getExperiencePoints();
        assertEquals(initialExp, PlayersForTest.JOSH.getExperiencePoints());
        int expGain =
                QuestManager.getSingleton().getQuest(questID).getExperiencePointsGained();

        QuestState x = QuestManager.getSingleton().getQuestStateByID(playerID, questID);
        x.checkForFulfillmentOrFinished();
        assertEquals(QuestStateEnum.FULFILLED, x.getStateValue());
        assertEquals(initialExp + expGain, p.getExperiencePoints());
    }

    /**
     *
     */
    @Test
    public void testGetQuestStateByIDWhenQuestStateIsAvilable()
    {
        PlayerManager.getSingleton().addPlayer(19);
        QuestState questState = QuestManager.getSingleton().getQuestStateByID(19, 7);
        assertEquals(7, questState.getID());
        assertEquals(QuestStateEnum.EXPIRED, questState.getStateValue());
    }

    /**
     *
     */
    @Test
    public void testGetQuestStateByIDWhenQuestStateIsCompleted()
    {
        PlayerManager.getSingleton().addPlayer(19);
        QuestState questState = QuestManager.getSingleton().getQuestStateByID(19, 9);
        assertEquals(9, questState.getID());
        assertEquals(QuestStateEnum.COMPLETED, questState.getStateValue());
    }

    /**
     * A finished quest should be marked as finished not expired
     */
    @Test
    public void testGetQuestStateByIDWhenQuestStateIsExpired()
    {
        PlayerManager.getSingleton().addPlayer(19);
        QuestState questState = QuestManager.getSingleton().getQuestStateByID(19, 8);
        assertEquals(8, questState.getID());
        assertEquals(QuestStateEnum.EXPIRED, questState.getStateValue());
    }

    /**
     * Test getting quests by a position and map name
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testGetQuestsPosition() throws DatabaseException
    {
        QuestManager qm = QuestManager.getSingleton();

        Position pos = QuestsForTest.ONE_BIG_QUEST.getPosition();

        int index = 1;
        for (Integer i : qm.getQuestsByPosition(pos,
                QuestsForTest.ONE_BIG_QUEST.getMapName()))
        {
            if (index == 1)
            {
                assertEquals((int) i, QuestsForTest.ONE_BIG_QUEST.getQuestID());
            }
            if (index == 3)
            {
                assertEquals((int) i, QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID());
            }
            index++;
        }
    }

    /**
     * Test that we can get a specific quests objectives
     *
     * @throws DatabaseException throw an exception if the quest id isn't found
     */
    @Test
    public void testGettingAQuestsObjectives() throws DatabaseException
    {
        QuestManager qm = QuestManager.getSingleton();
        QuestRecord quest = qm.getQuest(1);

        int i = 1;
        for (ObjectiveRecord a : quest.getObjectives())
        {
            if (i == 1)
            {
                assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveID(),
                        a.getObjectiveID());
                assertEquals(
                        ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveDescription(),
                        a.getObjectiveDescription());
            }
            if (i == 2)
            {
                assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE2.getObjectiveID(),
                        a.getObjectiveID());
                assertEquals(
                        ObjectivesForTest.QUEST1_OBJECTIVE2.getObjectiveDescription(),
                        a.getObjectiveDescription());
            }
            i++;
        }
    }

    /**
     * If we ask for an objective that doesn't exist, we should get null
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testGettingMissingObjective() throws DatabaseException
    {
        QuestManager qm = QuestManager.getSingleton();
        ObjectiveRecord actual = qm.getObjective(42, 16);
        assertNull(actual);
    }

    /**
     * We should be able to retrieve data about a specific objective
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testGettingOneObjective() throws DatabaseException
    {
        QuestManager qm = QuestManager.getSingleton();
        ObjectivesForTest expected = ObjectivesForTest.QUEST3_OBJECTIVE1;
        ObjectiveRecord actual =
                qm.getObjective(expected.getQuestID(), expected.getObjectiveID());
        assertEquals(ObjectivesForTest.QUEST3_OBJECTIVE1.getObjectiveDescription(),
                actual.getObjectiveDescription());
        assertEquals(ObjectivesForTest.QUEST3_OBJECTIVE1.getObjectiveID(),
                actual.getObjectiveID());
        assertEquals(ObjectivesForTest.QUEST3_OBJECTIVE1.getExperiencePointsGained(),
                actual.getExperiencePointsGained());
        assertEquals(ObjectivesForTest.QUEST3_OBJECTIVE1.getQuestID(),
                actual.getQuestID());
    }

    /**
     * Test getting a quest from the database and storing it into our Quest Manager,
     * using a Quest Row Data Gateway Mock
     *
     * @throws DatabaseException throw an exception if the quest id isn't found
     */
    @Test
    public void testGettingOneQuest() throws DatabaseException
    {
        QuestManager qm = QuestManager.getSingleton();
        QuestsForTest expected = QuestsForTest.ONE_BIG_QUEST;
        QuestRecord actual = qm.getQuest(1);
        assertEquals(expected.getQuestID(), actual.getQuestID());
        assertEquals(expected.getQuestDescription(), actual.getDescription());
        assertEquals(expected.getMapName(), actual.getTriggerMapName());
        assertEquals(expected.getObjectiveForFulfillment(),
                actual.getObjectivesForFulfillment());
        assertEquals(expected.getExperienceGained(), actual.getExperiencePointsGained());
    }

    /**
     * Test getting two quests from the database
     *
     * @throws DatabaseException throw an exception if the quest id isn't found
     */
    @Test
    public void testGettingTwoQuests() throws DatabaseException
    {
        QuestManager qm = QuestManager.getSingleton();

        assertEquals(1, qm.getQuest(1).getQuestID());
        assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(),
                qm.getQuest(1).getDescription());

        assertEquals(2, qm.getQuest(2).getQuestID());
        assertEquals(QuestsForTest.THE_OTHER_QUEST.getQuestDescription(),
                qm.getQuest(2).getDescription());
    }

    /**
     * Test getting two quests and their two objectives from the db
     *
     * @throws DatabaseException throw an exception if the quest id isn't found
     */
    @Test
    public void testGettingTwoQuestsAndTheirObjectives() throws DatabaseException
    {
        QuestManager qm = QuestManager.getSingleton();
        QuestRecord quest1 = qm.getQuest(1);
        QuestRecord quest2 = qm.getQuest(2);

        int i = 1;
        for (ObjectiveRecord a : quest1.getObjectives())
        {
            if (i == 1)
            {
                assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveID(),
                        a.getObjectiveID());
                assertEquals(
                        ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveDescription(),
                        a.getObjectiveDescription());
            }
            if (i == 2)
            {
                assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE2.getObjectiveID(),
                        a.getObjectiveID());
                assertEquals(
                        ObjectivesForTest.QUEST1_OBJECTIVE2.getObjectiveDescription(),
                        a.getObjectiveDescription());
            }
            i++;
        }

        i = 1;
        for (ObjectiveRecord a : quest2.getObjectives())
        {
            if (i == 1)
            {
                assertEquals(ObjectivesForTest.QUEST2_OBJECTIVE1.getObjectiveID(),
                        a.getObjectiveID());
                assertEquals(
                        ObjectivesForTest.QUEST2_OBJECTIVE1.getObjectiveDescription(),
                        a.getObjectiveDescription());
            }
            if (i == 2)
            {
                assertEquals(ObjectivesForTest.QUEST2_OBJECTIVE2.getObjectiveID(),
                        a.getObjectiveID());
                assertEquals(
                        ObjectivesForTest.QUEST2_OBJECTIVE2.getObjectiveDescription(),
                        a.getObjectiveDescription());
            }
            i++;
        }
    }

    /**
     * Test that the Quest is completed when the player has enough doubloons.
     */
    @Test
    public void testHandleDoubloonsChanged()
    {

        int playerID = 19;
        int questID = 10;
        int objectiveID = 1;

        Player p = PlayerManager.getSingleton().addPlayer(playerID);

        QuestState qs = new QuestState(playerID, questID, QuestStateEnum.TRIGGERED, true);
        QuestManager.getSingleton().addQuestState(playerID, qs);

        ObjectiveState as =
                new ObjectiveState(objectiveID, ObjectiveStateEnum.TRIGGERED, true);

        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(as);

        qs.addObjectives(objectiveList);

        ObjectiveState objectiveStateByID =
                QuestManager.getSingleton().getObjectiveStateByID(playerID, questID,
                        objectiveID);

        assertEquals(ObjectiveStateEnum.TRIGGERED, objectiveStateByID.getState());

        p.setDoubloons(7);

        DoubloonChangeReport report =
                new DoubloonChangeReport(playerID, 80, p.getBuffPool());

        QuestManager.getSingleton().handleDoubloonsChanged(report);

        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton()
                        .getObjectiveStateByID(playerID, questID, objectiveID)
                        .getState());

    }

    @Test
    public void testHandleFriends() throws DatabaseException
    {
        int playerID = PlayersForTest.MERLIN.getPlayerID();
        int playerIDD = PlayersForTest.JOHN.getPlayerID();
        int questID = ObjectivesForTest.QUEST_14_OBJECTIVE_1.getQuestID();
        int objectiveID = ObjectivesForTest.QUEST_14_OBJECTIVE_1.getObjectiveID();
        Player merlin = PlayerManager.getSingleton().addPlayer(playerID);
        Player john = PlayerManager.getSingleton().addPlayer(playerIDD);

        // Sets the quest state
        QuestState qs = new QuestState(playerID, questID, QuestStateEnum.TRIGGERED, true);
        QuestManager.getSingleton().addQuestState(playerID, qs);

        // Sets up and adds the friend objective
        ObjectiveState as =
                new ObjectiveState(objectiveID, ObjectiveStateEnum.TRIGGERED, true);
        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(as);
        qs.addObjectives(objectiveList);
        ObjectiveState objectiveStateByID =
                QuestManager.getSingleton().getObjectiveStateByID(playerID, questID,
                        objectiveID);

        // Verified that the objective state is still set to triggered
        assertEquals(ObjectiveStateEnum.TRIGGERED, objectiveStateByID.getState());

        // Sets the quest state
        QuestState qs2 =
                new QuestState(playerIDD, questID, QuestStateEnum.TRIGGERED, true);
        QuestManager.getSingleton().addQuestState(playerIDD, qs);

        // Sets up and adds the friend objective
        ObjectiveState as2 =
                new ObjectiveState(objectiveID, ObjectiveStateEnum.TRIGGERED, true);
        ArrayList<ObjectiveState> objectiveList2 = new ArrayList<>();
        objectiveList2.add(as2);
        qs2.addObjectives(objectiveList2);
        ObjectiveState objectiveStateByID2 =
                QuestManager.getSingleton().getObjectiveStateByID(playerIDD, questID,
                        objectiveID);
        // Verified that the objective state is still set to triggered
        assertEquals(ObjectiveStateEnum.TRIGGERED, objectiveStateByID2.getState());

        FriendTableDataGateway gateway =
                FriendTableDataGateway.getSingleton();
        //merlin and john became friends
        gateway.add(playerID, "John", FriendStatusEnum.ACCEPTED);
        //gateway.accept(playerID, "John");
        FriendConnectionReceivedReport report =
                new FriendConnectionReceivedReport(playerID, playerIDD);

        ReportObserverConnector.getSingleton().sendReport(report);
        QuestManager.getSingleton().handleFriends(report);

        // Verifies that the objective is now completed
        //Player 1 : Sender
        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton()
                        .getObjectiveStateByID(playerID, questID, objectiveID)
                        .getState());
        //		Player 2 Receiver
        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton()
                        .getObjectiveStateByID(playerIDD, questID, objectiveID)
                        .getState());
    }

    /**
     * Tests that an interactable object can complete an objective
     */
    @Test
    public void testHandleInteractableObjectUsed()
    {
        // Set up
        int playerID = PlayersForTest.NICK.getPlayerID();
        int questID = ObjectivesForTest.QUEST12_OBJECTIVE_1.getQuestID();
        int objectiveID = ObjectivesForTest.QUEST12_OBJECTIVE_1.getObjectiveID();
        Player nick = PlayerManager.getSingleton().addPlayer(playerID);

        // Sets the quest state
        QuestState qs = new QuestState(playerID, questID, QuestStateEnum.TRIGGERED, true);
        QuestManager.getSingleton().addQuestState(playerID, qs);

        // Sets up and adds the interact objective
        ObjectiveState as =
                new ObjectiveState(objectiveID, ObjectiveStateEnum.TRIGGERED, true);
        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(as);
        qs.addObjectives(objectiveList);
        ObjectiveState objectiveStateByID =
                QuestManager.getSingleton().getObjectiveStateByID(playerID, questID,
                        objectiveID);

        // Verified that the objective state is still set to triggered
        assertEquals(ObjectiveStateEnum.TRIGGERED, objectiveStateByID.getState());

        // Set up the interact object manager
        InteractObjectManager.getSingleton();

        // Places Nick next to the object and interacts with it
        nick.setPosition(InteractableItemsForTest.MSG_TEST.getPosition());
        KeyInputRecievedReport report = new KeyInputRecievedReport("e", playerID);
        ReportObserverConnector.getSingleton().sendReport(report);

        // Verifies that the objective is now completed
        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton()
                        .getObjectiveStateByID(playerID, questID, objectiveID)
                        .getState());

    }

    @Test
    public void testHandleMultipleFriends() throws DatabaseException
    {
        int playerID = PlayersForTest.MERLIN.getPlayerID();
        int playerIDD = PlayersForTest.JOHN.getPlayerID();
        int playerIDT = PlayersForTest.JOSH.getPlayerID();
        int questID = ObjectivesForTest.QUEST_15_OBJECTIVE_1.getQuestID();
        int objectiveID = ObjectivesForTest.QUEST_15_OBJECTIVE_1.getObjectiveID();
        Player merlin = PlayerManager.getSingleton().addPlayer(playerID);
        Player john = PlayerManager.getSingleton().addPlayer(playerIDD);
        Player josh = PlayerManager.getSingleton().addPlayer(playerIDT);
        // Sets the quest state
        QuestState qs = new QuestState(playerID, questID, QuestStateEnum.TRIGGERED, true);
        QuestManager.getSingleton().addQuestState(playerID, qs);

        // Sets up and adds the friend objective
        ObjectiveState as =
                new ObjectiveState(objectiveID, ObjectiveStateEnum.TRIGGERED, true);
        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(as);
        qs.addObjectives(objectiveList);
        ObjectiveState objectiveStateByID =
                QuestManager.getSingleton().getObjectiveStateByID(playerID, questID,
                        objectiveID);

        // Verified that the objective state is still set to triggered
        assertEquals(ObjectiveStateEnum.TRIGGERED, objectiveStateByID.getState());

        FriendTableDataGateway gateway =
                FriendTableDataGateway.getSingleton();
        //merlin and john became friends
        gateway.add(playerID, "John", FriendStatusEnum.ACCEPTED);


        //gateway.accept(playerID, "John");
        FriendConnectionReceivedReport report =
                new FriendConnectionReceivedReport(playerID, playerIDD);

        ReportObserverConnector.getSingleton().sendReport(report);
        QuestManager.getSingleton().handleFriends(report);
        gateway.add(playerID, "Josh", FriendStatusEnum.ACCEPTED);

        gateway.getFriendCounter(playerID);
        FriendConnectionReceivedReport report2 =
                new FriendConnectionReceivedReport(playerID, playerIDD);

        ReportObserverConnector.getSingleton().sendReport(report2);
        QuestManager.getSingleton().handleFriends(report2);
        assertEquals(2, gateway.getFriendCounter(playerID));
        // Verifies that the objective is now completed

        assertEquals(ObjectiveStateEnum.COMPLETED,
                QuestManager.getSingleton()
                        .getObjectiveStateByID(playerID, questID, objectiveID)
                        .getState());
    }

    /**
     * Tests Objective completion when a player types the proper command in the terminal
     */
    @Test
    public void testHandleReceiveTerminalTextReport()
    {
        int playerID = 19;
        int questID = ObjectivesForTest.ONRAMPING_TERMINAL_MAN.getQuestID();
        int objectiveID = ObjectivesForTest.ONRAMPING_TERMINAL_MAN.getObjectiveID();

        PlayerManager.getSingleton().addPlayer(playerID);

        QuestState qs = new QuestState(playerID, questID, QuestStateEnum.TRIGGERED, true);
        QuestManager.getSingleton().addQuestState(playerID, qs);

        ObjectiveState as =
                new ObjectiveState(objectiveID, ObjectiveStateEnum.TRIGGERED, true);

        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(as);

        qs.addObjectives(objectiveList);

        ObjectiveState objectiveStateByID = QuestManager.getSingleton()
                .getObjectiveStateByID(playerID, questID, objectiveID);

        assertEquals(ObjectiveStateEnum.TRIGGERED, objectiveStateByID.getState());

        String terminalText = "man";
        ReceiveTerminalTextReport report =
                new ReceiveTerminalTextReport(playerID, "", terminalText);
        ReportObserverConnector.getSingleton().sendReport(report);

        assertEquals(ObjectiveStateEnum.COMPLETED, QuestManager.getSingleton()
                .getObjectiveStateByID(playerID, questID, objectiveID).getState());
    }

    /**
     * Test initializing a quest manager
     */
    @Test
    public void testInitialization()
    {
        QuestManager.resetSingleton();
        QuestManager questManager = QuestManager.getSingleton();
        assertNotNull(questManager);
    }

    /**
     * Make sure QuestManager is a resetable singleton
     */
    @Test
    public void testIsSingleton()
    {
        QuestManager qm1 = QuestManager.getSingleton();
        QuestManager qm2 = QuestManager.getSingleton();

        assertSame(qm1, qm2);

        QuestManager.resetSingleton();
        assertNotSame(qm1, QuestManager.getSingleton());
    }

    /**
     * Objective isn't completed because the person is outside the range of who they
     * are supposed to talk to
     */
    @Test
    public void testNotCompleteObjectiveByChattingOutsideOfRange()
    {
        int playerToTest = PlayersForTest.MARTY.getPlayerID();
        Player playerOne = PlayerManager.getSingleton().addPlayer(playerToTest);

        Player toTalkTo = PlayerManager.getSingleton().addPlayer(PlayersForTest.QUIZBOT.getPlayerID());
        Position playerOnePosition =
                new Position(toTalkTo.getPosition().getRow() + 6,
                        toTalkTo.getPosition().getColumn() + 6);
        playerOne.setPosition(playerOnePosition);

        ChatMessageReceivedReport csmr =
                new ChatMessageReceivedReport(playerOne.getPlayerID(), 0, "Hello",
                        playerOne.getPosition(), ChatType.Local);

        ReportObserverConnector.getSingleton().sendReport(csmr);

        assertEquals(ObjectiveStateEnum.TRIGGERED,
                QuestManager.getSingleton().getQuestStateByID(playerToTest, 5)
                        .getObjectiveList().get(0).getState());
    }

    /**
     * Make sure quest is triggered if it walks onto a location that has a quest
     */
    @Test
    public void testPlayerTriggerOnMovement()
    {
        Position pos1 = new Position(1, 1);
        Position pos2 = QuestsForTest.THE_LITTLE_QUEST.getPosition();
        Player p = PlayerManager.getSingleton().addPlayer(4);
        p.setMapName(QuestsForTest.THE_LITTLE_QUEST.getMapName());
        p.setPosition(pos1);
        QuestManager one = QuestManager.getSingleton();
        assertEquals(QuestStatesForTest.PLAYER4_QUEST4.getState(),
                QuestManager.getSingleton()
                        .getQuestStateByID(p.getPlayerID(),
                                QuestsForTest.THE_LITTLE_QUEST.getQuestID())
                        .getStateValue());
        QuestManager two = QuestManager.getSingleton();
        assertSame(one, two);
        p.setPosition(pos2);

        assertEquals(QuestStateEnum.TRIGGERED, QuestManager.getSingleton()
                .getQuestStateByID(p.getPlayerID(),
                        QuestsForTest.THE_LITTLE_QUEST.getQuestID()).getStateValue());
    }

    /**
     * Make sure quest is triggered within player when quest is available
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     * @throws DatabaseException               the state changed illegally
     */
    @Test
    public void testPlayerTriggersQuest()
            throws IllegalObjectiveChangeException, IllegalQuestChangeException,
            DatabaseException
    {
        Player p = PlayerManager.getSingleton().addPlayer(7);
        assertEquals(QuestStateEnum.AVAILABLE, QuestManager.getSingleton()
                .getQuestStateByID(p.getPlayerID(),
                        QuestStatesForTest.PLAYER7_QUEST2.getQuestID()).getStateValue());

        QuestManager.getSingleton()
                .triggerQuest(7, QuestStatesForTest.PLAYER7_QUEST2.getQuestID());
        assertEquals(QuestStateEnum.TRIGGERED, QuestManager.getSingleton()
                .getQuestStateByID(p.getPlayerID(),
                        QuestStatesForTest.PLAYER7_QUEST2.getQuestID()).getStateValue());
    }

    /**
     * A Database Exception should be thrown if a quest id does not exist
     */
    @SuppressWarnings("unused")
    @Test
    public void testQuestDoesNotExits()
    {
        assertThrows(DatabaseException.class, () ->
        {
            QuestManager qm = QuestManager.getSingleton();
            QuestRecord quest1 = qm.getQuest(1000);
        });
    }

    /**
     * When a player moves to the right place, we should trigger the questy
     */
    @Test
    public void triggersOnPlayerMovement()
    {
        OptionsManager.getSingleton().setMapFileTitle(ServersForTest.QUAD.getMapName());
        Player p = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        p.setPosition(QuestsForTest.ONE_BIG_QUEST.getPosition());
        assertEquals(QuestStateEnum.TRIGGERED, QuestManager.getSingleton()
                .getQuestStateByID(p.getPlayerID(),
                        QuestStatesForTest.PLAYER1_QUEST1.getQuestID()).getStateValue());
    }
}
