package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sk5587
 */
@GameTest("GameClient")
public class DisplayTextReportTest
{

    /**
     * Make sure report hold right information.
     */
    @Test
    public void create()
    {

        DisplayTextReport report = new DisplayTextReport("text");
        assertEquals("text", report.getText());
    }

}
