package edu.ship.engr.shipsim.model;

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
 * @author Joshua Ktyal
 * <p>
 * Make sure that the RoamingInfoNPCBehavior acts as expected
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetChatManager
@ResetReportObserverConnector
public class RoamingInfoNPCBehaviorTest
{

    private RoamingInfoNPCBehavior behavior;
    private Player player;

    @BeforeEach
    public void localSetUp()
    {
        OptionsManager.getSingleton().setMapFileTitle(PlayersForTest.MOWREY_FRONTDESK_NPC.getMapName());
        behavior = new RoamingInfoNPCBehavior(PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID());
        player = PlayerManager.getSingleton().addPlayer(PlayersForTest.JOHN.getPlayerID());
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID());
    }

    /**
     * When a question is correctly answered, the player who got it right should
     * have their score incremented.
     */
    @Test
    public void testGetAResponse()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChatMessageToClientReport is sent
        connector.registerObserver(observer, ChatMessageToClientReport.class);

        // setup a report to send to the npc
        NPCChatReport report = new NPCChatReport(player.getPlayerID(), 0, "Heiddy ho, neighborino", player.getPosition(), ChatType.Local);

        // tell the npc to receive the report
        behavior.receiveReport(report);

        // since the NPC sent a chat, verify that the observer received a ChatMessageToClientReport
        verify(observer, times(1)).receiveReport(any(ChatMessageToClientReport.class));
    }
}
