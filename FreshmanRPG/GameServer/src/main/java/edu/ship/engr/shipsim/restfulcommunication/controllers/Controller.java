package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.model.QualifiedObservableConnector;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;

/**
 * @author Derek
 */
public abstract class Controller
{
    private static final int MAX_WAIT_TIME = 1000;

    /**
     * @return the amount of time we should wait for a report
     */
    protected int getMaxWaitTime()
    {
        return MAX_WAIT_TIME;
    }

    @SafeVarargs
    public final <T extends QualifiedObservableReport> T processAction(Runnable initiationAction, Class<? extends QualifiedObservableReport>... reportClasses)
    {
        return QualifiedObservableConnector.processAction(initiationAction, getMaxWaitTime(), reportClasses);
    }
}
