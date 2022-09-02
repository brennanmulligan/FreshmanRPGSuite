package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Ian Keefer
 * @author TJ Renninger
 */
public class KeyInputRecievedReportTest extends ServerSideTest
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
