package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.cheatCodeBehaviors.MockCheatCodeBehavior;
import edu.ship.engr.shipsim.model.reports.ChatMessageReceivedReport;
import edu.ship.engr.shipsim.model.reports.ChatMessageToClientReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetChatManager;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Dave
 * <p>
 * Make sure the ChatManager behaves properly.
 */
@GameTest("GameServer")
@ResetChatManager
@ResetPlayerManager
@ResetReportObserverConnector
public class ChatManagerTest
{
    /**
     * Make sure that ChatManger behaves as a singleton
     */
    @Test
    public void testSingleton()
    {
        ChatManager cm1 = ChatManager.getSingleton();
        ChatManager cm2 = ChatManager.getSingleton();

        assertSame(cm1, cm2);
        ChatManager.resetSingleton();
        assertNotSame(cm1, ChatManager.getSingleton());
    }

    /**
     * Make sure that ChatManager notifies its observers when it creates a
     * SendChatMessage report.
     */
    @Test
    public void testNotifiesObserversOnDirectSend()
    {
        PlayerManager.getSingleton().addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified when a ChatMessageToClientReport is sent
        connector.registerObserver(observer, ChatMessageToClientReport.class);

        // send the report to the client
        ChatManager.getSingleton().sendChatToClients(PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.MERLIN.getPlayerID(), "message", mock(Position.class), ChatType.Local);

        // verify that the observer received a report
        verify(observer, times(1)).receiveReport(any(ChatMessageToClientReport.class));
    }

    /**
     * If it sees a cheat code, it should process it and NOT send the chat to
     * everyone
     */
    @Test
    public void handlesCheatCodes()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a Chat MessageReceivedReport
        connector.registerObserver(observer, ChatMessageReceivedReport.class);

        // send the chat message, which is actually a cheat code
        MockCheatCodeBehavior.gaveCheat = false;
        ChatManager.getSingleton().processChatMessage(42, 0, MockCheatCodeBehavior.CHAT_TEXT, mock(Position.class), ChatType.Local);
        assertTrue(MockCheatCodeBehavior.gaveCheat);

        // since the message was a cheat code, a ChatMessageToClientReport shouldn't have been sent
        verify(observer, never()).receiveReport(any(ChatMessageToClientReport.class));
    }

    /**
     * If it sees a cheat code, it should process it and NOT send the chat to
     * everyone
     */
    @Test
    public void sendTheChatIfItIsntACheatCode()
    {
        PlayerManager.getSingleton().addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());

        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChatMessageToClientReport is sent
        connector.registerObserver(observer, ChatMessageToClientReport.class);

        // send a chat message, which isn't a cheat code
        ChatManager.getSingleton().processChatMessage(PlayersForTest.MERLIN.getPlayerID(), 0, "message", mock(Position.class), ChatType.Local);

        // verify that the observer received a ChatMessageToClientReport, since the chat wasn't a cheat code
        verify(observer, times(1)).receiveReport(any(ChatMessageToClientReport.class));
    }

    /**
     * Conditions for when a local message should or shouldn't be received
     */
    @Test
    public void testLocalMessage()
    {
        /**
         * Both are start on the same position 52,52 so they should return true
         */
        assertTrue(ChatManager.getSingleton().canReceiveLocalMessage(
                PlayersForTest.JOHN.getPosition(), PlayersForTest.MERLIN.getPosition()));
        /**
         * Edge case for the method working both ways
         */
        assertTrue(ChatManager.getSingleton().canReceiveLocalMessage(
                PlayersForTest.MERLIN.getPosition(), PlayersForTest.JOHN.getPosition()));
        /**
         * These are too far away to receive a message from each other should return false
         */
        assertFalse(ChatManager.getSingleton().canReceiveLocalMessage(
                PlayersForTest.JOHN.getPosition(), PlayersForTest.TEACHER_NPC.getPosition()));
        /**
         * Edge case for the method not working when playerID's are switched
         */
        assertFalse(ChatManager.getSingleton().canReceiveLocalMessage(
                PlayersForTest.TEACHER_NPC.getPosition(), PlayersForTest.JOHN.getPosition()));

    }

}