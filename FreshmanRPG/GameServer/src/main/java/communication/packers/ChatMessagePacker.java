package communication.packers;

import java.util.ArrayList;

import communication.messages.ChatMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.ChatMessageReceivedReport;

/**
 * @author Dave
 *
 *         Packs up information from the ChatManager into a ChatMessage to be
 *         sent to the server.
 */
public class ChatMessagePacker extends MessagePacker
{

	/**
	 * @param object A SendChatMessageReport to be translated into a ChatMessage
	 * @return A ChatMessage based on the SendChatMessageReport that was given.
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{

		if (object.getClass() != ChatMessageReceivedReport.class)
		{
			throw new IllegalArgumentException("ChatMessagePacker cannot pack messages of type " + object.getClass());
		}

		ChatMessageReceivedReport report = (ChatMessageReceivedReport) object;
		ChatMessage msg = new ChatMessage(report.getSenderID(), report.getReceiverID(), report.getChatText(), report.getPosition(),
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
		result.add(ChatMessageReceivedReport.class);
		return result;
	}

}
