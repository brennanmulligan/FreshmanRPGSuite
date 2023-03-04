package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerMovedMessage;
import edu.ship.engr.shipsim.model.reports.ClientPlayerMovedReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * If a movement report is for this client's player, then we have to send it to the server.
 * If not, we can ignore it.
 *
 * @author merlin
 */
public class PlayerMovedMessagePacker extends MessagePacker
{

    /**
     *
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        ClientPlayerMovedReport movementReport = (ClientPlayerMovedReport) object;
        int playerID = movementReport.getID();
        if (movementReport.isThisClientsPlayer())
        {
            Message msg = new PlayerMovedMessage(playerID, movementReport.isQuiet(), movementReport.getNewPosition());
            return msg;
        }
        return null;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result =
                new ArrayList<>();
        result.add(ClientPlayerMovedReport.class);
        return result;
    }

}
