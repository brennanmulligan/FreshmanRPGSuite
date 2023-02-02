package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.LogoutReport;

/**
 * CommandLogout.java
 *
 * @author Zachary & Abdul
 */
public class CommandLogout extends Command
{

    /**
     *
     */
    @Override
    public void execute()
    {
        LogoutReport report = new LogoutReport();
        ReportObserverConnector.getSingleton().sendReport(report);
    }

}
