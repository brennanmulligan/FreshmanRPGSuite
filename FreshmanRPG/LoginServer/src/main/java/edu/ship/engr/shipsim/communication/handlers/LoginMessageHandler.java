package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.LoginMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.LoginFailedException;
import edu.ship.engr.shipsim.model.LoginPlayerManager;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.LoginFailedReport;
import edu.ship.engr.shipsim.model.reports.LoginSuccessfulReport;

/**
 * Handles a report of a player moving
 *
 * @author merlin
 */
public class LoginMessageHandler extends MessageHandler
{

    /**
     * When a player tries to login, we should tell the model so that it can
     * check the credentials
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        LoginMessage loginMsg = (LoginMessage) msg;

        // TODO: https://gitlab.engr.ship.edu/merlin/freshmanrpgsuite/-/issues/20 - Rework Password Checking

        try
        {
            LoginSuccessfulReport report = LoginPlayerManager.getSingleton().login(loginMsg.getPlayerName(),
                    loginMsg.getPassword());

            ReportObserverConnector.getSingleton().sendReport(report);
        }
        catch (LoginFailedException e)
        {
            LoginFailedReport report = new LoginFailedReport(e.getMessage());

            ReportObserverConnector.getSingleton().sendReport(report);
        }
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return LoginMessage.class;
    }

}
