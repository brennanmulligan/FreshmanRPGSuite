package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.QuestManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Denny Fleagle, Chris Roadcap
 */
public class ReceiveTerminalTextReportTest extends ServerSideTest
{

    /**
     * reset the necessary singletons
     */
    @Before
    public void localSetUp()
    {
        QuestManager.resetSingleton();
    }

    /**
     * Test the creation of report
     */
    @Test
    public void createReport()
    {
        String resultText = "unknown";

        ReceiveTerminalTextReport report = new ReceiveTerminalTextReport(PlayersForTest.DAVE.getPlayerID(), resultText, "");

        assertEquals(PlayersForTest.DAVE.getPlayerID(), report.getPlayerID());
        assertEquals(resultText, report.getResultText());

    }

}
