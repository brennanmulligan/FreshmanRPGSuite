package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerLeaveMessage;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.PlayerLeaveReport;

import java.util.ArrayList;

/**
 * Packs a message telling clients that a player has left this area server
 *
 * @author Merlin
 */
public class PlayerLeaveMessagePacker extends MessagePacker
{

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result = new ArrayList<>();
        result.add(PlayerLeaveReport.class);
        return result;
    }

    /**
     * @see MessagePacker#pack(Report)
     */
    @Override
    public Message pack(Report object)
    {
        if (object.getClass().equals(PlayerLeaveReport.class))
        {
            PlayerLeaveReport report = (PlayerLeaveReport) object;
            if (report.getPlayerID() != getAccumulator().getPlayerID())
            {
                LoggerManager.getSingleton().getLogger()
                        .info("Telling player " + this.getAccumulator().getPlayerID() +
                                " that player " + report.getPlayerID() + " left");
                return new PlayerLeaveMessage(report.getPlayerID());
            }
        }
        return null;
    }

}
