package edu.ship.engr.shipsim.communication.packers;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginSuccessMessage;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.RestfulLoginSuccessfulReport;

import java.util.ArrayList;

/**
 * @author Derek
 */
public class RestfulLoginSuccessMessagePacker extends MessagePacker
{
    @Override
    public Message pack(Report object)
    {
        RestfulLoginSuccessfulReport report = (RestfulLoginSuccessfulReport) object;

        return new RestfulLoginSuccessMessage(report.getPlayerID());
    }

    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        return Lists.newArrayList(RestfulLoginSuccessfulReport.class);
    }
}
