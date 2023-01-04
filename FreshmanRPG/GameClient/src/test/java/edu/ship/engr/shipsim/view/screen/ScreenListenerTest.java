package edu.ship.engr.shipsim.view.screen;

import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.StubReport1;
import edu.ship.engr.shipsim.model.reports.StubReport2;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the non-abstract stuff in ScreenListener
 *
 * @author Merlin
 */
@GameTest("GameClient")
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
        ReportObserverConnector connector = ReportObserverConnector
                .getSingleton();
        ArrayList<Class<? extends Report>> reportTypes = underTest
                .getReportTypes();
        for (Class<? extends Report> reportType : reportTypes)
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
        public ArrayList<Class<? extends Report>> getReportTypes()
        {
            ArrayList<Class<? extends Report>> reportTypes = new ArrayList<>();
            reportTypes.add(StubReport1.class);
            reportTypes.add(StubReport2.class);

            return reportTypes;
        }

        @Override
        public void receiveReport(Report report)
        {
            // don't need anything here
        }

    }

}
