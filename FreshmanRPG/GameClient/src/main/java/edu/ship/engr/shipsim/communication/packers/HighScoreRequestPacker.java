package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.HighScoreRequestMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
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
     * @see MessagePacker#pack(QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        return new HighScoreRequestMessage();
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result =
                new ArrayList<>();
        result.add(HighScoreRequestReport.class);
        return result;
    }

}
