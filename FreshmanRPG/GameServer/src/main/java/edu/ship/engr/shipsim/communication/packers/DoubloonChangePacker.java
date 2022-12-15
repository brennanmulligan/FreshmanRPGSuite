package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.DoubloonsChangedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.DoubloonChangeReport;

import java.util.ArrayList;

/**
 * @author Matthew Croft
 */
public class DoubloonChangePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {

        if (object.getClass() != DoubloonChangeReport.class)
        {
            throw new IllegalArgumentException(
                    "DoubloonChangedMessagePacker cannot pack messages of type " + object.getClass());
        }

        DoubloonChangeReport report = (DoubloonChangeReport) object;

        if (this.getAccumulator().getPlayerID() == report.getPlayerID())
        {
            DoubloonsChangedMessage msg = new DoubloonsChangedMessage(report.getPlayerID(),
                    report.getDoubloons(), report.getBuffPool());
            return msg;
        }
        return null;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
        result.add(DoubloonChangeReport.class);
        return result;
    }

}
