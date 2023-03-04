package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.QuestStateChangeMessage;
import edu.ship.engr.shipsim.model.reports.QuestStateChangeReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * If our player quest state has changed, we need to send a message so that the
 * client can tell the player of their success
 *
 * @author Merlin
 */
public class QuestStateChangeMessagePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        QuestStateChangeReport rpt = (QuestStateChangeReport) object;
        QuestStateChangeMessage msg = null;
        int playerID = rpt.getPlayerID();
        if (this.getAccumulator().getPlayerID() == playerID)
        {
            msg = new QuestStateChangeMessage(rpt.getPlayerID(), rpt.isQuiet(), rpt.getQuestID(), rpt.getQuestTitle(),
                    rpt.getQuestDescription(), rpt.getNewState());
        }
        return msg;

    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(QuestStateChangeReport.class);
        return result;
    }

}
