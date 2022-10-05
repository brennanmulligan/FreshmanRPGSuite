package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests user key input report
 *
 * @author Ian Keefer & TJ Renninger
 */
@GameTest("GameClient")
public class KeyInputSentReportTest
{

    /**
     * Tests creation of a KeyInputSentReport
     */
    @Test
    public void testInitialization()
    {
        String input = "q";
        ClientKeyInputSentReport report = new ClientKeyInputSentReport(input);
        assertEquals(input, report.getInput());
    }

}
