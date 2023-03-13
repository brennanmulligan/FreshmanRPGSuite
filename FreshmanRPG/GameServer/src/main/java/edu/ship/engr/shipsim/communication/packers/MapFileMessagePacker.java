package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.MapFileMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.model.reports.PlayerConnectionReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Merlin
 */
public class MapFileMessagePacker extends MessagePacker
{


    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        PlayerConnectionReport report = (PlayerConnectionReport) object;
        try
        {
            int playerID = report.getPlayerID();
            boolean isQuiet =  report.isQuiet();
            if (this.getAccumulator().getPlayerID() == playerID)
            {
                // send this server's map file back to the client when they
                // connect to the server
                return new MapFileMessage(playerID, isQuiet, OptionsManager.getSingleton().getMapFileTitle());
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
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(PlayerConnectionReport.class);
        return result;
    }

}
