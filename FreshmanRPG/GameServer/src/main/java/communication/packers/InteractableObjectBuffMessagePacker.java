package communication.packers;

import java.util.ArrayList;

import communication.messages.BuffMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.InteractableObjectBuffReport;

/**
 * Listens for Buff reports to send buff message
 * @author Elisabeth Ostrow, Stephen Clabaugh
 *
 */
public class InteractableObjectBuffMessagePacker extends MessagePacker
{

	/**
	 * builds the message if this is the appropriate packer
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		InteractableObjectBuffReport report = (InteractableObjectBuffReport) object;

		int playerID = report.getPlayerID();
		int pointPool = report.getExpPointPool();

		if (this.getAccumulator().getPlayerID() == playerID)
		{
			BuffMessage msg = new BuffMessage(playerID, pointPool);
			return msg;
		}
		return null;
	}

	/**
	 * The list of reports we listen for
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(InteractableObjectBuffReport.class);
		return result;
	}

}
