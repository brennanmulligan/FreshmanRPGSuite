package edu.ship.engr.shipsim.communication.packers;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginSuccessMessage;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.RestfulLoginSuccessfulReport;

import java.util.ArrayList;

/**
 * @author Derek
 */
public class RestfulLoginSuccessMessagePacker extends MessagePacker
{
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        RestfulLoginSuccessfulReport report = (RestfulLoginSuccessfulReport) object;

        return new RestfulLoginSuccessMessage(report.getPlayerID());
    }

    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        return Lists.newArrayList(RestfulLoginSuccessfulReport.class);
    }
}
