package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginFailedMessage;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.RestfulLoginFailedReport;

/**
 * @author Derek
 */
public class RestfulLoginFailedMessageHandler extends MessageHandler
{
    @Override
    public void process(Message msg)
    {
        RestfulLoginFailedMessage message = (RestfulLoginFailedMessage) msg;

        RestfulLoginFailedReport
                report = new RestfulLoginFailedReport(message.getMessage());

        ReportObserverConnector.getSingleton().sendReport(report);
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return RestfulLoginFailedMessage.class;
    }
}
