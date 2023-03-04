package edu.ship.engr.shipsim.model.reports;

/**
 * Report that requests a high score
 *
 * @author Ryan
 */
public class HighScoreRequestReport extends SendMessageReport
{
    public HighScoreRequestReport()
    {
        // Happens on client, thus it will always be loud
        super(0, false);
    }
}
