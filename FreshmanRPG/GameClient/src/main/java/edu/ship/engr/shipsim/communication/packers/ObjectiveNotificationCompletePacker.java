package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ObjectiveNotificationCompleteMessage;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.ObjectiveNotificationCompleteReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * @author Ryan
 */
public class ObjectiveNotificationCompletePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        ObjectiveNotificationCompleteReport r = (ObjectiveNotificationCompleteReport) object;
        return new ObjectiveNotificationCompleteMessage(r.getPlayerID(), r.isQuiet(), r.getQuestID(), r.getObjectiveID());
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result =
                new ArrayList<>();
        result.add(ObjectiveNotificationCompleteReport.class);
        return result;
    }

}
