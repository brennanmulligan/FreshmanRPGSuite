package communication.packers;

import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.PlayerAppearanceChangeReport;

import java.util.ArrayList;

/**
 * @author sb6844
 *
 */
public class PlayerAppearanceChangeMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(PlayerAppearanceChangeReport.class);
		return result;
	}

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
//		PlayerAppearanceChangeReport report = (PlayerAppearanceChangeReport) object;
//		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
//		{
//			PlayerAppearanceChangeMessage msg = new PlayerAppearanceChangeMessage(report.getPlayerID(), report.getAppearanceType());
//			return msg;
//		}
		return null;
	}

}
