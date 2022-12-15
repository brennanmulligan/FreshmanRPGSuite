package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.InteractionDeniedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.InteractionDeniedReport;

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
    public Message pack(QualifiedObservableReport object)
    {

        InteractionDeniedReport report = (InteractionDeniedReport) object;

        int playerID = report.getPlayerID();
        if (this.getAccumulator().getPlayerID() == playerID)
        {
            InteractionDeniedMessage msg = new InteractionDeniedMessage(playerID);
            return msg;
        }
        return null;
    }

    /**
     * Returns the report types the packer will pack
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
        result.add(InteractionDeniedReport.class);
        return result;
    }

}
