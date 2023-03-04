package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginMessage;
import edu.ship.engr.shipsim.model.LoginFailedException;
import edu.ship.engr.shipsim.model.LoginPlayerManager;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.LoginSuccessfulReport;
import edu.ship.engr.shipsim.model.reports.RestfulLoginServerFailedReport;
import edu.ship.engr.shipsim.model.reports.RestfulLoginServerSuccessfulReport;

/**
 * @author Derek
 */
public class RestfulLoginMessageHandler extends MessageHandler
{
    @Override
    public void process(Message msg)
    {
        RestfulLoginMessage loginMsg = (RestfulLoginMessage) msg;

        // TODO: https://gitlab.engr.ship.edu/merlin/freshmanrpgsuite/-/issues/20 - Rework Password Checking

        try
        {
            LoginSuccessfulReport response = LoginPlayerManager.getSingleton().login(loginMsg.getUsername(),
                    loginMsg.getPassword());

            RestfulLoginServerSuccessfulReport
                    report = new RestfulLoginServerSuccessfulReport(response.getPlayerID());

            ReportObserverConnector.getSingleton().sendReport(report);
        }
        catch (LoginFailedException e)
        {
            RestfulLoginServerFailedReport
                    report = new RestfulLoginServerFailedReport(e.getMessage());

            ReportObserverConnector.getSingleton().sendReport(report);
        }
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return RestfulLoginMessage.class;
    }
}
