package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ObjectiveNotificationCompleteMessage;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.ObjectiveNotificationCompleteReport;

import java.util.ArrayList;

/**
 * @author Ryan
 */
public class ObjectiveNotificationCompletePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(Report)
     */
    @Override
    public Message pack(Report object)
    {
        ObjectiveNotificationCompleteReport r = (ObjectiveNotificationCompleteReport) object;
        return new ObjectiveNotificationCompleteMessage(r.getPlayerID(), r.getQuestID(), r.getObjectiveID());
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result =
                new ArrayList<>();
        result.add(ObjectiveNotificationCompleteReport.class);
        return result;
    }

}