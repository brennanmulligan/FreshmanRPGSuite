package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Denny Fleagle, Chris Roadcap
 */
@GameTest("GameServer")
@ResetQuestManager
public class ReceiveTerminalTextReportTest
{
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
