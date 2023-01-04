package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.model.reports.QuestStateChangeReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test for the QuestState Class
 *
 * @author Ryan
 */
@GameTest("GameServer")
@ResetQuestManager
@ResetReportObserverConnector
public class QuestStateTest
{
    /**
     * Test creating a very simple quest, and retreiving its information
     */
    @Test
    public void testInitialize()
    {
        QuestState qs = new QuestState(2, 1, QuestStateEnum.AVAILABLE, true);

        assertEquals(2, qs.getPlayerID());
        assertEquals(1, qs.getID());
        assertEquals(QuestStateEnum.AVAILABLE, qs.getStateValue());
        assertTrue(qs.isNeedingNotification());
    }

    /**
     * Test adding an ArrayList of objectives into quest
     */
    @Test
    public void testAddObjectives()
    {
        QuestState qs = new QuestState(2, 1, QuestStateEnum.AVAILABLE, true);
        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        ObjectiveState as1 = new ObjectiveState(1, ObjectiveStateEnum.HIDDEN, false);
        ObjectiveState as2 = new ObjectiveState(2, ObjectiveStateEnum.HIDDEN, false);

        objectiveList.add(as1);
        objectiveList.add(as2);

        qs.addObjectives(objectiveList);

        assertEquals(2, qs.getSizeOfObjectiveList());
    }

    /**
     * Test the change in quest's state when triggered
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     * @throws DatabaseException               shouldn't
     */
    @Test
    public void testTriggerQuest()
            throws IllegalObjectiveChangeException, IllegalQuestChangeException, DatabaseException
    {
        QuestState quest = new QuestState(2, 1, QuestStateEnum.AVAILABLE, true);
        quest.trigger();
        assertEquals(QuestStateEnum.TRIGGERED, quest.getStateValue());
        assertTrue(quest.isNeedingNotification());
    }

    /**
     * Test to make sure you can't trigger finished quests
     */
    @Test
    public void testTriggerFinishedQuest()
    {
        assertThrows(IllegalQuestChangeException.class, () ->
        {
            QuestState quest = new QuestState(2, 1, QuestStateEnum.COMPLETED, false);
            quest.trigger();
        });
    }

    /**
     * Test that when a quest is triggered, its objectives get triggered as well
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     * @throws DatabaseException               shouldn't
     */
    @Test
    public void testTriggerQuestsObjectives()
            throws IllegalObjectiveChangeException, IllegalQuestChangeException, DatabaseException
    {
        QuestState qs = new QuestState(2, 1, QuestStateEnum.AVAILABLE, false);
        ArrayList<ObjectiveState> adList = new ArrayList<>();

        ObjectiveState as1 = new ObjectiveState(1, ObjectiveStateEnum.HIDDEN, false);
        ObjectiveState as2 = new ObjectiveState(2, ObjectiveStateEnum.HIDDEN, false);
        ObjectiveState as3 = new ObjectiveState(3, ObjectiveStateEnum.HIDDEN, false);

        adList.add(as1);
        adList.add(as2);
        adList.add(as3);

        qs.addObjectives(adList);
        adList = qs.getObjectiveList();

        qs.trigger();

        for (ObjectiveState as : adList)
        {
            assertEquals(ObjectiveStateEnum.TRIGGERED, as.getState());
            assertFalse(as.isNeedingNotification());
        }
    }

    /**
     * When the right number of objectives are complete (with or without
     * notifications complete) the quest should become fulfilled and the appropriate
     * report should be generated
     *
     * @throws DatabaseException           shouldn't
     * @throws IllegalQuestChangeException thrown if illegal state change
     */
    @Test
    public void testFulfilling() throws DatabaseException, IllegalQuestChangeException
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a QuestStateChangeReport is sent
        connector.registerObserver(observer, QuestStateChangeReport.class);

        // retrieve objects from database
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

        int originalExperiencePoints = player.getExperiencePoints();

        // build the quest state for later
        QuestState questState = new QuestState(player.getPlayerID(), QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID(), QuestStateEnum.TRIGGERED, false);

        List<ObjectiveState> objectiveStates = Lists.newArrayList();
        objectiveStates.add(new ObjectiveState(1, ObjectiveStateEnum.COMPLETED, true));
        objectiveStates.add(new ObjectiveState(2, ObjectiveStateEnum.COMPLETED, false));
        objectiveStates.add(new ObjectiveState(3, ObjectiveStateEnum.COMPLETED, false));
        objectiveStates.add(new ObjectiveState(4, ObjectiveStateEnum.TRIGGERED, false));
        objectiveStates.add(new ObjectiveState(5, ObjectiveStateEnum.COMPLETED, false));
        questState.addObjectives(objectiveStates);

        // checks for all completed objectives in the quest
        // adds experience points if the quest is complete
        questState.checkForFulfillmentOrFinished();

        // build the expected report
        QuestStateChangeReport expectedReport = new QuestStateChangeReport(player.getPlayerID(), QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID(),
                QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestTitle(),
                QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestDescription(), QuestStateEnum.FULFILLED);

        // since the quest became fulfilled, the observer should be notified by the above report
        verify(observer, times(1)).receiveReport(eq(expectedReport));

