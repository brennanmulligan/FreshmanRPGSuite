package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.ExperienceChangedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.ExperienceChangedReport;

import java.util.ArrayList;

/**
 * @author Ryan
 */
public class ExperienceChangedMessagePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(Report)
     */
    @Override
    public Message pack(Report object)
    {
        if (object.getClass() != ExperienceChangedReport.class)
        {
            throw new IllegalArgumentException(
                    "ExperienceChangedMessagePacker cannot pack messages of type " + object.getClass());
        }

        ExperienceChangedReport report = (ExperienceChangedReport) object;

        if (this.getAccumulator().getPlayerID() == report.getPlayerID())
        {
            ExperienceChangedMessage msg = new ExperienceChangedMessage(report.getPlayerID(),
                    report.getExperiencePoints(), report.getRecord());
            return msg;
        }
        return null;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result = new ArrayList<>();
        result.add(ExperienceChangedReport.class);
        return result;
    }

}
