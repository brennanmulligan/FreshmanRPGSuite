package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.HighScoreRequestReport;

/**
 * Command that sends the high score request report
 *
 * @author Ryan
 */
public class CommandHighScoreRequest extends Command
{

    @Override
    void execute()
    {
        HighScoreRequestReport r = new HighScoreRequestReport();
        ReportObserverConnector.getSingleton().sendReport(r);
    }

}
