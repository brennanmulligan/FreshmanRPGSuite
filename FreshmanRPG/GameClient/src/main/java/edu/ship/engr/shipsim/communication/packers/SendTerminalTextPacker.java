package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.SendTerminalTextMessage;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;
import edu.ship.engr.shipsim.model.reports.SendTerminalTextReport;

import java.util.ArrayList;

/**
 * @author bl5922 SendTerminalTextPacker that looks for SendTerminalTextReport and
 * creates a OnlinePlayersMessage to send to the server
 */
public class SendTerminalTextPacker extends MessagePacker
{

    /**
     * @param object A SendTerminalTextReport to be translated into a OnlinePlayersMessage
     * @return A OnlinePlayersMessage based on the SendTerminalTextReport that was
     * given.
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass() != SendTerminalTextReport.class)
        {
            throw new IllegalArgumentException("ChatMessagePacker cannot pack messages of type " + object.getClass());
        }

        SendTerminalTextReport report = (SendTerminalTextReport) object;
        SendTerminalTextMessage msg = new SendTerminalTextMessage(report.getPlayerID(), report.isQuiet(), report.getTerminalText());

        return msg;

    }

    /**
     * @see MessagePacker#getReportTypesWePack() This packer
     * listens for SendTerminalTextReport
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(SendTerminalTextReport.class);
        return result;
    }

}
