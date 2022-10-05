package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ian Keefer
 * @author TJ Renninger
 */
@GameTest("GameServer")
public class KeyInputRecievedReportTest
{

    /**
     * Test to see that a key input report is created.
     */
    @Test
    public void testInitialization()
    {
        String input = "q";
        int id = 1;
        KeyInputRecievedReport report = new KeyInputRecievedReport(input, id);
        assertEquals(input, report.getInput());
    }

}
