package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.MapFileMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.PlayerConnectionReport;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Merlin
 */
public class MapFileMessagePacker extends MessagePacker
{


    /**
     * @see MessagePacker#pack(Report)
     */
    @Override
    public Message pack(Report object)
    {
        PlayerConnectionReport report = (PlayerConnectionReport) object;
        try
        {
            int playerID = report.getPlayerID();
            if (this.getAccumulator().getPlayerID() == playerID)
            {
                // send this server's map file back to the client when they
                // connect to the server
                return new MapFileMessage(playerID, OptionsManager.getSingleton().getMapFileTitle());
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
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result = new ArrayList<>();
        result.add(PlayerConnectionReport.class);
        return result;
    }

}
