package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginSuccessMessage;
import edu.ship.engr.shipsim.model.QualifiedObservableConnector;
import edu.ship.engr.shipsim.model.reports.RestfulLoginSuccessReport;

/**
 * @author Derek
 */
public class RestfulLoginSuccessMessageHandler extends MessageHandler
{
    @Override
    public void process(Message msg)
    {
        RestfulLoginSuccessMessage message = (RestfulLoginSuccessMessage) msg;

        RestfulLoginSuccessReport report = new RestfulLoginSuccessReport(message.getPlayerID());

        QualifiedObservableConnector.getSingleton().sendReport(report);
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return RestfulLoginSuccessMessage.class;
    }
}
