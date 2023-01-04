package edu.ship.engr.shipsim.communication.packers;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.communication.messages.LoginSuccessfulMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.LoginSuccessfulReport;

import java.util.ArrayList;

/**
 * @author Derek
 */
public class LoginSuccessMessagePacker extends MessagePacker
{
    @Override
    public Message pack(Report object)
    {
        LoginSuccessfulReport report = (LoginSuccessfulReport) object;

        return new LoginSuccessfulMessage(report.getPlayerID(), report.getHostname(), report.getPort(), report.getPin());
    }

    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        return Lists.newArrayList(LoginSuccessfulReport.class);
    }
}
