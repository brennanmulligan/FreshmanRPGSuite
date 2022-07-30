package communication.packers;

import communication.messages.Message;
import communication.messages.PlayerLeaveMessage;
import datasource.LoggerManager;
import model.QualifiedObservableReport;
import model.reports.PlayerLeaveReport;

import java.util.ArrayList;

/**
 * Packs a message telling clients that a player has left this area server
 *
 * @author Merlin
 */
public class PlayerLeaveMessagePacker extends MessagePacker
{

    /**
     * @see communication.packers.MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
        result.add(PlayerLeaveReport.class);
        return result;
    }

    /**
     * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        if (object.getClass().equals(PlayerLeaveReport.class))
        {
            PlayerLeaveReport report = (PlayerLeaveReport) object;
            if (report.getPlayerID() != getAccumulator().getPlayerID())
            {
                LoggerManager.getSingleton().getLogger()
                        .info("Telling player " + this.getAccumulator().getPlayerID() +
                                " that player " + report.getPlayerID() + " left");
                return new PlayerLeaveMessage(report.getPlayerID());
            }
        }
        return null;
    }

}
