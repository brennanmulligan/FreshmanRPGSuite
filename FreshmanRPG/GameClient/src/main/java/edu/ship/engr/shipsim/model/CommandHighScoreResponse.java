package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.model.reports.HighScoreResponseReport;

import java.util.ArrayList;

/**
 * @author nk3668
 */
public class CommandHighScoreResponse extends Command
{
    ArrayList<PlayerScoreRecord> scores;

    /**
     * Constructor for CommandHighScoreResponse
     *
     * @param scoreReports the high scores
     */
    public CommandHighScoreResponse(ArrayList<PlayerScoreRecord> scoreReports)
    {
        this.scores = scoreReports;
    }

    /**
     * Generate a HighScoreReponseReport and send it off
     */
    @Override
    void execute()
    {
        HighScoreResponseReport report = new HighScoreResponseReport(scores);
        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     * Return score reports
     *
     * @return score reports
     */
    public ArrayList<PlayerScoreRecord> getScoreRecord()
    {
        return scores;
    }

}
