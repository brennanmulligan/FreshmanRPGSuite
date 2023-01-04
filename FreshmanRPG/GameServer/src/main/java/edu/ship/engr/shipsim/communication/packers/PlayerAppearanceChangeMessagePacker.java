package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.PlayerAppearanceChangeReport;

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
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result = new ArrayList<>();
        result.add(PlayerAppearanceChangeReport.class);
        return result;
    }

    /**
     * @see MessagePacker#pack(Report)
     */
    @Override
    public Message pack(Report object)
    {
//		PlayerAppearanceChangeReport report = (PlayerAppearanceChangeReport) object;
//		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
//		{
//			PlayerAppearanceChangeMessage msg = new PlayerAppearanceChangeMessage(report.getPlayerID(), report.getAppearanceType());
//			return msg;
//		}
        return null;
    }

}
