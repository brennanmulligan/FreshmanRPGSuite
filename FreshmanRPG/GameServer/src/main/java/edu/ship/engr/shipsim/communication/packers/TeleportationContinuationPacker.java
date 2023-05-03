package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TeleportationContinuationMessage;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.MapToServerMapping;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.reports.PlayerReadyToTeleportReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * Progresses teleportation after the player has been moved and persisted.
 */
public class TeleportationContinuationPacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        PlayerReadyToTeleportReport report = (PlayerReadyToTeleportReport) object;
        if (this.getAccumulator().getPlayerID() == report.getPlayerID())
        {
            try
            {
                MapToServerMapping mapping = new MapToServerMapping(report.getMapName());

                int newPin = PlayerManager.getSingleton().getNewPinFor(report.getPlayerID());
                LoggerManager.getSingleton().getLogger().info("Sending teleportation " +
                        "continuation msg to " + report.getPlayerID() + " sending them " +
                        "to " + report.getMapName());
                return new TeleportationContinuationMessage(report.getMapName(), mapping.getHostName(),
                        mapping.getPortNumber(), report.getPlayerID(),
                        newPin, report.isQuiet());
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
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(PlayerReadyToTeleportReport.class);
        return result;
    }

}
