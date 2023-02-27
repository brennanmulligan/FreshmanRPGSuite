package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginSuccessMessage;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
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

        RestfulLoginSuccessReport report = new RestfulLoginSuccessReport(message.getRelevantPlayerID());

        ReportObserverConnector.getSingleton().sendReport(report);
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return RestfulLoginSuccessMessage.class;
    }
}
