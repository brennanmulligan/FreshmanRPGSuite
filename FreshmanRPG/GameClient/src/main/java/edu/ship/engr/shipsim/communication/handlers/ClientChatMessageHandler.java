package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ChatMessageToClient;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandChatMessageReceivedFromServer;

/**
 * Should process an incoming ChatMessage that is reporting that a message was sent by a Player
 *
 * @author Frank Schmidt
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
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return ChatMessageToClient.class;
    }

}
