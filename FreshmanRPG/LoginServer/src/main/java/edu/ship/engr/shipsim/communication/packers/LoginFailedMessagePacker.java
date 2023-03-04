package edu.ship.engr.shipsim.communication.packers;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.communication.messages.LoginFailedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.reports.LoginFailedReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * @author Derek
 */
public class LoginFailedMessagePacker extends MessagePacker
{
    @Override
    public Message pack(SendMessageReport object)
    {
        LoginFailedReport report = (LoginFailedReport) object;

        return new LoginFailedMessage(report.getMessage(), report.isQuiet());
    }

    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        return Lists.newArrayList(LoginFailedReport.class);
    }
}
