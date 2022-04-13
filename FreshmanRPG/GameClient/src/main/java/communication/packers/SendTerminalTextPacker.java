package communication.packers;

import java.util.ArrayList;

import communication.messages.SendTerminalTextMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.SendTerminalTextReport;

/**
 * @author bl5922 SendTerminalTextPacker that looks for SendTerminalTextReport and
 *         creates a OnlinePlayersMessage to send to the server
 */
public class SendTerminalTextPacker extends MessagePacker
{

	/**
	 * @param object
	 *            A SendTerminalTextReport to be translated into a OnlinePlayersMessage
	 * @return A OnlinePlayersMessage based on the SendTerminalTextReport that was
	 *         given.
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass() != SendTerminalTextReport.class)
		{
			throw new IllegalArgumentException("ChatMessagePacker cannot pack messages of type " + object.getClass());
		}

		SendTerminalTextReport report = (SendTerminalTextReport) object;
		SendTerminalTextMessage msg = new SendTerminalTextMessage(report.getPlayerID(), report.getTerminalText());

		return msg;

	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack() This packer
	 *      listens for SendTerminalTextReport
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(SendTerminalTextReport.class);
		return result;
	}

}
