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
import model.reports.QuestStateChangeReport;
import datatypes.PlayersForTest;
import datatypes.QuestsForTest;

/**
 * Test for the QuestState Class
 *
 * @author Ryan
 *
 */
public class QuestStateTest
{

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
	 * Test adding an ArrayList of adventures into quest
	 */
	@Test
	public void testAddAdventures()
	{
		QuestState qs = new QuestState(2, 1, QuestStateEnum.AVAILABLE, true);
		ArrayList<AdventureState> adventureList = new ArrayList<>();
		AdventureState as1 = new AdventureState(1, AdventureStateEnum.HIDDEN, false);
		AdventureState as2 = new AdventureState(2, AdventureStateEnum.HIDDEN, false);

		adventureList.add(as1);
		adventureList.add(as2);

		qs.addAdventures(adventureList);

		assertEquals(2, qs.getSizeOfAdventureList());
	}

	/**
	 * Test the change in quest's state when triggered
	 *
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testTriggerQuest()
			throws IllegalAdventureChangeException, IllegalQuestChangeException, DatabaseException
	{
		QuestState quest = new QuestState(2, 1, QuestStateEnum.AVAILABLE, true);
		quest.trigger();
		assertEquals(QuestStateEnum.TRIGGERED, quest.getStateValue());
		assertTrue(quest.isNeedingNotification());
	}

	/**
	 * Test to make sure you can't trigger finished quests
	 *
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test(expected = IllegalQuestChangeException.class)
	public void testTriggerFinishedQuest()
			throws IllegalQuestChangeException, DatabaseException, IllegalAdventureChangeException
	{
		QuestState quest = new QuestState(2, 1, QuestStateEnum.COMPLETED, false);
		quest.trigger();

	}

	/**
	 * Test that when a quest is triggered, its adventures get triggered as well
	 *
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testTriggerQuestsAdventures()
			throws IllegalAdventureChangeException, IllegalQuestChangeException, DatabaseException
	{
		QuestState qs = new QuestState(2, 1, QuestStateEnum.AVAILABLE, false);
		ArrayList<AdventureState> adList = new ArrayList<>();

		AdventureState as1 = new AdventureState(1, AdventureStateEnum.HIDDEN, false);
		AdventureState as2 = new AdventureState(2, AdventureStateEnum.HIDDEN, false);
		AdventureState as3 = new AdventureState(3, AdventureStateEnum.HIDDEN, false);

		adList.add(as1);
		adList.add(as2);
		adList.add(as3);

		qs.addAdventures(adList);
		adList = qs.getAdventureList();

		qs.trigger();

		for (AdventureState as : adList)
		{
			assertEquals(AdventureStateEnum.TRIGGERED, as.getState());
			assertFalse(as.isNeedingNotification());
		}
	}

	/**
	 * When the right number of adventures are complete (with or without
	 * notifications complete) the quest should become fulfilled and the appropriate
	 * report should be generated
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void testFulfilling() throws DatabaseException, IllegalQuestChangeException
	{

		PlayerManager.getSingleton().addPlayer(2);
		int origExperiencePoints = PlayerManager.getSingleton().getPlayerFromID(2).getExperiencePoints();
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, QuestStateChangeReport.class);
		QuestStateChangeReport rpt = new QuestStateChangeReport(2, QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID(),
				QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestTitle(),
				QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestDescription(), QuestStateEnum.FULFILLED);
		obs.receiveReport(rpt);
		EasyMock.replay(obs);


		QuestState qs = new QuestState(2, QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID(), QuestStateEnum.TRIGGERED,
				false);
		ArrayList<AdventureState> adList = new ArrayList<>();

		PlayerManager.getSingleton().addPlayer(2);

		AdventureState as = new AdventureState(1, AdventureStateEnum.COMPLETED, true);
		adList.add(as);
		as = new AdventureState(2, AdventureStateEnum.COMPLETED, false);

		adList.add(as);
		as = new AdventureState(3, AdventureStateEnum.COMPLETED, false);
		adList.add(as);
		as = new AdventureState(4, AdventureStateEnum.TRIGGERED, false);
		adList.add(as);
		as = new AdventureState(5, AdventureStateEnum.COMPLETED, false);
		adList.add(as);

		qs.addAdventures(adList);
		qs.checkForFulfillmentOrFinished();
		assertEquals(origExperiencePoints + QuestsForTest.ONE_SAME_LOCATION_QUEST.getExperienceGained(),
				PlayerManager.getSingleton().getPlayerFromID(2).getExperiencePoints());
		assertEquals(QuestStateEnum.FULFILLED, qs.getStateValue());
		assertTrue(qs.isNeedingNotification());
		EasyMock.verify(obs);
	}

	/**
	 * If a quest is already in the process of being fulfilled, no report should be
	 * generated
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void testFulfillingRepeatedly() throws DatabaseException, IllegalQuestChangeException
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, QuestStateChangeReport.class);
		EasyMock.replay(obs);
		QuestState qs = new QuestState(1, 3, QuestStateEnum.FULFILLED, false);
		ArrayList<AdventureState> adList = new ArrayList<>();

		AdventureState as = new AdventureState(1, AdventureStateEnum.COMPLETED, false);
		adList.add(as);
		as = new AdventureState(2, AdventureStateEnum.COMPLETED, true);
		adList.add(as);
		as = new AdventureState(3, AdventureStateEnum.COMPLETED, false);
		adList.add(as);
		as = new AdventureState(4, AdventureStateEnum.TRIGGERED, false);
		adList.add(as);
		as = new AdventureState(5, AdventureStateEnum.COMPLETED, false);
		adList.add(as);

		qs.addAdventures(adList);
		qs.checkForFulfillmentOrFinished();
		assertEquals(QuestStateEnum.FULFILLED, qs.getStateValue());
		assertFalse(qs.isNeedingNotification());
		EasyMock.verify(obs);
	}

	/**
	 * Test that the new change state method works as intended.
	 *
	 * @throws IllegalQuestChangeException
	 *             thrown if changing to a wrong state
	 * @throws DatabaseException
	 *             shouldn't
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
	 * @throws DatabaseException shouldn't
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
