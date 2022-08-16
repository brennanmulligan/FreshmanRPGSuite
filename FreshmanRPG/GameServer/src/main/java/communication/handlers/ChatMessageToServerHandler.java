package communication.handlers;

import communication.messages.ChatMessageToClient;
import communication.messages.ChatMessageToServer;
import communication.messages.Message;
import model.CommandChatMessageReceived;
import model.ModelFacade;

/**
 * Handles ChatMessages from the client and creates the command to change the
 * state of the model
 *
 * @author Josh
 */
public class ChatMessageToServerHandler extends MessageHandler
{

	/**
	 * Process the incoming message by creating a ChatMessageReceivedCommand
	 *
	 * @param msg the incoming ChatMessage
	 */
	@Override
	public void process(Message msg)
	{

		if (msg.getClass().equals(ChatMessageToServer.class))
		{
			ChatMessageToServer cMsg = (ChatMessageToServer) msg;

			CommandChatMessageReceived cmd = new CommandChatMessageReceived(cMsg.getSenderID(), cMsg.getReceiverID(), cMsg.getChatText(),
					cMsg.getPosition(), cMsg.getType());

			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @return Returns the type of Message that this Handler deals with
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return ChatMessageToServer.class;
	}
}