        // check that the experience points and quest state have been changed
        assertEquals(originalExperiencePoints + QuestsForTest.ONE_SAME_LOCATION_QUEST.getExperienceGained(), player.getExperiencePoints());
        assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
        assertTrue(questState.isNeedingNotification());
    }

    /**
     * If a quest is already in the process of being fulfilled, no report should be
     * generated
     *
     * @throws DatabaseException           shouldn't
     * @throws IllegalQuestChangeException thrown if illegal state change
     */
    @Test
    public void testFulfillingRepeatedly() throws DatabaseException, IllegalQuestChangeException
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a QuestStateChangeReport is sent
        connector.registerObserver(observer, QuestStateChangeReport.class);

        // retrieve objects from database
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

        int originalExperiencePoints = player.getExperiencePoints();

        // build the quest state for later
        QuestState questState = new QuestState(player.getPlayerID(), QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID(), QuestStateEnum.FULFILLED, false);

        List<ObjectiveState> objectiveStates = Lists.newArrayList();
        objectiveStates.add(new ObjectiveState(1, ObjectiveStateEnum.COMPLETED, true));
        objectiveStates.add(new ObjectiveState(2, ObjectiveStateEnum.COMPLETED, false));
        objectiveStates.add(new ObjectiveState(3, ObjectiveStateEnum.COMPLETED, false));
        objectiveStates.add(new ObjectiveState(4, ObjectiveStateEnum.TRIGGERED, false));
        objectiveStates.add(new ObjectiveState(5, ObjectiveStateEnum.COMPLETED, false));
        questState.addObjectives(objectiveStates);

        // checks for all completed objectives in the quest
        // adds experience points if the quest will become complete
        questState.checkForFulfillmentOrFinished();

        // since the quest was already fulfilled, the observer should not be notified by a QuestStateChangeReport
        verify(observer, never()).receiveReport(any(QuestStateChangeReport.class));

        // check that the experience points and quest state have been changed
        assertEquals(originalExperiencePoints, player.getExperiencePoints());
        assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
        assertFalse(questState.isNeedingNotification());
    }

    /**
     * Test that the new change state method works as intended.
     *
     * @throws IllegalQuestChangeException thrown if changing to a wrong state
     * @throws DatabaseException           shouldn't
     */
    @Test
    public void testChangeStateToTriggered() throws IllegalQuestChangeException, DatabaseException
    {
        QuestState quest = new QuestState(2, 1, QuestStateEnum.AVAILABLE, false);
        quest.changeState(QuestStateEnum.TRIGGERED, false);
        assertEquals(quest.getStateValue(), QuestStateEnum.TRIGGERED);
    }

    /**
     * A finished quest should be marked as finished not expired
     */
    @Test
    public void testCompleteQuestNotExpired()
    {
        QuestState quest = new QuestState(19, 8, QuestStateEnum.COMPLETED, false);
        assertEquals(QuestStateEnum.COMPLETED, quest.getStateValue());
    }

    /**
     * An available quest should be marked as expired
     */
    @Test
    public void testAvailableQuestIsExpired()
    {
        QuestState quest = new QuestState(19, 8, QuestStateEnum.AVAILABLE, false);
        assertEquals(QuestStateEnum.EXPIRED, quest.getStateValue());
    }

    /**
     * A triggered quest should be marked as expired
     */
    @Test
    public void testTriggeredQuestIsExpired()
    {
        QuestState quest = new QuestState(19, 8, QuestStateEnum.TRIGGERED, false);
        assertEquals(QuestStateEnum.EXPIRED, quest.getStateValue());
    }

    /**
     * Tests that when a quest with a CompletedActionType of TRIGGER_QUESTS is completed,
     * the quests associated with it are set to triggered.
     *
     * @throws IllegalQuestChangeException shouldn't
     * @throws DatabaseException           shouldn't
     */
    @Test
    public void CompleteQuestTriggersAnotherQuest() throws IllegalQuestChangeException, DatabaseException
    {
        int playerID = PlayersForTest.STEVE.getPlayerID();
        QuestsForTest quest1 = QuestsForTest.MEET_NPC_TUTOR_QUEST;
        QuestsForTest quest2 = QuestsForTest.MEET_REAL_LIFE_TUTOR_QUEST;
        QuestStateEnum available = QuestStateEnum.AVAILABLE;
        QuestStateEnum triggered = QuestStateEnum.TRIGGERED;
        QuestStateEnum fulfilled = QuestStateEnum.FULFILLED;
        QuestStateEnum completed = QuestStateEnum.COMPLETED;
        boolean needingNotification = true;

        QuestState quest = QuestManager.getSingleton().getQuestStateByID(playerID, quest1.getQuestID());
        quest.changeState(triggered, needingNotification);
        quest.changeState(fulfilled, needingNotification);

        QuestState toBeTriggered = QuestManager.getSingleton().getQuestStateByID(playerID, quest2.getQuestID());

        quest.complete();

        assertEquals(completed, quest.getStateValue());
        assertEquals(triggered, toBeTriggered.getStateValue());

    }
}
