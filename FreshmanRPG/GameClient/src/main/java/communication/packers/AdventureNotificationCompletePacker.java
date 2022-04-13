package communication.packers;

import java.util.ArrayList;

import communication.messages.AdventureNotificationCompleteMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.AdventureNotificationCompleteReport;

/**
 * @author Ryan
 *
 */
public class AdventureNotificationCompletePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		AdventureNotificationCompleteReport r = (AdventureNotificationCompleteReport) object;
		return new AdventureNotificationCompleteMessage(r.getPlayerID(), r.getQuestID(), r.getAdventureID());
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result =
				new ArrayList<>();
		result.add(AdventureNotificationCompleteReport.class);
		return result;
	}

}
