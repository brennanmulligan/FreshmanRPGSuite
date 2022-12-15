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
    public boolean execute()
    {
        LogoutReport report = new LogoutReport();
        QualifiedObservableConnector.getSingleton().sendReport(report);
        return true;
    }

}
