package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TerminalTextExitMessage;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;
import edu.ship.engr.shipsim.model.reports.TerminalTextExitReport;

import java.util.ArrayList;

/**
 * Packer for processing an Exit report.
 * Receives the exit report, and send the TerminalTextExitMessage over the the
 * client side handler (TerminalTextExitHandler).
 * <p>
 * Authors: John G. , Ian L.
 */
public class TerminalTextExitPacker extends MessagePacker
{

    /**
     * Receives an ExitReport, and packs the Report into a message to notify the client side connection.
     * The client side handler is "TerminaTextExitHandler".
     *
     * @param object - the ExitReport pushed from the command sent
     * @return
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass() != TerminalTextExitReport.class)
        {
            throw new IllegalArgumentException(
                    "TerminalTextExitPacker cannot pack messages of type "
                            + object.getClass());
        }
        TerminalTextExitReport report = (TerminalTextExitReport) object;
        Message msg = new TerminalTextExitMessage(report.getPlayerID(), report.isQuiet());
        System.out.println("Sending message");
        return msg;
    }

    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(TerminalTextExitReport.class);
        return result;

    }
}
