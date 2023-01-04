package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.KeyInputMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.ClientKeyInputSentReport;

import java.util.ArrayList;

/**
 * Tests functionality for a key input message packer
 *
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputMessagePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(Report)
     */
    @Override
    public Message pack(Report object)
    {
        if (object.getClass() != ClientKeyInputSentReport.class)
        {
            throw new IllegalArgumentException("KeyInputPacker cannot pack messages of type " + object.getClass());
        }

        ClientKeyInputSentReport report = (ClientKeyInputSentReport) object;
        KeyInputMessage msg = new KeyInputMessage(report.getInput());

        return msg;
    }

    /**
     *
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result =
                new ArrayList<>();
        result.add(ClientKeyInputSentReport.class);
        return result;
    }

}
