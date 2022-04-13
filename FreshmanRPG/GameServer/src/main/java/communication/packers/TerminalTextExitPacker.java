package communication.packers;

import communication.messages.Message;
import communication.messages.TerminalTextExitMessage;
import model.QualifiedObservableReport;
import model.reports.TerminalTextExitReport;
import java.util.ArrayList;

/**
 * Packer for processing an Exit report.
 * Receives the exit report, and send the TerminalTextExitMessage over the the
 * client side handler (TerminalTextExitHandler).
 *
 * Authors: John G. , Ian L.
 *
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
    public Message pack(QualifiedObservableReport object)
    {
        if (object.getClass() != TerminalTextExitReport.class)
        {
            throw new IllegalArgumentException(
                    "TerminalTextExitPacker cannot pack messages of type "
                            + object.getClass());
        }
        TerminalTextExitReport report = (TerminalTextExitReport) object;
        Message msg = new TerminalTextExitMessage(report.getPlayerID());
        System.out.println("Sending message");
        return msg;
    }

    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
        result.add(TerminalTextExitReport.class);
        return result;

    }
}
