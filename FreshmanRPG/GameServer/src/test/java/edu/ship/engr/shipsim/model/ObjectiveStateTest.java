package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.*;
import edu.ship.engr.shipsim.model.reports.ObjectiveStateChangeReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Test class for ObjectiveState
 *
 * @author Ryan
 */
@GameTest("GameServer")
@ResetQuestManager
@ResetReportObserverConnector
public class ObjectiveStateTest
{
    private QuestState questState = null;

    /**
     * Test to ensure the creation of an objective is correct
     */
    @Test
    public void testInitialization()
    {
        ObjectiveState objective = new ObjectiveState(1, ObjectiveStateEnum.HIDDEN, false);

        assertEquals(1, objective.getID());
        assertEquals(ObjectiveStateEnum.HIDDEN, objective.getState());
        assertFalse(objective.isNeedingNotification());

    }

    /**
     * Test to check if an objective's state can be changed from hidden to pending
     * using the trigger method
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws DatabaseException               shouldn't
     * @throws IllegalQuestChangeException     thrown if changing to a wrong state
     */
    @Test
    public void testTriggerObjective()
            throws IllegalObjectiveChangeException, DatabaseException, IllegalQuestChangeException
    {
        int playerID = 1;
        ObjectiveState adv = new ObjectiveState(1, ObjectiveStateEnum.HIDDEN, false);
        ArrayList<ObjectiveState> al = new ArrayList<>();
        al.add(adv);
        QuestState q = new QuestState(playerID, 2, QuestStateEnum.TRIGGERED, false);
        q.addObjectives(al);
        QuestManager.getSingleton().addQuestState(playerID, q);
        adv.trigger();
        assertEquals(ObjectiveStateEnum.TRIGGERED, adv.getState());
    }

    /**
     * Test to check if an objective's state can be changed from triggered to late
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws DatabaseException               shouldn't
     * @throws IllegalQuestChangeException     thrown if changing to a wrong state
     */
    @Test
    public void testLateQuestObjective()
            throws IllegalObjectiveChangeException, DatabaseException, IllegalQuestChangeException
    {
        int playerID = 1;
        ObjectiveState adv = new ObjectiveState(1, ObjectiveStateEnum.HIDDEN, false);
        ArrayList<ObjectiveState> al = new ArrayList<>();
        al.add(adv);
        QuestState q = new QuestState(playerID, 2, QuestStateEnum.TRIGGERED, false);
        q.addObjectives(al);
        QuestManager.getSingleton().addQuestState(playerID, q);
        adv.trigger();
        assertEquals(ObjectiveStateEnum.TRIGGERED, adv.getState());
        adv.missed();
        assertEquals(ObjectiveStateEnum.LATE, adv.getState());
    }

    /**
     * Test trigger when the objective's state is not initially hidden
     */
    @Test
    public void testTriggerNonHiddenObjective()
    {
        assertThrows(IllegalObjectiveChangeException.class, () ->
        {
            ObjectiveState objective = new ObjectiveState(1, ObjectiveStateEnum.TRIGGERED, false);

            questState = new QuestState(19, 6, QuestStateEnum.TRIGGERED, false);

            ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
            objectiveList.add(objective);
            questState.addObjectives(objectiveList);

            objective.changeState(ObjectiveStateEnum.TRIGGERED, false);
            assertEquals(ObjectiveStateEnum.TRIGGERED, objective.getState());
            objective.trigger();
            assertEquals(ObjectiveStateEnum.TRIGGERED, objective.getState());
        });
    }

    /**
     * Completing an objective doesn't make the quest fulfilled unless we have
     * completed enough of them
     *
     * @throws DatabaseException               shouldn't
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     */
    @Test
    public void testCompleteNotFulfillingObjective()
            throws DatabaseException, IllegalObjectiveChangeException, IllegalQuestChangeException
    {

        PlayerManager.getSingleton().addPlayer(2);
        ArrayList<ObjectiveState> al = new ArrayList<>();
        ObjectiveState objective = new ObjectiveState(1, ObjectiveStateEnum.TRIGGERED, true);
        al.add(objective);
        QuestState qState = new QuestState(2, 1, QuestStateEnum.TRIGGERED, false);
        qState.addObjectives(al);

        objective.complete();

        assertEquals(ObjectiveStateEnum.COMPLETED, objective.getState());
        assertTrue(objective.isNeedingNotification());
        assertEquals(QuestStateEnum.TRIGGERED, qState.getStateValue());
        assertFalse(qState.isNeedingNotification());
    }

