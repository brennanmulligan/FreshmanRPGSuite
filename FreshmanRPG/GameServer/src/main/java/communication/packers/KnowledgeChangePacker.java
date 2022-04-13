package communication.packers;

import java.util.ArrayList;

import communication.messages.KnowledgeChangedMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.KnowledgePointsChangeReport;

/**
 * @author Matthew Croft
 *
 */
public class KnowledgeChangePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{

		if (object.getClass() != KnowledgePointsChangeReport.class)
		{
			throw new IllegalArgumentException(
					"KnowledgeChangedMessagePacker cannot pack messages of type " + object.getClass());
		}

		KnowledgePointsChangeReport report = (KnowledgePointsChangeReport) object;

		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
		{
			KnowledgeChangedMessage msg = new KnowledgeChangedMessage(report.getPlayerID(),
					report.getKnowledgePoints(), report.getBuffPool());
			return msg;
		}
		return null;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(KnowledgePointsChangeReport.class);
		return result;
	}

}
