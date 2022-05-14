package communication.handlers;

import communication.messages.ChatMessageToClient;
import communication.messages.ChatMessageToServer;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandChatMessageReceivedFromServer;

/**
 * Should process an incoming ChatMessage that is reporting that a message was sent by a Player
 *
 * @author Frank Schmidt
 *
 */
public class ClientChatMessageHandler extends MessageHandler
{

	/**
	 * Passes a ChatMessage to the ChatMessageReceivedCommand
	 *
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(ChatMessageToClient.class))
		{
			ChatMessageToClient chatMessage = (ChatMessageToClient) msg;
			CommandChatMessageReceivedFromServer cmd =
					new CommandChatMessageReceivedFromServer(chatMessage.getSenderID(),
							chatMessage.getReceiverID(), chatMessage.getChatText(),
					chatMessage.getPosition(), chatMessage.getType());
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}

	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return ChatMessageToClient.class;
	}

}
