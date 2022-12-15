package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.AreaCollisionMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.AreaCollisionReport;

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
    public Message pack(QualifiedObservableReport object)
    {
        if (object.getClass() != AreaCollisionReport.class)
        {
            throw new IllegalArgumentException(
                    "AreaCollisionPacker cannot pack messages of type "
                            + object.getClass());
        }
        AreaCollisionReport report = (AreaCollisionReport) object;
        Message msg = new AreaCollisionMessage(report.getPlayerID(), report.getAreaName());
        return msg;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
        result.add(AreaCollisionReport.class);
        return result;
    }

}
