package view.screen.login;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.LoginFailedReport;
import model.reports.LoginInitiatedReport;
import model.reports.LogoutReport;
import model.reports.PinFailedReport;
import view.screen.ScreenListener;
import view.screen.Screens;

/**
 * Every screen has one of these and it is responsible for listening for the
 * reports that are associated with this screen and updating the state as
 * necessary
 *
 * @author Merlin
 *
 */
public class ScreenLoginListener extends ScreenListener
{

	/**
	 *
	 */
	public ScreenLoginListener()
	{
		super.setUpListening();
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport arg)
	{
		final ScreenListener sl = this;
		if (arg.getClass().equals(LoginInitiatedReport.class))
		{
			sl.switchToScreen(Screens.MAP_SCREEN);
		}

		if (arg.getClass().equals(LoginFailedReport.class))
		{
			ScreenLogin screen = (ScreenLogin) Screens.LOGIN_SCREEN.getScreen();
			LoginFailedReport report = (LoginFailedReport) arg;
			screen.showFlagMessage(report.toString());
			this.switchToScreen(Screens.LOGIN_SCREEN);
		}

		if (arg.getClass().equals(PinFailedReport.class))
		{
			ScreenLogin screen = (ScreenLogin) Screens.LOGIN_SCREEN.getScreen();
			PinFailedReport report = (PinFailedReport) arg;
			screen.showFlagMessage(report.toString());
			this.switchToScreen(Screens.LOGIN_SCREEN);
		}

		if (arg.getClass().equals(LogoutReport.class))
		{

			this.switchToScreen(Screens.LOGIN_SCREEN);
			Screens.MAP_SCREEN.getScreen().dispose();
		}

	}

	/**
	 * @see view.screen.ScreenListener#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<>();
		reportTypes.add(LoginInitiatedReport.class);
		reportTypes.add(LoginFailedReport.class);
		reportTypes.add(PinFailedReport.class);
		reportTypes.add(LogoutReport.class);
		return reportTypes;
	}

}
