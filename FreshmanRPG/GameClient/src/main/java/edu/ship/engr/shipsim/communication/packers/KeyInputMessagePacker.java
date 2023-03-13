package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.KeyInputMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.reports.ClientKeyInputSentReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * Tests functionality for a key input message packer
 *
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputMessagePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass() != ClientKeyInputSentReport.class)
        {
            throw new IllegalArgumentException("KeyInputPacker cannot pack messages of type " + object.getClass());
        }

        ClientKeyInputSentReport report = (ClientKeyInputSentReport) object;
        KeyInputMessage msg = new KeyInputMessage(report.getInput(), report.isQuiet());

        return msg;
    }

    /**
     *
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result =
                new ArrayList<>();
        result.add(ClientKeyInputSentReport.class);
        return result;
    }

}
