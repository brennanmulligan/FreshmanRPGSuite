package communication.packers;

import java.util.ArrayList;

import communication.messages.Message;
import communication.messages.TimeToLevelUpDeadlineMessage;
import model.QualifiedObservableReport;
import model.reports.TimeToLevelUpDeadlineReport;

/**
 * Packs the time to level up deadline message
 *
 * @author Chris, Marty, Evan
 *
 */
public class TimeToLevelUpDeadlinePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass() != TimeToLevelUpDeadlineReport.class)
		{
			throw new IllegalArgumentException(
					"TimeToLevelUpDeadlineReport cannot pack messages of type " + object.getClass());
		}

		TimeToLevelUpDeadlineReport report = (TimeToLevelUpDeadlineReport) object;

		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
		{
			TimeToLevelUpDeadlineMessage msg = new TimeToLevelUpDeadlineMessage(report.getPlayerID(),
					report.getTimeToDeadline(), report.getNextLevel());
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
		result.add(TimeToLevelUpDeadlineReport.class);
		return result;
	}
}
