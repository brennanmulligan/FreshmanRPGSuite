package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.ReportObserverConnector;

/**
 * @author Derek
 */
public abstract class Controller
{
    private static final int MAX_WAIT_TIME = 2500;

    /**
     * @return the amount of time we should wait for a report
     */
    protected int getMaxWaitTime()
    {
        return MAX_WAIT_TIME;
    }

    @SafeVarargs
    public final <T extends Report> T processAction(Runnable initiationAction, Class<? extends Report>... reportClasses)
    {
        return ReportObserverConnector.processAction(initiationAction, getMaxWaitTime(), reportClasses);
    }
}
