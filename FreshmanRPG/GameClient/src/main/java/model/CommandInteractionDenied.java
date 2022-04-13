package model;

import model.reports.InteractionDeniedReport;

/**
 * Command for triggering the InteractionDeniedReport
 */
public class CommandInteractionDenied extends Command
{

	@Override
	boolean execute()
	{
		InteractionDeniedReport report = new InteractionDeniedReport();
		QualifiedObservableConnector.getSingleton().sendReport(report);
		return true;
	}

}
