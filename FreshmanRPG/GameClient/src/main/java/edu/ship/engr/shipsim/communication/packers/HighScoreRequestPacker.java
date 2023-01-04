package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.HighScoreRequestMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.HighScoreRequestReport;

import java.util.ArrayList;

/**
 * Packer for the high score request message
 *
 * @author Ryan
 */
public class HighScoreRequestPacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(Report)
     */
    @Override
    public Message pack(Report object)
    {
        return new HighScoreRequestMessage();
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result =
                new ArrayList<>();
        result.add(HighScoreRequestReport.class);
        return result;
    }

}
