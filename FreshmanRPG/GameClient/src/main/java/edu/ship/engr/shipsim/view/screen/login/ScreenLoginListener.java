package edu.ship.engr.shipsim.view.screen.login;

import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.reports.LoginFailedReport;
import edu.ship.engr.shipsim.model.reports.LoginInitiatedReport;
import edu.ship.engr.shipsim.model.reports.LogoutReport;
import edu.ship.engr.shipsim.model.reports.PinFailedReport;
import edu.ship.engr.shipsim.view.screen.ScreenListener;
import edu.ship.engr.shipsim.view.screen.Screens;

import java.util.ArrayList;

/**
 * Every screen has one of these and it is responsible for listening for the
 * reports that are associated with this screen and updating the state as
 * necessary
 *
 * @author Merlin
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
     * @see ReportObserver#receiveReport(Report)
     */
    @Override
    public void receiveReport(Report arg)
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
     * @see ScreenListener#getReportTypes()
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypes()
    {
        ArrayList<Class<? extends Report>> reportTypes = new ArrayList<>();
        reportTypes.add(LoginInitiatedReport.class);
        reportTypes.add(LoginFailedReport.class);
        reportTypes.add(PinFailedReport.class);
        reportTypes.add(LogoutReport.class);
        return reportTypes;
    }

}
