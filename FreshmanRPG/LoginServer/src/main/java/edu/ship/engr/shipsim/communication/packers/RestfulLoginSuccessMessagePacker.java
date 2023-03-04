package edu.ship.engr.shipsim.communication.packers;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginSuccessMessage;
import edu.ship.engr.shipsim.model.reports.RestfulLoginServerSuccessfulReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * @author Derek
 */
public class RestfulLoginSuccessMessagePacker extends MessagePacker
{
    @Override
    public Message pack(SendMessageReport object)
    {
        RestfulLoginServerSuccessfulReport
                report = (RestfulLoginServerSuccessfulReport) object;

        return new RestfulLoginSuccessMessage(report.getPlayerID(),
                report.isQuiet());
    }

    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        return Lists.newArrayList(RestfulLoginServerSuccessfulReport.class);
    }
}
