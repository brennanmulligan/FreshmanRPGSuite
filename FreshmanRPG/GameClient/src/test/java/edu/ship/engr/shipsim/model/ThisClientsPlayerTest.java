package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestTest;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.reports.*;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

/**
 * Tests behaviors that are unique to the player playing on this client
 *
 * @author merlin
 */
@GameTest("GameClient")
@ResetClientPlayerManager
@ResetReportObserverConnector
public class ThisClientsPlayerTest
{
    /**
     * Make sure that observers get an appropriate notification when the current
     * player moves
     */
    @Test
    public void notifiesOnMove()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ClientPlayerMovedReport is sent
        connector.registerObserver(observer, ClientPlayerMovedReport.class);

        // set up the report for verification
        ClientPlayerMovedReport expectedReport = new ClientPlayerMovedReport(1, new Position(3, 4), true);

        // setup the player and move them
        ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);
        cp.move(new Position(3, 4));

        // since cp.move sends a ClientPlayerMovedReport, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * Test that the client player notifies on quest request
     */
    @Test
    public void notifiesOnQuestRequest()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a QuestStateReport is sent
        connector.registerObserver(observer, QuestStateReport.class);

        ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);
        ClientPlayerQuestStateDTO q = ClientPlayerQuestTest.createOneQuestWithTwoObjectives();
        cp.addQuest(q);
        ArrayList<ClientPlayerQuestStateDTO> expected = new ArrayList<>();
        expected.add(q);

        // set up a report for verification
        QuestStateReport expectedReport = new QuestStateReport(expected);

        // send the report
        cp.sendCurrentQuestStateReport();

        // since cp.sendCurrentQuestStateReport sends a QuestStateReport, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * Make sure that you can add a quest to ThisClientsPlayer
     */
    @Test
    public void testThisPlayerContainsClientPlayerQuest()
    {
        ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

        ClientPlayerQuestStateDTO q = ClientPlayerQuestTest.createOneQuestWithTwoObjectives();

        cp.addQuest(q);
        assertEquals(2, cp.getQuests().get(0).getObjectiveList().get(1).getObjectiveID());
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
        ClientPlayerQuestStateDTO q = ClientPlayerQuestTest.createOneQuestWithTwoObjectives();
        cp.addQuest(q);

        ClientPlayerObjectiveStateDTO a = new ClientPlayerObjectiveStateDTO(42, "Test Objective ow2", 3,
                ObjectiveStateEnum.HIDDEN, false, true, "Chair", QuestStateEnum.AVAILABLE);
        ClientPlayerQuestStateDTO qow = new ClientPlayerQuestStateDTO(41, "quest title",
                "Test Quest ow1", QuestStateEnum.AVAILABLE, 42, 3, true, null, false);

        qow.addObjective(a);

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
     * Test that we can send a report that contains the objectives that
     * currently need notification
     */
    @Test
    public void testSendObjectivesNeedingNotificationReport()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ObjectiveNeedingNotificationReport is sent
        connector.registerObserver(observer, ObjectiveNeedingNotificationReport.class);

        ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

        ClientPlayerObjectiveStateDTO objective = new ClientPlayerObjectiveStateDTO(1, "Test Objective 1", 0,
                ObjectiveStateEnum.COMPLETED, true, true, "Mom", QuestStateEnum.AVAILABLE);
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "questtitle", "Test Quest 1",
                QuestStateEnum.COMPLETED, 1, 2, true, null, false);
        q.addObjective(objective);
        cp.addQuest(q);

        ArrayList<ClientPlayerQuestStateDTO> questList = new ArrayList<>();
        questList.add(q);

        ObjectiveNeedingNotificationReport expectedReport = new ObjectiveNeedingNotificationReport(
                cp.getID(), q.getQuestID(), objective.getObjectiveID(),
                objective.getObjectiveDescription(), objective.getObjectiveState(), true, "Mom");

        cp.overwriteQuestList(questList);

        // since cp.overwriteQuestList sends a ObjectiveNeedingNotificationReport, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(expectedReport);
    }

    /**
     * Test that we can send a report that contains the quests that currently
     * have need notification
     */
    @Test
    public void testQuestNeedingNotificationReport()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a QuestNeedingNotificationReport is sent
        connector.registerObserver(observer, QuestNeedingNotificationReport.class);

        ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

        ClientPlayerObjectiveStateDTO objective = new ClientPlayerObjectiveStateDTO(1, "Test Objective 1", 0,
                ObjectiveStateEnum.COMPLETED, true, true, "Fred", QuestStateEnum.AVAILABLE);
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "quest title", "Test Quest 1",
                QuestStateEnum.COMPLETED, 1, 2, true, null, false);
        q.addObjective(objective);
        cp.addQuest(q);

        ArrayList<ClientPlayerQuestStateDTO> questList = new ArrayList<>();
        questList.add(q);

        QuestNeedingNotificationReport expectedReport = new QuestNeedingNotificationReport(
                cp.getID(), q.getQuestID(), q.getQuestDescription(), q.getQuestState());

        cp.overwriteQuestList(questList);

        // since cp.overwriteQuestList sends a QuestNeedingNotificationReport, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * Test that we can set the values of ThisClientPlayer's experience info,
     * level description, and # points required for this player to level up for
     * this level
     */
    @Test
    public void testSendExperiencePointsChangeReport()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ExperiencePointsChangeReport is sent
        connector.registerObserver(observer, ExperiencePointsChangeReport.class);

        ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

        int exp = 10;
        LevelRecord rec = new LevelRecord("Felyne Explorer", 10, 10, 7);
        cp.setLevelInfo(rec, 10);

        ExperiencePointsChangeReport expectedReport = new ExperiencePointsChangeReport(exp, rec);

        // since cp.setLevelInfo sends an ExperiencePointsChangeReport, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(expectedReport);
    }

    /**
     * Test that we can set the values of ThisClientPlayer's doubloons
     */
    @Test
    public void testDoubloons()
    {
        ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

        cp.setDoubloons(10);

        assertEquals(10, cp.getDoubloons());
    }

    @Test
    public void testSendDoubloonChangeReport()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a DoubloonChangeReport is sent
        connector.registerObserver(observer, DoubloonChangeReport.class);

        ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

        int exp = 10;
        cp.setDoubloons(10);

        DoubloonChangeReport expectedReport = new DoubloonChangeReport(exp);

        cp.sendDoubloonChangeReport();

        // since cp.sendDoubloonChangeReport sends a DoubloonChangeReport, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

}
