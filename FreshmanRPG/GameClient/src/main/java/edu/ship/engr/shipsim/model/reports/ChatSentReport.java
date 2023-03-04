package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;

/**
 * Report for when a chat message is sent
 *
 * @author Steve
 */
public class ChatSentReport extends SendMessageReport
{
    private String message;
    private int senderID;
    private int receiverID;
    private Position position;
    private ChatType type;

    /**
     * @param senderID Who sent the message
     * @param message  The message we received
     * @param position Location of sender
     * @param type     What type of message this is
     */
    public ChatSentReport(int senderID, int receiverID, String message, Position position, ChatType type)
    {
        // Happens on client, thus it will always be loud
        super(0, false);
        this.message = message;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.position = position;
        this.type = type;
    }


    /**
     * @return The message we received
     */
    public String getMessage()
    {
        return message;
    }


    /**
     * @return Who sent the message
     */
    public int getSenderID()
    {
        return senderID;
    }


    /**
     * @return Location of sender
     */
    public Position getPosition()
    {
        return position;
    }


    /**
     * @return What type of message this is
     */
    public ChatType getType()
    {
        return type;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + receiverID;
        result = prime * result + senderID;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        ChatSentReport other = (ChatSentReport) obj;
        if (message == null)
        {
            if (other.message != null)
            {
                return false;
            }
        }
        else if (!message.equals(other.message))
        {
            return false;
        }
        if (position == null)
        {
            if (other.position != null)
            {
                return false;
            }
        }
        else if (!position.equals(other.position))
        {
            return false;
        }
        if (receiverID != other.receiverID)
        {
            return false;
        }
        if (senderID != other.senderID)
        {
            return false;
        }
        if (type != other.type)
        {
            return false;
        }
        return true;
    }


    public int getReceiverID()
    {
        return receiverID;
    }

}
