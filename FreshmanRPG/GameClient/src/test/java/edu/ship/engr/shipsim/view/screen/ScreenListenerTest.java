package edu.ship.engr.shipsim.view.screen;

import edu.ship.engr.shipsim.model.QualifiedObservableConnector;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.StubQualifiedObservableReport1;
import edu.ship.engr.shipsim.model.reports.StubQualifiedObservableReport2;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Test the non-abstract stuff in ScreenListener
 *
 * @author Merlin
 */
public class ScreenListenerTest
{
    /**
     * Make sure that the subclass correctly hooks itself into the Qualified
     * Observerable apparatus
     */
    @Test
    public void addsItselfCorrectly()
    {
        ScreenListener underTest = new MockScreenListener();
        QualifiedObservableConnector connector = QualifiedObservableConnector
                .getSingleton();
        ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = underTest
                .getReportTypes();
        for (Class<? extends QualifiedObservableReport> reportType : reportTypes)
        {
            assertTrue(connector.doIObserve(underTest, reportType));
        }

    }

    private class MockScreenListener extends ScreenListener
    {
        public MockScreenListener()
        {
            super.setUpListening();
        }

        @Override
        public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
        {
            ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<>();
            reportTypes.add(StubQualifiedObservableReport1.class);
            reportTypes.add(StubQualifiedObservableReport2.class);

            return reportTypes;
        }

        @Override
        public void receiveReport(QualifiedObservableReport report)
        {
            // don't need anything here
        }

    }

}
