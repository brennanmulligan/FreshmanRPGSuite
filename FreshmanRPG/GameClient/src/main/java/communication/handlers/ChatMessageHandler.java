package communication.handlers;

import communication.messages.ChatMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandChatMessageReceived;

/**
 * Should process an incoming ChatMessage that is reporting that a message was sent by a Player
 *
 * @author Frank Schmidt
 *
 */
public class ChatMessageHandler extends MessageHandler
{

	/**
	 * Passes a ChatMessage to the ChatMessageReceivedCommand
	 *
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(ChatMessage.class))
		{
			ChatMessage chatMessage = (ChatMessage) msg;
			CommandChatMessageReceived cmd = new CommandChatMessageReceived(chatMessage.getSenderID(), chatMessage.getReceiverID(), chatMessage.getChatText(),
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
		return ChatMessage.class;
	}

}
