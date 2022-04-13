package communication.packers;

import java.util.ArrayList;

import communication.messages.AdventureStateChangeMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.AdventureStateChangeReport;

/**
 * @author Ryan
 *
 */
public class AdventureStateChangeMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		AdventureStateChangeReport rpt = (AdventureStateChangeReport) object;
		AdventureStateChangeMessage msg = null;
		int playerID = rpt.getPlayerID();
		if (this.getAccumulator().getPlayerID() == playerID)
		{
			msg = new AdventureStateChangeMessage(rpt.getPlayerID(), rpt.getQuestID(), rpt.getAdventureID(),
					rpt.getAdventureDescription(), rpt.getNewState(), rpt.isRealLifeAdventure(), rpt.getWitnessTitle());
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
		result.add(AdventureStateChangeReport.class);
		return result;
	}

}
