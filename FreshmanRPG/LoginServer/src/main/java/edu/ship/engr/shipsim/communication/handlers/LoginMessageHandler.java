package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.LoginFailedMessage;
import edu.ship.engr.shipsim.communication.messages.LoginMessage;
import edu.ship.engr.shipsim.communication.messages.LoginSuccessfulMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.LoginFailedException;
import edu.ship.engr.shipsim.model.LoginPlayerManager;
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
        try
        {
            LoginSuccessfulReport report = LoginPlayerManager.getSingleton().login(loginMsg.getPlayerName(),
                    loginMsg.getPassword());
            LoginSuccessfulMessage response = new LoginSuccessfulMessage(report.getPlayerID(), report.getHostname(),
                    report.getPort(), report.getPin());
            this.getStateAccumulator().queueMessage(response);
        }
        catch (LoginFailedException e)
        {
            LoginFailedMessage response = new LoginFailedMessage(e.getMessage());
            this.getStateAccumulator().queueMessage(response);
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
