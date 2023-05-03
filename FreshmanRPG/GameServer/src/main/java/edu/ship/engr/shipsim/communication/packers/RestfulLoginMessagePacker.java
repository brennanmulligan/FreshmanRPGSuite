package edu.ship.engr.shipsim.communication.packers;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginMessage;
import edu.ship.engr.shipsim.model.reports.RestfulLoginInitiatedReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * @author Derek
 */
public class RestfulLoginMessagePacker extends MessagePacker
{
    @Override
    public Message pack(SendMessageReport object)
    {
        RestfulLoginInitiatedReport report = (RestfulLoginInitiatedReport) object;

        return new RestfulLoginMessage(report.getPlayerName(), report.getPassword(), report.isQuiet());
    }

    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        return Lists.newArrayList(RestfulLoginInitiatedReport.class);
    }
}
