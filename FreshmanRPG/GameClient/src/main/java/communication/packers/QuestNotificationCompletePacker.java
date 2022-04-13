package communication.packers;

import java.util.ArrayList;

import communication.messages.Message;
import communication.messages.QuestNotificationCompleteMessage;
import model.QualifiedObservableReport;
import model.reports.QuestNotificationCompleteReport;

/**
 * A message that tells the server that the player has seen that a quest has changed state
 * @author Merlin
 *
 */
public class QuestNotificationCompletePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		QuestNotificationCompleteReport r = (QuestNotificationCompleteReport) object;
		return new QuestNotificationCompleteMessage(r.getPlayerID(), r.getQuestID());
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result =
				new ArrayList<>();
		result.add(QuestNotificationCompleteReport.class);
		return result;
	}

}