    /**
     * When we complete the right number of objectives, the quest should move to the
     * state that will cause fulfillment notification to occur
     *
     * @throws DatabaseException               shouldn't
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     */
    @Test
    public void testFulfillingObjective()
            throws DatabaseException, IllegalObjectiveChangeException, IllegalQuestChangeException
    {
        PlayerManager.getSingleton().addPlayer(1);

        questState = new QuestState(1, QuestsForTest.THE_LITTLE_QUEST.getQuestID(), QuestStateEnum.TRIGGERED, false);

        ObjectiveState objective = new ObjectiveState(1, ObjectiveStateEnum.TRIGGERED, false);
        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(objective);
        for (int i = 0; i < QuestsForTest.ONE_BIG_QUEST.getObjectiveForFulfillment() - 1; i++)
        {
            objectiveList.add(new ObjectiveState(i, ObjectiveStateEnum.TRIGGERED, false));
        }
        questState.addObjectives(objectiveList);

        objective.complete();
        assertEquals(ObjectiveStateEnum.COMPLETED, objective.getState());
        assertTrue(objective.isNeedingNotification());
        assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
        assertTrue(questState.isNeedingNotification());
    }

    /**
     * When we complete the right number of objectives, the quest should move to the
     * state that will cause fulfillment notification to occur
     *
     * @throws DatabaseException               shouldn't
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     */
    @Test
    public void testFinishingObjective()
            throws DatabaseException, IllegalObjectiveChangeException, IllegalQuestChangeException
    {
        PlayerManager.getSingleton().addPlayer(1);

        questState = new QuestState(1, 2, QuestStateEnum.TRIGGERED, false);

        ObjectiveState objective = new ObjectiveState(1, ObjectiveStateEnum.TRIGGERED, false);
        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(objective);
        for (int i = 0; i < QuestsForTest.THE_OTHER_QUEST.getObjectiveForFulfillment() - 1; i++)
        {
            objectiveList.add(new ObjectiveState(i, ObjectiveStateEnum.COMPLETED, false));
        }
        questState.addObjectives(objectiveList);

        objective.complete();
        assertEquals(ObjectiveStateEnum.COMPLETED, objective.getState());
        assertTrue(objective.isNeedingNotification());
        assertEquals(QuestStateEnum.COMPLETED, questState.getStateValue());
        assertTrue(questState.isNeedingNotification());
    }

    /**
     * We can't fulfill a quest that is already fulfilled
     *
     * @throws DatabaseException               shouldn't
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     */
    @Test
    public void testCompleteAlreadyFulfilledObjective()
            throws DatabaseException, IllegalObjectiveChangeException, IllegalQuestChangeException
    {
        PlayerManager.getSingleton().addPlayer(1);

        questState = new QuestState(1, 2, QuestStateEnum.FULFILLED, false);

        ObjectiveState objective = new ObjectiveState(1, ObjectiveStateEnum.COMPLETED, false);
        ObjectiveState objective2 = new ObjectiveState(2, ObjectiveStateEnum.TRIGGERED, false);
        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(objective);
        objectiveList.add(objective2);
        objectiveList.add(new ObjectiveState(2, ObjectiveStateEnum.TRIGGERED, false));
        questState.addObjectives(objectiveList);

        objective2.complete();
        assertEquals(ObjectiveStateEnum.COMPLETED, objective2.getState());
        assertTrue(objective2.isNeedingNotification());
        assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
        assertFalse(questState.isNeedingNotification());
    }

    /**
     * Test that the new change state method works as intended.
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws DatabaseException               shouldn't
     * @throws IllegalQuestChangeException     thrown if changing to a wrong state
     */
    @Test
    public void testChangeStateToPending()
            throws IllegalObjectiveChangeException, DatabaseException, IllegalQuestChangeException
    {

        ArrayList<ObjectiveState> al = new ArrayList<>();
        ObjectiveState objective = new ObjectiveState(ObjectiveStatesForTest.PLAYER7_QUEST2_ADV1.getObjectiveID(),
                ObjectiveStatesForTest.PLAYER7_QUEST2_ADV1.getState(),
                ObjectiveStatesForTest.PLAYER7_QUEST2_ADV1.isNeedingNotification());
        al.add(objective);
        QuestState qState = new QuestState(7, QuestStatesForTest.PLAYER7_QUEST2.getQuestID(),
                QuestStatesForTest.PLAYER7_QUEST2.getState(),
                QuestStatesForTest.PLAYER7_QUEST2.isNeedingNotification());

        qState.addObjectives(al);

        objective.changeState(ObjectiveStateEnum.TRIGGERED, false);
        assertEquals(objective.getState(), ObjectiveStateEnum.TRIGGERED);
    }

