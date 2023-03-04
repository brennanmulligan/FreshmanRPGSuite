package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ObjectiveStateChangeMessage;
import edu.ship.engr.shipsim.model.reports.ObjectiveStateChangeReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * @author Ryan
 */
public class ObjectiveStateChangeMessagePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        ObjectiveStateChangeReport report = (ObjectiveStateChangeReport) object;
        ObjectiveStateChangeMessage msg = null;
        int playerID = report.getPlayerID();
        if (this.getAccumulator().getPlayerID() == playerID)
        {
            msg = new ObjectiveStateChangeMessage(report.getPlayerID(), report.isQuiet(), report.getQuestID(), report.getObjectiveID(),
                    report.getObjectiveDescription(), report.getNewState(), report.isRealLifeObjective(), report.getWitnessTitle());
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
        result.add(ObjectiveStateChangeReport.class);
        return result;
    }

}
