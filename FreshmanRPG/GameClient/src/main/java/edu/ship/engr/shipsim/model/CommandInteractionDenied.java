package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.InteractionDeniedReport;

/**
 * Command for triggering the InteractionDeniedReport
 */
public class CommandInteractionDenied extends Command
{

    @Override
    boolean execute()
    {
        InteractionDeniedReport report = new InteractionDeniedReport();
        ReportObserverConnector.getSingleton().sendReport(report);
        return true;
    }

}
