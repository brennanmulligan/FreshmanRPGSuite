package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PinFailedMessage;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.PinFailedReport;

import java.util.ArrayList;

/**
 * Takes the information given to us and translates it to the appropriate
 * PinFailedMessage.
 *
 * @author Matt and Andy
 */
public class PinFailedMessagePacker extends MessagePacker
{

    /**
     * Generates a PinFailedMessage for a PinFailedReport.
     *
     * @see MessagePacker#pack(QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        PinFailedReport report = (PinFailedReport) object;
        if (object.getClass().equals(PinFailedReport.class))
        {
            int playerID = report.getPlayerID();
            if (this.getAccumulator().getPlayerID() == playerID)
            {
                PinFailedMessage msg = new PinFailedMessage(playerID);
                return msg;
            }

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
        result.add(PinFailedReport.class);
        return result;
    }

}
