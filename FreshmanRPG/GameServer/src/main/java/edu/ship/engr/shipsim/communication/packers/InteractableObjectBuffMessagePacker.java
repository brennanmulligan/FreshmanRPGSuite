package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.BuffMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.reports.InteractableObjectBuffReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * Listens for Buff reports to send buff message
 *
 * @author Elisabeth Ostrow, Stephen Clabaugh
 */
public class InteractableObjectBuffMessagePacker extends MessagePacker
{

    /**
     * builds the message if this is the appropriate packer
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        InteractableObjectBuffReport report = (InteractableObjectBuffReport) object;

        int playerID = report.getPlayerID();
        int pointPool = report.getExpPointPool();
        boolean isQuiet =  report.isQuiet();

        if (this.getAccumulator().getPlayerID() == playerID)
        {
            BuffMessage msg = new BuffMessage(playerID, isQuiet, pointPool);
            return msg;
        }
        return null;
    }

    /**
     * The list of reports we listen for
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(InteractableObjectBuffReport.class);
        return result;
    }

}
