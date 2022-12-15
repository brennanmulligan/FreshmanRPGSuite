package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.QuestStateChangeMessage;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.QuestStateChangeReport;

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
     * @see MessagePacker#pack(QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        QuestStateChangeReport rpt = (QuestStateChangeReport) object;
        QuestStateChangeMessage msg = null;
        int playerID = rpt.getPlayerID();
        if (this.getAccumulator().getPlayerID() == playerID)
        {
            msg = new QuestStateChangeMessage(rpt.getPlayerID(), rpt.getQuestID(), rpt.getQuestTitle(),
                    rpt.getQuestDescription(), rpt.getNewState());
        }
        return msg;

    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
        result.add(QuestStateChangeReport.class);
        return result;
    }

}
