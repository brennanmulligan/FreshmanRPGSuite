package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ReceiveTerminalTextMessage;
import edu.ship.engr.shipsim.model.reports.ReceiveTerminalTextReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * @author Denny Fleagle
 * @author Chris Roadcap
 * @author Kanza Amin
 */

public class ReceiveTerminalTextMessagePacker extends MessagePacker
{

    /**
     * pack the report to a message
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass() != ReceiveTerminalTextReport.class)
        {
            throw new IllegalArgumentException(
                    "ReceiveTerminalTextMessagePacker cannot pack messages of type" + object.getClass());
        }

        ReceiveTerminalTextReport report = (ReceiveTerminalTextReport) object;


        int playerID = report.getPlayerID();
        String terminalText = report.getResultText();
        if (this.getAccumulator().getPlayerID() == playerID)
        {
            ReceiveTerminalTextMessage msg = new ReceiveTerminalTextMessage(playerID, report.isQuiet(), terminalText);
            return msg;
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
        result.add(ReceiveTerminalTextReport.class);
        return result;
    }

}
