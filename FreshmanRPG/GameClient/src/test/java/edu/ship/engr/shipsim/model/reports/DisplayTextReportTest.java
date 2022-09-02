package edu.ship.engr.shipsim.model.reports;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author sk5587
 */
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
