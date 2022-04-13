package communication.packers;

import java.util.ArrayList;

import communication.messages.InitializeThisClientsPlayerMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.UpdatePlayerInformationReport;

/**
 * @author Merlin
 *
 */
public class UpdatePlayerInformationMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(UpdatePlayerInformationReport.class);
		return result;
	}

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		UpdatePlayerInformationReport x = (UpdatePlayerInformationReport) object;
		if (this.getAccumulator().getPlayerID() == x.getPlayerID())
		{
			return new InitializeThisClientsPlayerMessage(x.getClientPlayerQuestList(), x.getFriendsList(), x.getExperiencePts(),
					x.getKnowledgePts(), x.getLevel());
		}
		return null;
	}

}
