package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginMessage;
import edu.ship.engr.shipsim.model.LoginFailedException;
import edu.ship.engr.shipsim.model.LoginPlayerManager;
import edu.ship.engr.shipsim.model.QualifiedObservableConnector;
import edu.ship.engr.shipsim.model.reports.LoginSuccessfulReport;
import edu.ship.engr.shipsim.model.reports.RestfulLoginFailedReport;
import edu.ship.engr.shipsim.model.reports.RestfulLoginSuccessfulReport;

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

            RestfulLoginSuccessfulReport report = new RestfulLoginSuccessfulReport(response.getPlayerID());

            QualifiedObservableConnector.getSingleton().sendReport(report);
        }
        catch (LoginFailedException e)
        {
            RestfulLoginFailedReport report = new RestfulLoginFailedReport(e.getMessage());

            QualifiedObservableConnector.getSingleton().sendReport(report);
        }
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return RestfulLoginMessage.class;
    }
}
