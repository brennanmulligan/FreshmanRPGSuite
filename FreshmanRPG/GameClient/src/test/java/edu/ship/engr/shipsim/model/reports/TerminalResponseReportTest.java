package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Nate and Ben
 */
@GameTest("GameClient")
public class TerminalResponseReportTest
{

    /**
     * test creation of the TerminalResponseReport
     */
    @Test
    public void testCreation()
    {
        int playerID = 1;
        String terminalText = "text";

        TerminalResponseReport report = new TerminalResponseReport(playerID, terminalText);
        assertEquals(playerID, report.getPlayerID());
        assertEquals(terminalText, report.getTerminalResult());
    }

}
