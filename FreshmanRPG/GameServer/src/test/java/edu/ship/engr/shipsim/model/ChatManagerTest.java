package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.cheatCodeBehaviors.MockCheatCodeBehavior;
import edu.ship.engr.shipsim.model.reports.ChatMessageReceivedReport;
import edu.ship.engr.shipsim.model.reports.ChatMessageToClientReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetChatManager;
import edu.ship.engr.shipsim.testing.annotations.ResetQualifiedObservableConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Dave
 * <p>
 * Make sure the ChatManager behaves properly.
 */
@GameTest("GameServer")
@ResetChatManager
@ResetQualifiedObservableConnector
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
        QualifiedObservableConnector connector = spy(QualifiedObservableConnector.getSingleton());
        QualifiedObserver observer = mock(QualifiedObserver.class);

        // register the observer to be notified when a ChatMessageToClientReport is sent
        connector.registerObserver(observer, ChatMessageToClientReport.class);

        // send the report to the client
        ChatManager.getSingleton().sendChatToClients(42, 0, "message", mock(Position.class), ChatType.Local);

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
        QualifiedObservableConnector connector = spy(QualifiedObservableConnector.getSingleton());
        QualifiedObserver observer = mock(QualifiedObserver.class);

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
        // mock the connector and observer
        QualifiedObservableConnector connector = spy(QualifiedObservableConnector.getSingleton());
        QualifiedObserver observer = mock(QualifiedObserver.class);

        // register the observer to be notified if a ChatMessageToClientReport is sent
        connector.registerObserver(observer, ChatMessageToClientReport.class);

        // send a chat message, which isn't a cheat code
        ChatManager.getSingleton().processChatMessage(42, 0, "message", mock(Position.class), ChatType.Local);

        // verify that the observer received a ChatMessageToClientReport, since the chat wasn't a cheat code
        verify(observer, times(1)).receiveReport(any(ChatMessageToClientReport.class));
    }

}