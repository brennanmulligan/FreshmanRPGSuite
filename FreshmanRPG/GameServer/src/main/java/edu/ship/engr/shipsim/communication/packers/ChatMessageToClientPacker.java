package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.ChatMessageToClient;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.ChatMessageToClientReport;

import java.util.ArrayList;

/**
 * @author Dave
 * <p>
 * Packs up information from the ChatManager into a ChatMessage to be
 * sent to the server.
 */
public class ChatMessageToClientPacker extends MessagePacker
{

    /**
     * @param object A SendChatMessageReport to be translated into a ChatMessage
     * @return A ChatMessage based on the SendChatMessageReport that was given.
     */
    @Override
    public Message pack(Report object)
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
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result = new ArrayList<>();
        result.add(ChatMessageToClientReport.class);
        return result;
    }

}
