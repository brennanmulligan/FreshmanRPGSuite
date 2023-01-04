package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.model.reports.HighScoreResponseReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author nk3668
 */
@GameTest("GameClient")
@ResetReportObserverConnector
public class CommandHighScoreResponseTest
{

    /**
     * Tests to make sure constructor sets values
     */
    @Test
    public void testConstructor()
    {
        ArrayList<PlayerScoreRecord> list = new ArrayList<>();
        list.add(new PlayerScoreRecord("Ethan", 0));
        list.add(new PlayerScoreRecord("Weaver", 3));
        list.add(new PlayerScoreRecord("Merlin", 9001));
        list.add(new PlayerScoreRecord("Nate", 984257));

        CommandHighScoreResponse res = new CommandHighScoreResponse(list);
        assertEquals(list, res.getScoreRecord());
    }

    /**
     * Tests to make sure execute correctly
     * sends high scores.
     */
    @Test
    public void testExecute()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a HighScoreResponseReport is sent
        connector.registerObserver(observer, HighScoreResponseReport.class);

        ArrayList<PlayerScoreRecord> list = new ArrayList<>();
        list.add(new PlayerScoreRecord("Ethan", 0));
        list.add(new PlayerScoreRecord("Weaver", 3));
        list.add(new PlayerScoreRecord("John", 25));
        list.add(new PlayerScoreRecord("Merlin", 9001));
        list.add(new PlayerScoreRecord("Nate", 984257));

        // set up command for later
        CommandHighScoreResponse res = new CommandHighScoreResponse(list);

        // set up report for verification
        HighScoreResponseReport expectedReport = new HighScoreResponseReport(list);

        // execute the command
        res.execute();

        // since CommandHighScoreResponse sends a report, verify that the observer is notified for it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

}
