package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.HighScoreRequestMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.reports.HighScoreRequestReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * Packer for the high score request message
 *
 * @author Ryan
 */
public class HighScoreRequestPacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        return new HighScoreRequestMessage(getAccumulator().getPlayerID(), getAccumulator().getFirstMessage().isQuietMessage());
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result =
                new ArrayList<>();
        result.add(HighScoreRequestReport.class);
        return result;
    }

}