    /**
     * Makes sure that a ObjectiveStateChangeReport is created when the objectives
     * change state from PENDING to COMPLETED
     *
     * @throws IllegalQuestChangeException     shouldn't throw this
     * @throws DatabaseException               shouldn't
     * @throws IllegalObjectiveChangeException shouldn't either
     */
    @Test
    public void receiveReportChangeState()
            throws IllegalObjectiveChangeException, DatabaseException, IllegalQuestChangeException
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ObjectiveStateChangeReport is sent
        connector.registerObserver(observer, ObjectiveStateChangeReport.class);

        List<ObjectiveState> objectiveStates = Lists.newArrayList();
        ObjectiveState objectiveState = new ObjectiveState(ObjectiveStatesForTest.PLAYER1_QUEST2_ADV2.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, ObjectiveStatesForTest.PLAYER1_QUEST2_ADV2.isNeedingNotification());
        objectiveStates.add(objectiveState);

        QuestState questState = new QuestState(1, QuestStatesForTest.PLAYER1_QUEST2.getQuestID(),
                QuestStatesForTest.PLAYER1_QUEST2.getState(),
                QuestStatesForTest.PLAYER1_QUEST2.isNeedingNotification());
        questState.addObjectives(objectiveStates);

        // change the state of the objective, which should send out a report
        objectiveState.changeState(ObjectiveStateEnum.COMPLETED, true);

        // the report we expect the observer to be notified with
        ObjectiveStateChangeReport expectedReport = new ObjectiveStateChangeReport(
                ObjectiveStatesForTest.PLAYER1_QUEST2_ADV2.getPlayerID(),
                ObjectiveStatesForTest.PLAYER1_QUEST2_ADV2.getQuestID(),
                ObjectiveStatesForTest.PLAYER1_QUEST2_ADV2.getObjectiveID(),
                ObjectivesForTest.QUEST2_OBJECTIVE2.getObjectiveDescription(), ObjectiveStateEnum.COMPLETED, false,
                null);

        // since the objective state was changed, the observer should receive the above report
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * Test that expired quest hidden objectives can not be triggered
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws DatabaseException               shouldn't
     * @throws IllegalQuestChangeException     thrown if changing to a wrong state
     */
    @Test
    public void testExpiredQuestHiddenObjectiveNotTriggered()
            throws IllegalObjectiveChangeException, DatabaseException, IllegalQuestChangeException
    {
        PlayerManager.getSingleton().addPlayer(19);
        questState = new QuestState(19, 8, QuestStateEnum.EXPIRED, false);

        ObjectiveState objective = new ObjectiveState(1, ObjectiveStateEnum.HIDDEN, false);

        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(objective);
        questState.addObjectives(objectiveList);

        objective.changeState(ObjectiveStateEnum.TRIGGERED, false);
        assertEquals(ObjectiveStateEnum.EXPIRED, objective.getState());
    }

    /**
     * Test that expired quest triggered objectives can not be completed
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong state
     * @throws DatabaseException               shouldn't
     * @throws IllegalQuestChangeException     thrown if changing to a wrong state
     */
    @Test
    public void testExpiredQuestTriggeredObjectiveNotComplete()
            throws IllegalObjectiveChangeException, DatabaseException, IllegalQuestChangeException
    {
        PlayerManager.getSingleton().addPlayer(19);
        questState = new QuestState(19, 8, QuestStateEnum.EXPIRED, false);

        ObjectiveState objective = new ObjectiveState(1, ObjectiveStateEnum.TRIGGERED, false);

        ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
        objectiveList.add(objective);
        questState.addObjectives(objectiveList);

        objective.changeState(ObjectiveStateEnum.COMPLETED, false);
        assertEquals(ObjectiveStateEnum.EXPIRED, objective.getState());
    }
}
