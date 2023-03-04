package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.QuestNotificationCompleteMessage;
import edu.ship.engr.shipsim.model.reports.QuestNotificationCompleteReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * A message that tells the server that the player has seen that a quest has changed state
 *
 * @author Merlin
 */
public class QuestNotificationCompletePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        QuestNotificationCompleteReport r = (QuestNotificationCompleteReport) object;
        return new QuestNotificationCompleteMessage(r.getPlayerID(), r.isQuiet(), r.getQuestID());
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result =
                new ArrayList<>();
        result.add(QuestNotificationCompleteReport.class);
        return result;
    }

}
