package communication.packers;

import java.util.ArrayList;

import communication.messages.Message;
import communication.messages.updateFriendListMessage;
import model.QualifiedObservableReport;
import model.reports.updateFriendListReport;

/**
 * @author Christian C, Andrew M
 */
public class updateFriendListPacker extends MessagePacker
{

	/**
	 * Packs the report into a message
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass() != updateFriendListReport.class)
		{
			throw new IllegalArgumentException(
					"updateFriendListPacker cannot pack messages of type " + object.getClass());
		}

		updateFriendListReport report = (updateFriendListReport) object;

		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
		{
			updateFriendListMessage msg = new updateFriendListMessage(report.getFriendDTO());
			return msg;
		}
		return null;
	}

	/**
	 * Get the types of reports we can pack
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(updateFriendListReport.class);
		return result;
	}

}
