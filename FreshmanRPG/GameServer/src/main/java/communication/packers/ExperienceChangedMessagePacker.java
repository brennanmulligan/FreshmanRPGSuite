package communication.packers;

import java.util.ArrayList;

import communication.messages.ExperienceChangedMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.ExperienceChangedReport;

/**
 * @author Ryan
 *
 */
public class ExperienceChangedMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass() != ExperienceChangedReport.class)
		{
			throw new IllegalArgumentException(
					"ExperienceChangedMessagePacker cannot pack messages of type " + object.getClass());
		}

		ExperienceChangedReport report = (ExperienceChangedReport) object;

		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
		{
			ExperienceChangedMessage msg = new ExperienceChangedMessage(report.getPlayerID(),
					report.getExperiencePoints(), report.getRecord());
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
		result.add(ExperienceChangedReport.class);
		return result;
	}

}
