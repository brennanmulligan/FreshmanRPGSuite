package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.MapFileMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.PlayerConnectionReport;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Merlin
 */
public class MapFileMessagePacker extends MessagePacker
{


    /**
     * @see MessagePacker#pack(QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        PlayerConnectionReport report = (PlayerConnectionReport) object;
        try
        {
            int playerID = report.getPlayerID();
            if (this.getAccumulator().getPlayerID() == playerID)
            {
                // send this server's map file back to the client when they
                // connect to the server
                return new MapFileMessage(OptionsManager.getSingleton().getMapFileTitle());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
        result.add(PlayerConnectionReport.class);
        return result;
    }

}
