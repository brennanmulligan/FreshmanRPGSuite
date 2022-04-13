package communication.packers;

import java.util.ArrayList;

import communication.messages.Message;
import communication.messages.OtherPlayerMovedMessage;
import model.QualifiedObservableReport;
import model.reports.PlayerMovedReport;

/**
 * Takes the information given to us when MovementNotifier updates and
 * translates it to the appropriate MovementMessage. It turns out this is pretty
 * trivial since the MovementNotifier pushes a MovementMessage
 *
 * @author merlin
 *
 */
public class OtherPlayerMovedMessagePacker extends MessagePacker
{

	/**
	 * Generates a MovementMessage for a PlayerMovedReport that not associated
	 * with the player in the accumulator.
	 *
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass().equals(PlayerMovedReport.class))
		{
			PlayerMovedReport report = (PlayerMovedReport) object;
			int playerID = report.getPlayerID();
			if (this.getAccumulator().getPlayerID() != playerID)
			{
				OtherPlayerMovedMessage msg = new OtherPlayerMovedMessage(playerID, report.getNewPosition());
				return msg;
			}
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
		result.add(PlayerMovedReport.class);
		return result;
	}

}
