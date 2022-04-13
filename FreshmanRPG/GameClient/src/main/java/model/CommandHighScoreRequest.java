package model;

import model.reports.HighScoreRequestReport;

/**
 * Command that sends the high score request report
 * @author Ryan
 *
 */
public class CommandHighScoreRequest extends Command
{

	@Override
	boolean execute()
	{
		HighScoreRequestReport r = new HighScoreRequestReport();
		QualifiedObservableConnector.getSingleton().sendReport(r);

		return true;
	}

}
