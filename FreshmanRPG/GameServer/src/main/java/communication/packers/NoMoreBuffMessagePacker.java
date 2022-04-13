package communication.packers;

import java.util.ArrayList;

import communication.messages.Message;
import communication.messages.NoMoreBuffMessage;
import model.QualifiedObservableReport;
import model.reports.NoMoreBuffReport;

/**
 * Packs the NoMoreBuffMessage
 * @author Aaron Gerber
 * @author Stephen Clabaugh
 */
public class NoMoreBuffMessagePacker extends MessagePacker
{

	/**
	 * Pack the report if the playerID matches the playerID it was given
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		NoMoreBuffReport report = (NoMoreBuffReport) object;

		int playerID = report.getPlayerID();
		if (this.getAccumulator().getPlayerID() == playerID)
		{
			NoMoreBuffMessage msg = new NoMoreBuffMessage(playerID);
			return msg;
		}
		return null;
	}

	/**
	 * List the types of reports this can pack.
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(NoMoreBuffReport.class);
		return result;
	}

}
