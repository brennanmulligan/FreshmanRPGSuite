package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import dataDTO.ClientPlayerQuestTest;
import datatypes.PlayersForTest;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import dataDTO.ClientPlayerAdventureStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import datasource.LevelRecord;
import datatypes.AdventureStateEnum;
import datatypes.Position;
import datatypes.QuestStateEnum;
import model.reports.AdventureNeedingNotificationReport;
import model.reports.ClientPlayerMovedReport;
import model.reports.ExperiencePointsChangeReport;
import model.reports.KnowledgePointsChangeReport;
import model.reports.QuestNeedingNotificationReport;
import model.reports.QuestStateReport;

/**
 * Tests behaviors that are unique to the player playing on this client
 *
 * @author merlin
 *
 */
public class ThisClientsPlayerTest
{

	/**
	 * Reset the singletons
	 */
	@Before
	public void setup()
	{
		ClientPlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * Make sure that observers get an appropriate notification when the current
	 * player moves
	 */
	@Test
	public void notifiesOnMove()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ClientPlayerMovedReport.class);
		ClientPlayerMovedReport report = new ClientPlayerMovedReport(1,
				new Position(3, 4), true);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);
		cp.move(new Position(3, 4));

		EasyMock.verify(obs);

	}

	/**
	 * Test that the client player notifies on quest request
	 */
	@Test
	public void notifiesOnQuestRequest()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);
		ClientPlayerQuestStateDTO q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();
		cp.addQuest(q);
		ArrayList<ClientPlayerQuestStateDTO> expected = new ArrayList<>();
		expected.add(q);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				QuestStateReport.class);
		QuestStateReport report = new QuestStateReport(expected);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.sendCurrentQuestStateReport();

		EasyMock.verify(obs);

	}

	/**
	 * Make sure that you can add a quest to ThisClientsPlayer
	 */
	@Test
	public void testThisPlayerContainsClientPlayerQuest()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		ClientPlayerQuestStateDTO q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();

		cp.addQuest(q);
		assertEquals(2, cp.getQuests().get(0).getAdventureList().get(1).getAdventureID());
		assertEquals("Test Quest 1", cp.getQuests().get(0).getQuestDescription());
	}

	static ThisClientsPlayer setUpThisClientsPlayer(PlayersForTest player)
	{
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		pm.initiateLogin(player.getPlayerName(), player.getPlayerPassword());
		ThisClientsPlayer cp = null;

		try
		{
			cp = pm.finishLogin(player.getPlayerID());
		}
		catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
			fail("Could not Tthis client's player from login");
		}
		return cp;
	}

	/**
	 * Test that we can overwrite ("whomp") this client player quest list
	 */
	@Test
	public void canWhompOnQuestList()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);
		ClientPlayerQuestStateDTO q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();
		cp.addQuest(q);

		ClientPlayerAdventureStateDTO a = new ClientPlayerAdventureStateDTO(42, "Test Adventure ow2", 3,
				AdventureStateEnum.HIDDEN, false, true, "Chair", QuestStateEnum.AVAILABLE);
		ClientPlayerQuestStateDTO qow = new ClientPlayerQuestStateDTO(41, "quest title",
				"Test Quest ow1", QuestStateEnum.AVAILABLE, 42, 3, true, null);

		qow.addAdventure(a);

		ArrayList<ClientPlayerQuestStateDTO> qList = new ArrayList<>();
		qList.add(qow);
		cp.overwriteQuestList(qList);

		assertEquals(qow, cp.getQuests().get(0));
	}

	/**
	 * Test that we can set the values of ThisClientPlayer's experience info,
	 * level description, and # points required for this player to level up for
	 * this level
	 */
	@Test
	public void testAllExperienceInfo()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		LevelRecord rec = new LevelRecord("Felyne Explorer", 100, 10, 7);
		cp.setLevelInfo(rec, 10);

		assertEquals(10, cp.getExperiencePoints());
		assertEquals("Felyne Explorer", cp.getLevelRecord().getDescription());
		assertEquals(100, cp.getLevelRecord().getLevelUpPoints());
	}

	/**
	 * Test that we can send a report that contains the adventures that
	 * currently need notification
	 */
	@Test
	public void testSendAdventuresNeedingNotificationReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		ClientPlayerAdventureStateDTO a = new ClientPlayerAdventureStateDTO(1, "Test Adventure 1", 0,
				AdventureStateEnum.COMPLETED, true, true, "Mom", QuestStateEnum.AVAILABLE);
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "questtitle", "Test Quest 1",
				QuestStateEnum.COMPLETED, 1, 2, true, null);
		q.addAdventure(a);
		cp.addQuest(q);

		ArrayList<ClientPlayerQuestStateDTO> questList = new ArrayList<>();
		questList.add(q);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				AdventureNeedingNotificationReport.class);
		AdventureNeedingNotificationReport report = new AdventureNeedingNotificationReport(
				cp.getID(), q.getQuestID(), a.getAdventureID(),
				a.getAdventureDescription(), a.getAdventureState(), true, "Mom");
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.overwriteQuestList(questList);

		EasyMock.verify(obs);
	}

	/**
	 * Test that we can send a report that contains the quests that currently
	 * have need notification
	 */
	@Test
	public void testQuestNeedingNotificationReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		ClientPlayerAdventureStateDTO a = new ClientPlayerAdventureStateDTO(1, "Test Adventure 1", 0,
				AdventureStateEnum.COMPLETED, true, true, "Fred", QuestStateEnum.AVAILABLE);
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "quest title", "Test Quest 1",
				QuestStateEnum.COMPLETED, 1, 2, true, null);
		q.addAdventure(a);
		cp.addQuest(q);

		ArrayList<ClientPlayerQuestStateDTO> questList = new ArrayList<>();
		questList.add(q);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				QuestNeedingNotificationReport.class);
		QuestNeedingNotificationReport report = new QuestNeedingNotificationReport(
				cp.getID(), q.getQuestID(), q.getQuestDescription(), q.getQuestState());
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.overwriteQuestList(questList);

		EasyMock.verify(obs);
	}

	/**
	 * Test that we can set the values of ThisClientPlayer's experience info,
	 * level description, and # points required for this player to level up for
	 * this level
	 */
	@Test
	public void testSendExperiencePointsChangeReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		int exp = 10;
		LevelRecord rec = new LevelRecord("Felyne Explorer", 10, 10, 7);
		cp.setLevelInfo(rec, 10);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, ExperiencePointsChangeReport.class);
		ExperiencePointsChangeReport report = new ExperiencePointsChangeReport(exp, rec);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.sendExperiencePointsChangeReport();

		EasyMock.verify(obs);
	}

	/**
	 * Test that we can set the values of ThisClientPlayer's knowledge info
	 */
	@Test
	public void testAllKnowledgeInfo()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		cp.setKnowledgePoints(10);

		assertEquals(10, cp.getKnowledgePoints());
	}

	/**
	 *
	 */
	@Test
	public void testSendKnowledgePointsChangeReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		int exp = 10;
		cp.setKnowledgePoints(10);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				KnowledgePointsChangeReport.class);
		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(exp);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.sendKnowledgePointsChangeReport();
		;

		EasyMock.verify(obs);
	}

}
