package edu.ship.engr.shipsim.communication.packers;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginMessage;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.RestfulLoginInitiatedReport;

import java.util.ArrayList;

/**
 * @author Derek
 */
public class RestfulLoginMessagePacker extends MessagePacker
{
    @Override
    public Message pack(Report object)
    {
        RestfulLoginInitiatedReport report = (RestfulLoginInitiatedReport) object;

        return new RestfulLoginMessage(report.getUsername(), report.getPassword());
    }

    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        return Lists.newArrayList(RestfulLoginInitiatedReport.class);
    }
}
