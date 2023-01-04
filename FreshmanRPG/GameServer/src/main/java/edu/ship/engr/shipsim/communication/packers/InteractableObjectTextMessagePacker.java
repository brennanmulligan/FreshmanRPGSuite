package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.DisplayTextMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.InteractableObjectTextReport;

import java.util.ArrayList;

/**
 * @author Stephen Clabaugh, Jacob Knight
 * Packs a message from InteractableObjectTextReport
 */
public class InteractableObjectTextMessagePacker extends MessagePacker
{

    /**
     * Checks that the report is of the correct type, and then packs it into a message
     */
    @Override
    public Message pack(Report object)
    {
        InteractableObjectTextReport report = (InteractableObjectTextReport) object;

        int playerID = report.getPlayerID();
        String text = report.getText();

        if (this.getAccumulator().getPlayerID() == playerID)
        {
            DisplayTextMessage msg = new DisplayTextMessage(playerID, text);
            return msg;
        }
        return null;
    }

    /**
     * Returns the types of reports the packer will pack
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result = new ArrayList<>();
        result.add(InteractableObjectTextReport.class);
        return result;
    }

}