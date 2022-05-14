package communication.packers;

import java.util.ArrayList;

import communication.messages.ChatMessageToClient;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.ChatMessageToClientReport;

/**
 * @author Dave
 *
 *         Packs up information from the ChatManager into a ChatMessage to be
 *         sent to the server.
 */
public class ChatMessageToClientPacker extends MessagePacker
{

	/**
	 * @param object A SendChatMessageReport to be translated into a ChatMessage
	 * @return A ChatMessage based on the SendChatMessageReport that was given.
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{

		if (object.getClass() != ChatMessageToClientReport.class)
		{
			throw new IllegalArgumentException("ChatMessageToClientPacker cannot pack " +
					"messages of type " + object.getClass());
		}

		ChatMessageToClientReport report = (ChatMessageToClientReport) object;
		ChatMessageToClient msg = new ChatMessageToClient(report.getSenderID(), report.getReceiverID(), report.getChatText(), report.getPosition(),
				report.getType());

		return msg;
	}

	/**
	 * This packer listens for ChatMessageReceivedReport
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(ChatMessageToClientReport.class);
		return result;
	}

}
