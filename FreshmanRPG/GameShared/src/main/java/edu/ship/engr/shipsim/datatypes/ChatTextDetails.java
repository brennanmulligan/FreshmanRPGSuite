package edu.ship.engr.shipsim.datatypes;

/**
 * @author Adam Pine
 * Class to hold a message, and it's specified type in one object
 */
public class ChatTextDetails
{
    private String message;
    private ChatType msgType;

    /**
     * Constructor
     *
     * @param msg     - the message text
     * @param msgType - the type of the message.
     */
    public ChatTextDetails(String msg, ChatType msgType)
    {
        this.message = msg;
        this.msgType = msgType;
    }

    /**
     * get the message text
     *
     * @return the message text
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Get the message type
     *
     * @return the message type
     */
    public ChatType getMsgType()
    {
        return msgType;
    }

    /**
     * @param message the message that was/will be sent
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * @param msgType the type of the message
     */
    public void setMsgType(ChatType msgType)
    {
        this.msgType = msgType;
    }
}
