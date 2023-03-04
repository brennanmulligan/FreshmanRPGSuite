package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PinFailedMessage;
import edu.ship.engr.shipsim.model.reports.PinFailedReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

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
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        PinFailedReport report = (PinFailedReport) object;
        if (object.getClass().equals(PinFailedReport.class))
        {
            int playerID = report.getPlayerID();
            if (this.getAccumulator().getPlayerID() == playerID)
            {
                PinFailedMessage msg = new PinFailedMessage(playerID, report.isQuiet());
                return msg;
            }

        }
        return null;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(PinFailedReport.class);
        return result;
    }

}
