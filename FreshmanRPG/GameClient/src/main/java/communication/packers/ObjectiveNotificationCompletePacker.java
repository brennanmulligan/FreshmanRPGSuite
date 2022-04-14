package communication.packers;

import java.util.ArrayList;

import communication.messages.ObjectiveNotificationCompleteMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.ObjectiveNotificationCompleteReport;

/**
 * @author Ryan
 *
 */
public class ObjectiveNotificationCompletePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		ObjectiveNotificationCompleteReport r = (ObjectiveNotificationCompleteReport) object;
		return new ObjectiveNotificationCompleteMessage(r.getPlayerID(), r.getQuestID(), r.getObjectiveID());
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result =
				new ArrayList<>();
		result.add(ObjectiveNotificationCompleteReport.class);
		return result;
	}

}
