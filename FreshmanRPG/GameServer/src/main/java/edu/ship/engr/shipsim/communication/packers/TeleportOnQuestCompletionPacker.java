package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TeleportationContinuationMessage;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;
import edu.ship.engr.shipsim.model.reports.TeleportOnQuestCompletionReport;

import java.util.ArrayList;

/**
 * Packer telling the client to teleport on quest completion
 *
 * @author Zach Thompson, Chris Hershey, Abdul
 */
public class TeleportOnQuestCompletionPacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass().equals(TeleportOnQuestCompletionReport.class))
        {
            TeleportOnQuestCompletionReport report = (TeleportOnQuestCompletionReport) object;
            try
            {
                TeleportationContinuationMessage msg = new TeleportationContinuationMessage(
                        report.getLocation().getMapName(), report.getHostName(), report.getPortNumber(),
                        report.getPlayerID(), PlayerManager.getSingleton().getNewPinFor(report.getPlayerID()), report.isQuiet());
                return msg;
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
        result.add(TeleportOnQuestCompletionReport.class);
        return result;
    }

}
