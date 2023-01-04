package edu.ship.engr.shipsim.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contains the logic for what an NPC should do
 *
 * @author Steve
 */
public abstract class NPCBehavior implements Serializable, ReportObserver
{
    private static final long serialVersionUID = -1535370359851281459L;

    /**
     * Defaults to 1s
     */
    protected int pollingInterval = 1000;

    protected int playerID;

    protected String filePath;

    NPCBehavior(int playerID)
    {
        this.playerID = playerID;
    }


    /**
     * @return how often this behavior wants to be performed
     */
    protected int getPollingInterval()
    {
        return this.pollingInterval;
    }

    /**
     * EVERY subclass should call this method in its constructor!!!!!!
     */
    protected void setUpListening()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        ArrayList<Class<? extends Report>> reportTypes = getReportTypes();
        for (Class<? extends Report> reportType : reportTypes)
        {
            cm.registerObserver(this, reportType);
        }
    }


    /**
     * Execute the timed event
     */
    protected abstract void doTimedEvent();

    /**
     * @return the report types this listener should pay attention to
     */
    protected abstract ArrayList<Class<? extends Report>> getReportTypes();


}
