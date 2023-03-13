package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.updateFriendListMessage;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;
import edu.ship.engr.shipsim.model.reports.updateFriendListReport;

import java.util.ArrayList;

/**
 * @author Christian C, Andrew M
 */
public class updateFriendListPacker extends MessagePacker
{

    /**
     * Packs the report into a message
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass() != updateFriendListReport.class)
        {
            throw new IllegalArgumentException(
                    "updateFriendListPacker cannot pack messages of type " + object.getClass());
        }

        updateFriendListReport report = (updateFriendListReport) object;

        if (this.getAccumulator().getPlayerID() == report.getPlayerID())
        {
            updateFriendListMessage msg = new updateFriendListMessage(report.getFriendDTO(), report.isQuiet());
            return msg;
        }
        return null;
    }

    /**
     * Get the types of reports we can pack
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(updateFriendListReport.class);
        return result;
    }

}
