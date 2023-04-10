package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerAppearanceChangeMessage;
import edu.ship.engr.shipsim.model.reports.PlayerAppearanceChangeReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * @author sb6844
 */
public class PlayerAppearanceChangeMessagePacker extends MessagePacker
{

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(PlayerAppearanceChangeReport.class);
        return result;
    }

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
		PlayerAppearanceChangeReport report = (PlayerAppearanceChangeReport) object;
		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
		{
			PlayerAppearanceChangeMessage msg = new PlayerAppearanceChangeMessage(report.getPlayerID(), report.getNewWearing());
			return msg;
		}
        return null;
    }

}
