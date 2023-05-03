package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.OtherPlayerMovedMessage;
import edu.ship.engr.shipsim.model.reports.PlayerMovedReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * Takes the information given to us when MovementNotifier updates and
 * translates it to the appropriate MovementMessage. It turns out this is pretty
 * trivial since the MovementNotifier pushes a MovementMessage
 *
 * @author merlin
 */
public class OtherPlayerMovedMessagePacker extends MessagePacker
{

    /**
     * Generates a MovementMessage for a PlayerMovedReport that not associated
     * with the player in the accumulator.
     *
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass().equals(PlayerMovedReport.class))
        {
            PlayerMovedReport report = (PlayerMovedReport) object;
            int playerID = report.getPlayerID();
            if (this.getAccumulator().getPlayerID() != playerID)
            {
                OtherPlayerMovedMessage msg = new OtherPlayerMovedMessage(playerID, report.isQuiet(), report.getPosition());
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
        result.add(PlayerMovedReport.class);
        return result;
    }

}
