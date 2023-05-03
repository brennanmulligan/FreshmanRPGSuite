package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.ChatMessageToClientReport;
import edu.ship.engr.shipsim.model.reports.NPCChatReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetChatManager;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * @author merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetChatManager
@ResetReportObserverConnector
public class TutorBehaviorTest
{
    private TutorBehavior behavior;

    /**
     * @throws DatabaseException shouldn't
     *                           Set up the behavior for each test
     */
    @BeforeEach
    public void localSetUp() throws DatabaseException
    {
        behavior = new TutorBehavior(PlayersForTest.TUTOR.getPlayerID());
    }

    /**
     * Makes sure that the tutor responds to a chat message that is ChatType.Local
     */
    @Test
    public void testTutorRespondsToPlayer()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChatMessageToClientReport is sent
        connector.registerObserver(observer, ChatMessageToClientReport.class);

        // add a player and a tutor to the player manager
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.NICK.getPlayerID());
        PlayerManager.getSingleton().addPlayer(PlayersForTest.TUTOR.getPlayerID());

        // setup the report to send
        NPCChatReport report = new NPCChatReport(player.getPlayerID(), 0, "Hello, tutor", player.getPosition(), ChatType.Local);

        // tell the npc to receive the report
        behavior.receiveReport(report);

        // since the tutor responded to the player's chat, verify that a ChatMessageToClientReport was sent by the npc
        verify(observer, times(1)).receiveReport(any(ChatMessageToClientReport.class));
    }

    /**
     * Makes sure that the tutor doesn't respond to a chat message that isnt in ChatType.Local
     */
    @Test
    public void testTutorDoesntRespondToPlayer()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChatMessageToClientReport is sent
        connector.registerObserver(observer, ChatMessageToClientReport.class);

        // add a player and a tutor to the player manager
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.NICK.getPlayerID());
        PlayerManager.getSingleton().addPlayer(PlayersForTest.TUTOR.getPlayerID());

        // set up the report to send
        NPCChatReport report = new NPCChatReport(player.getPlayerID(), 0, "Hello, tutor", player.getPosition(), ChatType.Zone);

        // tell the npc to receive the report
        behavior.receiveReport(report);

        // since the tutor didn't respond to the player's chat, verify that a ChatMessageToClientReport wasn't sent by the npc
        verify(observer, never()).receiveReport(any(ChatMessageToClientReport.class));
    }
}
