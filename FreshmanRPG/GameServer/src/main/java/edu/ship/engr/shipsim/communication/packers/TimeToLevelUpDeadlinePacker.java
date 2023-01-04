package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TimeToLevelUpDeadlineMessage;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.TimeToLevelUpDeadlineReport;

import java.util.ArrayList;

/**
 * Packs the time to level up deadline message
 *
 * @author Chris, Marty, Evan
 */
public class TimeToLevelUpDeadlinePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(Report)
     */
    @Override
    public Message pack(Report object)
    {
        if (object.getClass() != TimeToLevelUpDeadlineReport.class)
        {
            throw new IllegalArgumentException(
                    "TimeToLevelUpDeadlineReport cannot pack messages of type " + object.getClass());
        }

        TimeToLevelUpDeadlineReport report = (TimeToLevelUpDeadlineReport) object;

        if (this.getAccumulator().getPlayerID() == report.getPlayerID())
        {
            TimeToLevelUpDeadlineMessage msg = new TimeToLevelUpDeadlineMessage(report.getPlayerID(),
                    report.getTimeToDeadline(), report.getNextLevel());
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
        result.add(TimeToLevelUpDeadlineReport.class);
        return result;
    }
}
