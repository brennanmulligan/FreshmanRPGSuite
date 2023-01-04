package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.BuffMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.InteractableObjectBuffReport;

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
    public Message pack(Report object)
    {
        InteractableObjectBuffReport report = (InteractableObjectBuffReport) object;

        int playerID = report.getPlayerID();
        int pointPool = report.getExpPointPool();

        if (this.getAccumulator().getPlayerID() == playerID)
        {
            BuffMessage msg = new BuffMessage(playerID, pointPool);
            return msg;
        }
        return null;
    }

    /**
     * The list of reports we listen for
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result = new ArrayList<>();
        result.add(InteractableObjectBuffReport.class);
        return result;
    }

}
