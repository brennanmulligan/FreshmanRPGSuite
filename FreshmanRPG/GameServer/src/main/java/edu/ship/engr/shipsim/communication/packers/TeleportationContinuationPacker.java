package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TeleportationContinuationMessage;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.MapToServerMapping;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.PlayerReadyToTeleportReport;

import java.util.ArrayList;

/**
 * Progresses teleportation after the player has been moved and persisted.
 */
public class TeleportationContinuationPacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(Report)
     */
    @Override
    public Message pack(Report object)
    {
        PlayerReadyToTeleportReport report = (PlayerReadyToTeleportReport) object;
        if (this.getAccumulator().getPlayerID() == report.getPlayerID())
        {
            try
            {
                MapToServerMapping mapping = new MapToServerMapping(report.getMap());

                int newPin = PlayerManager.getSingleton().getNewPinFor(report.getPlayerID());
                LoggerManager.getSingleton().getLogger().info("Sending teleportation " +
                        "continuation msg to " + report.getPlayerID() + " sending them " +
                        "to " + report.getMap());
                return new TeleportationContinuationMessage(report.getMap(), mapping.getHostName(),
                        mapping.getPortNumber(), report.getPlayerID(),
                        newPin);
            }
            catch (DatabaseException e)
            {
                e.printStackTrace();

            }
        }
        return null;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result = new ArrayList<>();
        result.add(PlayerReadyToTeleportReport.class);
        return result;
    }

}
