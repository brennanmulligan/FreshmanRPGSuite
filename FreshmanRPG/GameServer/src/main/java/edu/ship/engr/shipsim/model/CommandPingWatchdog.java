package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.PingWatchdogReport;

public class CommandPingWatchdog extends Command
{
    @Override
    void execute()
    {
        String hostName = OptionsManager.getSingleton().getHostName();

        PingWatchdogReport report = new PingWatchdogReport(hostName);

        ReportObserverConnector.getSingleton().sendReport(report);
    }
}
