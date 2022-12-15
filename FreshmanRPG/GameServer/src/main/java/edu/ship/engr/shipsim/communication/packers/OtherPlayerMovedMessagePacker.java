package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.OtherPlayerMovedMessage;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.PlayerMovedReport;

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
     * @see MessagePacker#pack(QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        if (object.getClass().equals(PlayerMovedReport.class))
        {
            PlayerMovedReport report = (PlayerMovedReport) object;
            int playerID = report.getPlayerID();
            if (this.getAccumulator().getPlayerID() != playerID)
            {
                OtherPlayerMovedMessage msg = new OtherPlayerMovedMessage(playerID, report.getNewPosition());
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
        result.add(PlayerMovedReport.class);
        return result;
    }

}
