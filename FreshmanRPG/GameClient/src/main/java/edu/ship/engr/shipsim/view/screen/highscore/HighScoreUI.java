package edu.ship.engr.shipsim.view.screen.highscore;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.reports.ClientKeyInputSentReport;
import edu.ship.engr.shipsim.model.reports.HighScoreResponseReport;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;

import java.util.ArrayList;

/**
 * @author ck4124, Scott
 * @fixedby Ian Keefer and TJ Renninger
 */
public class HighScoreUI extends OverlayingScreen implements ReportObserver
{
    private final float WIDTH = 200f;
    private final float HEIGHT = 300f;
    private HighScoreTable highScoreTable;

    /**
     * sets up UI for top 10 experience points
     */
    public HighScoreUI()
    {
        super();
        setUpListening();
        highScoreTable = new HighScoreTable(true);
        highScoreTable.setFillParent(true);
        container.addActor(highScoreTable);
    }

    /**
     * Sets up the ReportObserver for HighScoreResponseReport
     */
    public void setUpListening()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        cm.registerObserver(this, HighScoreResponseReport.class);
        cm.registerObserver(this, ClientKeyInputSentReport.class);
    }

    /**
     * Toggle the invisibility of the highscore list
     */
    @Override
    public void toggleVisibility()
    {
        if (isVisible())
        {
            this.addAction(Actions.hide());
        }
        else
        {
            CommandHighScoreRequest cmd = new CommandHighScoreRequest();
            ClientModelFacade.getSingleton().queueCommand(cmd);
            this.addAction(Actions.show());
        }
    }

    /**
     * @see ReportObserver#receiveReport(Report)
     */
    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass().equals(HighScoreResponseReport.class))
        {
            HighScoreResponseReport rep = (HighScoreResponseReport) report;
            ArrayList<PlayerScoreRecord> list = rep.getScoreList();
            highScoreTable.updateHighScores(list);
        }
        else if (report.getClass().equals(ClientKeyInputSentReport.class))
        {
            ClientKeyInputSentReport r = (ClientKeyInputSentReport) report;
            // Check for H
            if (r.getInput().equals(Keys.toString(Keys.H)))
            {
                this.toggleVisibility();
            }
        }
    }

    /**
     * @see OverlayingScreen#getWidth()
     */
    @Override
    public float getMyWidth()
    {
        return WIDTH;
    }

    /**
     * @see OverlayingScreen#getHeight()
     */
    @Override
    public float getMyHeight()
    {
        return HEIGHT;
    }
}
