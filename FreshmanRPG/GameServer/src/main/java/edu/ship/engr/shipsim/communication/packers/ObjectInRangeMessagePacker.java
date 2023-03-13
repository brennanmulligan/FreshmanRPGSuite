package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.InteractionDeniedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.reports.InteractionDeniedReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * @author ag0612
 * @author jk1964
 * Packs a message from a ObjectInRangeReport
 */
public class ObjectInRangeMessagePacker extends MessagePacker
{

    /**
     * Confirms that it is the proper report type, and then packs the message
     */
    @Override
    public Message pack(SendMessageReport object)
    {

        InteractionDeniedReport report = (InteractionDeniedReport) object;

        int playerID = report.getPlayerID();
        boolean isQuiet = report.isQuiet();
        if (this.getAccumulator().getPlayerID() == playerID)
        {
            InteractionDeniedMessage msg = new InteractionDeniedMessage(playerID, isQuiet);
            return msg;
        }
        return null;
    }

    /**
     * Returns the report types the packer will pack
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(InteractionDeniedReport.class);
        return result;
    }

}
