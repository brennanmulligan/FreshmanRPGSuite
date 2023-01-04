package edu.ship.engr.shipsim.view.screen.coordinates;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.ClientPlayerMovedReport;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;

/**
 * Builds the ShopUI to be displayed when the "Shop" button is activated
 *
 * @author Zachary Semanco, Kevin Marek
 */
public class CoordinatesUI extends OverlayingScreen implements ReportObserver
{
    private final float WIDTH = 125f;
    private final float HEIGHT = 75f;

    private CoordinatesTable coordinatesTable;

    /**
     * Constructor to set up the tables and button
     */
    public CoordinatesUI()
    {
        super();
        setUpListener();
        coordinatesTable = new CoordinatesTable(false);
        coordinatesTable.setFillParent(true);
        container.addActor(coordinatesTable);

        this.setX(0);
        this.setY(725);
        toggleVisibility();
    }

    /**
     * TODO: Fill comments when code is written
     */
    private void setUpListener()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        cm.registerObserver(this, ClientPlayerMovedReport.class);
    }

    /**
     * When the prize report is received, ask the ShopTable to add each item to the
     * UI
     *
     */
    @Override
    public void receiveReport(Report report)
    {
        ClientPlayerMovedReport movedReport = (ClientPlayerMovedReport) report;
        if (movedReport.isThisClientsPlayer())
        {
            coordinatesTable.updateCoordinates(movedReport.getNewPosition());
        }
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

