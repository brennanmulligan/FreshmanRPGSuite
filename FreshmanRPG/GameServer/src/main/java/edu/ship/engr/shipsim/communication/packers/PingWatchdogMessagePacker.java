package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PingWatchdogMessage;
import edu.ship.engr.shipsim.model.reports.PingWatchdogReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;

public class PingWatchdogMessagePacker extends MessagePacker
{
    @Override
    public Message pack(SendMessageReport report)
    {
        if (!getAccumulator().isConnectedToWatchdogServer())
        {
            return null;
        }

        if (report.getClass().equals(PingWatchdogReport.class))
        {
            PingWatchdogReport pingReport = (PingWatchdogReport) report;

            return new PingWatchdogMessage(pingReport.getHostName());
        }

        return null;
    }

    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> classes = Lists.newArrayList();

        classes.add(PingWatchdogReport.class);

        return classes;
    }
}
