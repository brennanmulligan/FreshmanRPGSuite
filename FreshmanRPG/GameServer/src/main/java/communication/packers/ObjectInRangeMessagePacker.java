
package communication.packers;

import java.util.ArrayList;

import communication.messages.InteractionDeniedMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.InteractionDeniedReport;

/**
 * @author ag0612
 * @author jk1964
 * Packs a message from a ObjectInRangeReport
 */
public class ObjectInRangeMessagePacker extends MessagePacker
{

	/**
	 * Confirms that it is the proper report type, and then packs the message
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{

		InteractionDeniedReport report = (InteractionDeniedReport) object;

		int playerID = report.getPlayerID();
		if (this.getAccumulator().getPlayerID() == playerID)
		{
			InteractionDeniedMessage msg = new InteractionDeniedMessage(playerID);
			return msg;
		}
		return null;
	}

	/**
	 * Returns the report types the packer will pack
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(InteractionDeniedReport.class);
		return result;
	}

}
