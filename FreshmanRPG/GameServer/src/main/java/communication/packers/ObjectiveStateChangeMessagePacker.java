package communication.packers;

import java.util.ArrayList;

import communication.messages.ObjectiveStateChangeMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.ObjectiveStateChangeReport;

/**
 * @author Ryan
 *
 */
public class ObjectiveStateChangeMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		ObjectiveStateChangeReport rpt = (ObjectiveStateChangeReport) object;
		ObjectiveStateChangeMessage msg = null;
		int playerID = rpt.getPlayerID();
		if (this.getAccumulator().getPlayerID() == playerID)
		{
			msg = new ObjectiveStateChangeMessage(rpt.getPlayerID(), rpt.getQuestID(), rpt.getObjectiveID(),
					rpt.getObjectiveDescription(), rpt.getNewState(), rpt.isRealLifeObjective(), rpt.getWitnessTitle());
		}
		return msg;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(ObjectiveStateChangeReport.class);
		return result;
	}

}
