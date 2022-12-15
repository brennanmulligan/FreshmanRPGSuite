package edu.ship.engr.shipsim.view.screen.terminal;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import edu.ship.engr.shipsim.model.QualifiedObservableConnector;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.QualifiedObserver;
import edu.ship.engr.shipsim.model.reports.TerminalResponseReport;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;

/**
 * @author as3871
 */
public class TerminalUI extends OverlayingScreen implements QualifiedObserver
{

    private final float WIDTH = 600f;
    private final float HEIGHT = 475f;

    private TerminalTable terminalTable;

    /**
     * Constructor to set up the listening and tables
     */
    public TerminalUI()
    {
        super();
        setUpListening();
        terminalTable = new TerminalTable(false);
        terminalTable.setFillParent(true);
        container.addActor(terminalTable);
    }

    /**
     * Handle the report that we need
     */
    @Override
    public void receiveReport(QualifiedObservableReport report)
    {
        if (report.getClass() == TerminalResponseReport.class)
        {
            TerminalResponseReport rep = (TerminalResponseReport) report;
            terminalTable.addTerminalResponse(rep.getTerminalResult());

        }
    }

    /**
     * Sets up the QualifiedObserver for HighScoreResponseReport
     */
    public void setUpListening()
    {
        QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
        cm.registerObserver(this, TerminalResponseReport.class);
    }

    /**
     * @see OverlayingScreen#getMyWidth()
     */
    @Override
    public float getMyWidth()
    {
        return WIDTH;
    }

    /**
     * @see OverlayingScreen#getMyHeight()
     */
    @Override
    public float getMyHeight()
    {
        return HEIGHT;
    }

    /**
     * @see OverlayingScreen#toggleVisibility()
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
            this.addAction(Actions.show());
        }

    }

}
