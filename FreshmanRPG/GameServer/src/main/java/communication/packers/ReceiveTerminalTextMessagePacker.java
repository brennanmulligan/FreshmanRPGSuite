package communication.packers;

import java.util.ArrayList;

import communication.messages.ReceiveTerminalTextMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.ReceiveTerminalTextReport;

/**
 *
 * @author Denny Fleagle
 * @author Chris Roadcap
 * @author Kanza Amin
 *
 */

public class ReceiveTerminalTextMessagePacker extends MessagePacker
{

	/**
	 * pack the report to a message
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass() != ReceiveTerminalTextReport.class)
		{
			throw new IllegalArgumentException(
					"ReceiveTerminalTextMessagePacker cannot pack messages of type" + object.getClass());
		}

		ReceiveTerminalTextReport report = (ReceiveTerminalTextReport) object;


		int playerID = report.getPlayerID();
		String terminalText = report.getResultText();
		if (this.getAccumulator().getPlayerID() == playerID)
		{
			ReceiveTerminalTextMessage msg = new ReceiveTerminalTextMessage(playerID, terminalText);
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
		result.add(ReceiveTerminalTextReport.class);
		return result;
	}

}
