package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.AreaCollisionMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.reports.AreaCollisionReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * @author nhydock
 */
public class AreaCollisionMessagePacker extends MessagePacker
{
    /**
     *
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass() != AreaCollisionReport.class)
        {
            throw new IllegalArgumentException(
                    "AreaCollisionPacker cannot pack messages of type "
                            + object.getClass());
        }
        AreaCollisionReport report = (AreaCollisionReport) object;
        Message msg = new AreaCollisionMessage(report.getPlayerID(), report.isQuiet(), report.getAreaName());
        return msg;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(AreaCollisionReport.class);
        return result;
    }

}
