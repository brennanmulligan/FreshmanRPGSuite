package communication.packers;

import java.util.ArrayList;

import communication.messages.Message;
import communication.messages.QuestStateChangeMessage;
import model.QualifiedObservableReport;
import model.reports.QuestStateChangeReport;

/**
 * If our player quest state has changed, we need to send a message so that the
 * client can tell the player of their success
 *
 * @author Merlin
 *
 */
public class QuestStateChangeMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		QuestStateChangeReport rpt = (QuestStateChangeReport) object;
		QuestStateChangeMessage msg = null;
		int playerID = rpt.getPlayerID();
		if (this.getAccumulator().getPlayerID() == playerID)
		{
			msg = new QuestStateChangeMessage(rpt.getPlayerID(), rpt.getQuestID(), rpt.getQuestTitle(),
					rpt.getQuestDescription(), rpt.getNewState());
		}
		return msg;

	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(QuestStateChangeReport.class);
		return result;
	}

}
