package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

/**
 * @author Merlin
 */
@GameTest("GameShared")
@ResetReportObserverConnector
public class ReportObserverConnectorTest
{
    /**
     * Test the singleton functionality. First, make sure you get the same
     * object and then reset the singleton and make sure you get a different one
     */
    @Test
    public void isSingleton()
    {
        ReportObserverConnector connector = ReportObserverConnector.getSingleton();
        assertNotNull(connector);
        ReportObserverConnector connector2 = ReportObserverConnector.getSingleton();
        assertNotNull(connector2);
        assertSame(connector, connector2);
        ReportObserverConnector.resetSingleton();
        connector2 = ReportObserverConnector.getSingleton();
        assertNotSame(connector, connector2);
    }

    /**
     * If we register the same observer for the same report type, we should
     * ignore it
     */
    @Test
    public void addingSameObserverTwiceIgnoresSecondCall()
    {
        // mock the connector and observer
        ReportObserverConnector connector = Mockito.spy(ReportObserverConnector.getSingleton());
        ReportObserver mockObserver = Mockito.mock(ReportObserver.class);

        // register the observer twice, only the first should be effective
        connector.registerObserver(mockObserver, TestReport.class);
        connector.registerObserver(mockObserver, TestReport.class);

        // verify that the registerObserver method was called twice
        Mockito.verify(connector, times(2)).registerObserver(mockObserver, TestReport.class);

        // now cause the notification
        TestReport reportToSend = new TestReport();
        connector.sendReport(reportToSend);

        // verify that the observer only received the report once
        Mockito.verify(mockObserver, times(1)).receiveReport(reportToSend);
    }

    /**
     * Make sure that if we unregister an observer, it no longer gets updated on
     * notification
     */
    @Test
    public void canUnRegisterAnObserver()
    {
        // set up the connection
        ReportObserverConnector connector = Mockito.spy(ReportObserverConnector.getSingleton());
        ReportObserver mockObserver = Mockito.mock(ReportObserver.class);
        connector.registerObserver(mockObserver, TestReport.class);

        // verify that registerObserver was called once
        Mockito.verify(connector, times(1)).registerObserver(mockObserver, TestReport.class);


        // unregister observer. reports should no longer be received by observer
        connector.unregisterObserver(mockObserver, TestReport.class);

        // verify that unregisterObserver was called once
        Mockito.verify(connector, times(1)).unregisterObserver(mockObserver, TestReport.class);


        TestReport reportToSend = new TestReport();
        connector.sendReport(reportToSend);

        // Verify the report was NOT received by the observer
        Mockito.verify(mockObserver, never()).receiveReport(reportToSend);
    }

    /**
     * Test to get the total number of reports being listened too that are currently registered
     */
    @Test
    public void testObserverGetsRecorded()
    {
        // set up the connection
        ReportObserverConnector connector = ReportObserverConnector.getSingleton();
        ReportObserver mockObserver = Mockito.mock(ReportObserver.class);
        connector.registerObserver(mockObserver, TestReport.class);

        assertEquals(1, connector.getCount());
    }

    /**
     * If we unregister an observer, adding subsequent observables for that
     * report type should NOT connect them
     */
    @Test
    public void unregistrationForgetsObserver()
    {
        // set up the connection
        ReportObserverConnector connector = Mockito.spy(ReportObserverConnector.getSingleton());
        ReportObserver mockObserver = Mockito.mock(ReportObserver.class);

        // register and unregister the observer with the connector
        connector.registerObserver(mockObserver, TestReport.class);
        connector.unregisterObserver(mockObserver, TestReport.class);

        // verify the above methods were called once each
        Mockito.verify(connector, times(1)).registerObserver(mockObserver, TestReport.class);
        Mockito.verify(connector, times(1)).unregisterObserver(mockObserver, TestReport.class);

        // send the report
        TestReport reportToSend = new TestReport();
        connector.sendReport(reportToSend);

        // verify that the report was never received by the observer
        Mockito.verify(mockObserver, never()).receiveReport(reportToSend);
    }

    /**
     * We just want to be sure that, if you ask to unregister for something you
     * aren't connected to, we just ignore you.
     */
    @Test
    public void observerUnregistrationWhenNotRegistered()
    {
        ReportObserverConnector connector = ReportObserverConnector.getSingleton();
        ReportObserver mockObserver = Mockito.mock(ReportObserver.class);
        connector.unregisterObserver(mockObserver, TestReport.class);
    }

    @Test
    public void testWaitForReport() throws InterruptedException
    {
        TestReport report = new TestReport();

        TestReport response = ReportObserverConnector.processAction(() ->
        {
            ReportObserverConnector.getSingleton().sendReport(report);
        }, 1000, TestReport.class);

        assertEquals(report, response);
    }

    @Test
    public void testWaitForReportSleep() throws InterruptedException
    {
        TestReport report = new TestReport();

        TestReport response = ReportObserverConnector.processAction(() ->
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            ReportObserverConnector.getSingleton().sendReport(report);
        }, 1000, TestReport.class);

        assertEquals(report, response);
    }

    private static class TestReport implements Report
    {

    }
}
