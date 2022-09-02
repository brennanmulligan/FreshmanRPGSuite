package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ObjectiveStateChangeMessage;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.ObjectiveStateChangeReport;

import java.util.ArrayList;

/**
 * @author Ryan
 */
public class ObjectiveStateChangeMessagePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        ObjectiveStateChangeReport rpt = (ObjectiveStateChangeReport) object;
        ObjectiveStateChangeMessage msg = null;
        int playerID = rpt.getPlayerID();
        if (this.getAccumulator().getPlayerID() == playerID)
        {
            msg = new ObjectiveStateChangeMessage(rpt.getPlayerID(), rpt.getQuestID(), rpt.getObjectiveID(),
                    rpt.getObjectiveDescription(), rpt.getNewState(), rpt.isRealLifeObjective(), rpt.getWitnessTitle());
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
        result.add(ObjectiveStateChangeReport.class);
        return result;
    }

}
