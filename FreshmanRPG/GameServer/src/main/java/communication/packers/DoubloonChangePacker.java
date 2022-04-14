package communication.packers;

import java.util.ArrayList;

import communication.messages.DoubloonsChangedMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.DoubloonChangeReport;

/**
 * @author Matthew Croft
 *
 */
public class DoubloonChangePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{

		if (object.getClass() != DoubloonChangeReport.class)
		{
			throw new IllegalArgumentException(
					"DoubloonChangedMessagePacker cannot pack messages of type " + object.getClass());
		}

		DoubloonChangeReport report = (DoubloonChangeReport) object;

		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
		{
			DoubloonsChangedMessage msg = new DoubloonsChangedMessage(report.getPlayerID(),
					report.getDoubloons(), report.getBuffPool());
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
		result.add(DoubloonChangeReport.class);
		return result;
	}

}
