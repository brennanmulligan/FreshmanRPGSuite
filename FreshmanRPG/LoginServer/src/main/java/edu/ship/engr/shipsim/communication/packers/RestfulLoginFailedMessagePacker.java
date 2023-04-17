package edu.ship.engr.shipsim.communication.packers;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginFailedMessage;
import edu.ship.engr.shipsim.model.reports.RestfulLoginServerFailedReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * @author Derek
 */
public class RestfulLoginFailedMessagePacker extends MessagePacker
{
    @Override
    public Message pack(SendMessageReport object)
    {
        return new RestfulLoginFailedMessage();
    }

    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        return Lists.newArrayList(RestfulLoginServerFailedReport.class);
    }
}
