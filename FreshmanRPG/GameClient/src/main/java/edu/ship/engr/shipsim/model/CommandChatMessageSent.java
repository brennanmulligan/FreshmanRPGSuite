package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.ChatTextDetails;
import edu.ship.engr.shipsim.datatypes.ChatType;

/**
 * @author Matthew Kujawski
 * <p>
 * Receives the message parameters and passes them to the ChatManager
 */
public class CommandChatMessageSent extends Command
{
    private final String message;
    private final ChatType type;
    private final boolean isValidMessage;
    private int receiverID;


    /**
     * Constructor to use, with ChatUIMessage
     *
     * @param cm - Chat message to send.
     */
    public CommandChatMessageSent(ChatTextDetails cm)
    {
        isValidMessage = true;
        this.message = cm.getMessage();
        this.type = cm.getMsgType();
    }

    /**
     * Constructor that adds a receiver id for sending private messages
     *
     * @param cm         - chat message to send
     * @param receiverID - user to send message to
     */
    public CommandChatMessageSent(ChatTextDetails cm, int receiverID)
    {
        isValidMessage = true;
        this.message = cm.getMessage();
        this.type = cm.getMsgType();
        this.receiverID = receiverID;
    }

    /**
     * @return the message that was entered by the player
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @return the type of message
     */
    public ChatType getType()
    {
        return type;
    }

    public int getReceiverID()
    {
        return receiverID;
    }

    /**
     * @return True if the literal String given to this command was formatted
     * correctly with respect to message type
     */
    public boolean getValidity()
    {
        return isValidMessage;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        ClientChatManager.getSingleton().sendChatToServer(message, type, receiverID);
    }
}
