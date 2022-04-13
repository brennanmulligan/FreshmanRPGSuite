package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;
import model.reports.AdventureStateChangeReport;
import datatypes.AdventureStatesForTest;
import datatypes.AdventuresForTest;
import datatypes.QuestStatesForTest;
import datatypes.QuestsForTest;

/**
 * Test class for AdventureState
 *
 * @author Ryan
 *
 */
public class AdventureStateTest
{

	private QuestState questState = null;

	/**
	 *
	 */
	@Before
	public void setUp()
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		QuestManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * Test to ensure the creation of an adventure is correct
	 */
	@Test
	public void testInitialization()
	{
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.HIDDEN, false);

		assertEquals(1, adventure.getID());
		assertEquals(AdventureStateEnum.HIDDEN, adventure.getState());
		assertFalse(adventure.isNeedingNotification());

	}

	/**
	 * Test to check if an adventure's state can be changed from hidden to pending
	 * using the trigger method
	 *
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if changing to a wrong state
	 */
	@Test
	public void testTriggerAdventure()
			throws IllegalAdventureChangeException, DatabaseException, IllegalQuestChangeException
	{
		int playerID = 1;
		AdventureState adv = new AdventureState(1, AdventureStateEnum.HIDDEN, false);
		ArrayList<AdventureState> al = new ArrayList<>();
		al.add(adv);
		QuestState q = new QuestState(playerID, 2, QuestStateEnum.TRIGGERED, false);
		q.addAdventures(al);
		QuestManager.getSingleton().addQuestState(playerID, q);
		adv.trigger();
		assertEquals(AdventureStateEnum.TRIGGERED, adv.getState());
	}

	/**
	 * Test trigger when the adventure's state is not initially hidden
	 *
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if changing to a wrong state
	 */
	@Test(expected = IllegalAdventureChangeException.class)
	public void testTriggerNonHiddenAdventure()
			throws IllegalAdventureChangeException, DatabaseException, IllegalQuestChangeException
	{
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.TRIGGERED, false);

		questState = new QuestState(19, 6, QuestStateEnum.TRIGGERED, false);

		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(adventure);
		questState.addAdventures(adventureList);

		adventure.changeState(AdventureStateEnum.TRIGGERED, false);
		assertEquals(AdventureStateEnum.TRIGGERED, adventure.getState());
		adventure.trigger();
		assertEquals(AdventureStateEnum.TRIGGERED, adventure.getState());
	}

	/**
	 * Completing an adventure doesn't make the quest fulfilled unless we have
	 * completed enough of them
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void testCompleteNotFulfillingAdventure()
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{

		PlayerManager.getSingleton().addPlayer(2);
		ArrayList<AdventureState> al = new ArrayList<>();
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.TRIGGERED, true);
		al.add(adventure);
		QuestState qState = new QuestState(2, 1, QuestStateEnum.TRIGGERED, false);
		qState.addAdventures(al);

		adventure.complete();

		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
		assertTrue(adventure.isNeedingNotification());
		assertEquals(QuestStateEnum.TRIGGERED, qState.getStateValue());
		assertFalse(qState.isNeedingNotification());
	}

	/**
	 * When we complete the right number of adventures, the quest should move to the
	 * state that will cause fulfillment notification to occur
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void testFulfillingAdventure()
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		PlayerManager.getSingleton().addPlayer(1);

		questState = new QuestState(1, QuestsForTest.THE_LITTLE_QUEST.getQuestID(), QuestStateEnum.TRIGGERED, false);

		AdventureState adventure = new AdventureState(1, AdventureStateEnum.TRIGGERED, false);
		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(adventure);
		for (int i = 0; i < QuestsForTest.ONE_BIG_QUEST.getAdventuresForFulfillment() - 1; i++)
		{
			adventureList.add(new AdventureState(i, AdventureStateEnum.TRIGGERED, false));
		}
		questState.addAdventures(adventureList);

		adventure.complete();
		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
		assertTrue(adventure.isNeedingNotification());
		assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
		assertTrue(questState.isNeedingNotification());
	}

	/**
	 * When we complete the right number of adventures, the quest should move to the
	 * state that will cause fulfillment notification to occur
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void testFinishingAdventure()
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		PlayerManager.getSingleton().addPlayer(1);

		questState = new QuestState(1, 2, QuestStateEnum.TRIGGERED, false);

		AdventureState adventure = new AdventureState(1, AdventureStateEnum.TRIGGERED, false);
		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(adventure);
		for (int i = 0; i < QuestsForTest.THE_OTHER_QUEST.getAdventuresForFulfillment() - 1; i++)
		{
			adventureList.add(new AdventureState(i, AdventureStateEnum.COMPLETED, false));
		}
		questState.addAdventures(adventureList);

		adventure.complete();
		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
		assertTrue(adventure.isNeedingNotification());
		assertEquals(QuestStateEnum.COMPLETED, questState.getStateValue());
		assertTrue(questState.isNeedingNotification());
	}

	/**
	 * We can't fulfill a quest that is already fulfilled
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void testCompleteAlreadyFulfilledAdventure()
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		PlayerManager.getSingleton().addPlayer(1);

		questState = new QuestState(1, 2, QuestStateEnum.FULFILLED, false);

		AdventureState adventure = new AdventureState(1, AdventureStateEnum.COMPLETED, false);
		AdventureState adventure2 = new AdventureState(2, AdventureStateEnum.TRIGGERED, false);
		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(adventure);
		adventureList.add(adventure2);
		adventureList.add(new AdventureState(2, AdventureStateEnum.TRIGGERED, false));
		questState.addAdventures(adventureList);

		adventure2.complete();
		assertEquals(AdventureStateEnum.COMPLETED, adventure2.getState());
		assertTrue(adventure2.isNeedingNotification());
		assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
		assertFalse(questState.isNeedingNotification());
	}

	/**
	 * Test that the new change state method works as intended.
	 *
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if changing to a wrong state
	 */
	@Test
	public void testChangeStateToPending()
			throws IllegalAdventureChangeException, DatabaseException, IllegalQuestChangeException
	{

		ArrayList<AdventureState> al = new ArrayList<>();
		AdventureState adventure = new AdventureState(AdventureStatesForTest.PLAYER7_QUEST2_ADV1.getAdventureID(),
				AdventureStatesForTest.PLAYER7_QUEST2_ADV1.getState(),
				AdventureStatesForTest.PLAYER7_QUEST2_ADV1.isNeedingNotification());
		al.add(adventure);
		QuestState qState = new QuestState(7, QuestStatesForTest.PLAYER7_QUEST2.getQuestID(),
				QuestStatesForTest.PLAYER7_QUEST2.getState(),
				QuestStatesForTest.PLAYER7_QUEST2.isNeedingNotification());

		qState.addAdventures(al);

		adventure.changeState(AdventureStateEnum.TRIGGERED, false);
		assertEquals(adventure.getState(), AdventureStateEnum.TRIGGERED);
	}

	/**
	 * Makes sure that a AdventureStateChangeReport is created when the adventures
	 * change state from PENDING to COMPLETED
	 *
	 * @throws IllegalQuestChangeException
	 *             shouldn't throw this
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             shouldn't either
	 */
	@Test
	public void receiveReportChangeState()
			throws IllegalAdventureChangeException, DatabaseException, IllegalQuestChangeException
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ArrayList<AdventureState> al = new ArrayList<>();
		AdventureState state = new AdventureState(AdventureStatesForTest.PLAYER1_QUEST2_ADV2.getAdventureID(),
				AdventureStateEnum.TRIGGERED, AdventureStatesForTest.PLAYER1_QUEST2_ADV2.isNeedingNotification());
		al.add(state);
		QuestState qState = new QuestState(1, QuestStatesForTest.PLAYER1_QUEST2.getQuestID(),
				QuestStatesForTest.PLAYER1_QUEST2.getState(),
				QuestStatesForTest.PLAYER1_QUEST2.isNeedingNotification());

		qState.addAdventures(al);
		AdventureStateChangeReport report = new AdventureStateChangeReport(
				AdventureStatesForTest.PLAYER1_QUEST2_ADV2.getPlayerID(),
				AdventureStatesForTest.PLAYER1_QUEST2_ADV2.getQuestID(),
				AdventureStatesForTest.PLAYER1_QUEST2_ADV2.getAdventureID(),
				AdventuresForTest.QUEST2_ADVENTURE2.getAdventureDescription(), AdventureStateEnum.COMPLETED, false,
				"Criteria Game Location: (4, 15)");
		QualifiedObservableConnector.getSingleton().registerObserver(obs, AdventureStateChangeReport.class);

		obs.receiveReport(EasyMock.eq(report));

		EasyMock.replay(obs);

		state.changeState(AdventureStateEnum.COMPLETED, true);

		EasyMock.verify(obs);
	}

	/**
	 * Test that expired quest hidden adventures can not be triggered
	 *
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if changing to a wrong state
	 */
	@Test
	public void testExpiredQuestHiddenAdventureNotTriggered()
			throws IllegalAdventureChangeException, DatabaseException, IllegalQuestChangeException
	{
		PlayerManager.getSingleton().addPlayer(19);
		questState = new QuestState(19, 8, QuestStateEnum.EXPIRED, false);

		AdventureState adventure = new AdventureState(1, AdventureStateEnum.HIDDEN, false);

		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(adventure);
		questState.addAdventures(adventureList);

		adventure.changeState(AdventureStateEnum.TRIGGERED, false);
		assertEquals(AdventureStateEnum.EXPIRED, adventure.getState());
	}

	/**
	 * Test that expired quest triggered adventures can not be completed
	 *
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if changing to a wrong state
	 */
	@Test
	public void testExpiredQuestTriggeredAdventureNotComplete()
			throws IllegalAdventureChangeException, DatabaseException, IllegalQuestChangeException
	{
		PlayerManager.getSingleton().addPlayer(19);
		questState = new QuestState(19, 8, QuestStateEnum.EXPIRED, false);

		AdventureState adventure = new AdventureState(1, AdventureStateEnum.TRIGGERED, false);

		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(adventure);
		questState.addAdventures(adventureList);

		adventure.changeState(AdventureStateEnum.COMPLETED, false);
		assertEquals(AdventureStateEnum.EXPIRED, adventure.getState());
	}
}
