package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TeleportationInitiationMessage;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.ChangeMapReport;

import java.util.ArrayList;

/**
 * @author Matthew Kujawski and Frank
 * Listens for the chang map report.
 * Creates and packs a teleportation initiation message.
 */
public class TeleportationInitiationMessagePacker extends MessagePacker
{

    /**
     * @param object A ChangeMapReport to be translated into a TeleportationInitiationMessage
     * @return A TeleportationInitiationMessage based on the ChangeMapReport that was given.
     */
    @Override
    public Message pack(Report object)
    {
        if (object.getClass() != ChangeMapReport.class)
        {
            throw new IllegalArgumentException(
                    "TeleportationInitiationMessagePacker cannot pack messages of type "
                            + object.getClass());
        }
        ChangeMapReport report = (ChangeMapReport) object;

        return new TeleportationInitiationMessage(report.getPlayerID(),
                report.getMapName(), report.getPosition());
    }

    /**
     * The packer listens for ChangeMapReport.
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result =
                new ArrayList<>();
        result.add(ChangeMapReport.class);
        return result;
    }

}
