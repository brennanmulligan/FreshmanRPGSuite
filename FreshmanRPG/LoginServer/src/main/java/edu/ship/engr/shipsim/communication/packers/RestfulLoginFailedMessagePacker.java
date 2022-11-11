package edu.ship.engr.shipsim.communication.packers;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.RestfulLoginFailedMessage;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.RestfulLoginFailedReport;

import java.util.ArrayList;

/**
 * @author Derek
 */
public class RestfulLoginFailedMessagePacker extends MessagePacker
{
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        return new RestfulLoginFailedMessage();
    }

    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        return Lists.newArrayList(RestfulLoginFailedReport.class);
    }
}
