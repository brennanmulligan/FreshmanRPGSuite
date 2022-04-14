package view.screen.coordinates;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.ClientPlayerMovedReport;
import view.screen.OverlayingScreen;

/**
 * Builds the ShopUI to be displayed when the "Shop" button is activated
 *
 * @author Zachary Semanco, Kevin Marek
 */
public class CoordinatesUI extends OverlayingScreen implements QualifiedObserver
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
        QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
        cm.registerObserver(this, ClientPlayerMovedReport.class);
    }

    /**
     * When the prize report is received, ask the ShopTable to add each item to the
     * UI
     *
     * @See view.screen.shop.ShopTable#addShopItem(String, String, int)
     */
    @Override
    public void receiveReport(QualifiedObservableReport report)
    {
        ClientPlayerMovedReport movedReport = (ClientPlayerMovedReport) report;
        if(movedReport.isThisClientsPlayer())
        {
            coordinatesTable.updateCoordinates(movedReport.getNewPosition());
        }
    }

    /**
     * @see view.screen.OverlayingScreen#getMyWidth()
     */
    @Override
    public float getMyWidth()
    {
        return WIDTH;
    }

    /**
     * @see view.screen.OverlayingScreen#getMyHeight()
     */
    @Override
    public float getMyHeight()
    {
        return HEIGHT;

    }

    /**
     * @see view.screen.OverlayingScreen#toggleVisibility()
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

