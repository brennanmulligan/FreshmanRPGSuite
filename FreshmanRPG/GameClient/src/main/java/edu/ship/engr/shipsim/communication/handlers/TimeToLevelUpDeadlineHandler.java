package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TimeToLevelUpDeadlineMessage;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.ClientTimeToLevelUpDeadlineReport;

/**
 * @author Evan, Marty, Chris
 */
public class TimeToLevelUpDeadlineHandler extends MessageHandler
{

    /**
     * A player has joined our area server, so notify the PlayerManager of his
     * presence
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {

        if (msg.getClass().equals(TimeToLevelUpDeadlineMessage.class))
        {
            TimeToLevelUpDeadlineMessage realMsg = (TimeToLevelUpDeadlineMessage) msg;
            ClientTimeToLevelUpDeadlineReport report = new ClientTimeToLevelUpDeadlineReport(realMsg.getRelevantPlayerID(), realMsg.getTimeToDeadline(), realMsg.getNextLevel());
            ReportObserverConnector.getSingleton().sendReport(report);
        }

    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return TimeToLevelUpDeadlineMessage.class;
    }

}
