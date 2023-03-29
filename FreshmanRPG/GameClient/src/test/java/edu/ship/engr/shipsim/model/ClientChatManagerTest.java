package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.ChatReceivedReport;
import edu.ship.engr.shipsim.model.reports.ChatSentReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the functionality of the ChatManager
 *
 * @author Steve
 */
@GameTest("GameClient")
@ResetClientPlayerManager
@ResetReportObserverConnector
public class ClientChatManagerTest
{
    /**
     * Always start with a new singleton
     */
    @BeforeEach
    public void reset()
    {
        ClientChatManager.resetSingleton();
    }


    /**
     * There should be only one manager
     */
    @Test
    public void testSingleton()
    {
        ClientChatManager cm1 = ClientChatManager.getSingleton();
        assertSame(cm1, ClientChatManager.getSingleton());
        ClientChatManager.resetSingleton();
        assertNotSame(cm1, ClientChatManager.getSingleton());
    }

    /**
     * When an area chat is received, a report will always be sent out
     */
    @Test
    public void notifiesOnAreaChatReceived()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChatReceivedReport is sent
        connector.registerObserver(observer, ChatReceivedReport.class);

        // set up the report for the verification later
        ChatReceivedReport expectedReport = new ChatReceivedReport(42, 0, "message", ChatType.Zone);

        // send the chat message, which should send a ChatReceivedReport
        ClientChatManager.getSingleton().sendChatToUI(42, 0, "message", new Position(0, 0), ChatType.Zone);

        // since the ClientChatManager sent a report, verify that the observer was notified about it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * When a local chat is received, a report will always be sent out
     *
     * @throws NotBoundException     shouldn't
     * @throws AlreadyBoundException shouldn't
     */
    @Test
    public void notifiesOnLocalChatReceived() throws AlreadyBoundException, NotBoundException
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChatReceivedReport is sent
        connector.registerObserver(observer, ChatReceivedReport.class);

        ClientPlayerManager.getSingleton().initiateLogin("X", "X");
        ClientPlayer p = ClientPlayerManager.getSingleton().finishLogin(1);
        p.setPosition(new Position(5, 5));

        // set up the report for the verification later
        ChatReceivedReport expectedReport = new ChatReceivedReport(1, 0, "message", ChatType.Local);

        // send the chat message, which should send a ChatReceivedReport
        ClientChatManager.getSingleton().sendChatToUI(1, 0, "message", new Position(0, 0), ChatType.Local);

        // since the ClientChatManager sent a report, verify that the observer was notified about it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * Properly sends a report for a message going to the server
     *
     * @throws NotBoundException     shouldn't
     * @throws AlreadyBoundException shouldn't
     */
    @Test
    public void notifiesOnSendChatToServer() throws AlreadyBoundException, NotBoundException
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChatSentReport is sent
        connector.registerObserver(observer, ChatSentReport.class);

        ClientPlayerManager.getSingleton().initiateLogin("X", "X");
        ClientPlayer p = ClientPlayerManager.getSingleton().finishLogin(1);
        p.setPosition(new Position(5, 5));

        // set up the report for the verification later
        ChatSentReport expectedReport = new ChatSentReport(1, 0, "message", p.getPosition(), ChatType.Local);

        // send the chat message, which should send a ChatReceivedReport
        ClientChatManager.getSingleton().sendChatToServer("message", ChatType.Local, expectedReport.getReceiverID());

        // since the ClientChatManager sent a report, verify that the observer was notified about it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * @throws AlreadyBoundException
     * @throws NotBoundException
     */
    @Test
    public void sendChatToFriend() throws AlreadyBoundException, NotBoundException
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChatReceivedReport is sent
        connector.registerObserver(observer, ChatReceivedReport.class);

        ClientPlayerManager.getSingleton().initiateLogin("X", "X");
        ClientPlayer p = ClientPlayerManager.getSingleton().finishLogin(1);
        p.setPosition(new Position(5, 5));

        // set up the report for the verification later
        ChatReceivedReport expectedReport = new ChatReceivedReport(1, 2, "message", ChatType.Private);

        // send the chat message, which should send a ChatReceivedReport
        ClientChatManager.getSingleton().sendChatToUI(expectedReport.getSenderID(), expectedReport.getReceiverID(), "message", p.getPosition(), ChatType.Private);

        // since the ClientChatManager sent a report, verify that the observer was notified about it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

}
