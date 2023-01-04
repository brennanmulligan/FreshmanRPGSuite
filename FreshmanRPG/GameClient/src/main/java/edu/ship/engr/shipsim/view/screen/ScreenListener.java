package edu.ship.engr.shipsim.view.screen;

import com.badlogic.gdx.Game;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.ReportObserver;

import java.util.ArrayList;

/**
 * There is one of these for each screen and it is responsible for listening for
 * reports from the model and handling them
 *
 * @author Merlin
 */
public abstract class ScreenListener implements ReportObserver
{

    protected ScreenBasic screen;
    private Game gameToUse;

    /**
     * @return the report types this listener should pay attention to
     */
    public abstract ArrayList<Class<? extends Report>> getReportTypes();

    /**
     * Tell this listener which screen it is managing
     *
     * @param screen the screen
     */
    public void setAssociatedScreen(ScreenBasic screen)
    {
        this.screen = screen;
    }

    /**
     * Tell this listener what game it is attached to so that it can change
     * screens if necessary
     *
     * @param gameToUse the game we are using
     */
    public void setGame(Game gameToUse)
    {
        this.gameToUse = gameToUse;
    }

    /**
     * EVERY subclass should call this method in its constructor!!!!!!
     */
    public void setUpListening()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        ArrayList<Class<? extends Report>> reportTypes = getReportTypes();
        for (Class<? extends Report> reportType : reportTypes)
        {
            cm.registerObserver(this, reportType);
        }
    }

    /**
     * Switch to another screen
     *
     * @param screen the screen we should switch to
     */
    public void switchToScreen(Screens screen)
    {
        gameToUse.setScreen(screen.getScreen());
    }
}
