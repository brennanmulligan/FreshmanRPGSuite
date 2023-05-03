package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;
import edu.ship.engr.shipsim.model.reports.UpdatePlayerInformationReport;

import java.util.ArrayList;

/**
 * @author Merlin
 */
public class UpdatePlayerInformationMessagePacker extends MessagePacker
{

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(UpdatePlayerInformationReport.class);
        return result;
    }

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        UpdatePlayerInformationReport report = (UpdatePlayerInformationReport) object;
        if (this.getAccumulator().getPlayerID() == report.getPlayerID())
        {
            return new InitializeThisClientsPlayerMessage(report.getPlayerID(), report.isQuiet(),report.getClientPlayerQuestList(), report.getFriendsList(), report.getExperiencePoints(),
                    report.getDoubloons(), report.getLevel());
        }
        return null;
    }

}
