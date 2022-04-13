package communication.packers;

import java.util.ArrayList;

import communication.messages.Message;
import communication.messages.PlayerMovedMessage;
import model.QualifiedObservableReport;
import model.reports.ClientPlayerMovedReport;

/**
 * If a movement report is for this client's player, then we have to send it to the server.
 * If not, we can ignore it.
 * @author merlin
 *
 */
public class PlayerMovedMessagePacker extends MessagePacker
{

	/**
	 *
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		ClientPlayerMovedReport movementReport = (ClientPlayerMovedReport) object;
		int playerID = movementReport.getID();
		if (movementReport.isThisClientsPlayer())
		{
			Message msg = new PlayerMovedMessage(playerID, movementReport.getNewPosition());
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
		ArrayList<Class<? extends QualifiedObservableReport>> result =
				new ArrayList<>();
		result.add(ClientPlayerMovedReport.class);
		return result;
	}

}
