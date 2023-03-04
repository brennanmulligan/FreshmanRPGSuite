package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;

/**
 * Carries information from a chat message that should be sent to all clients.
 *
 * @author Dave
 */
public final class ChatMessageToClientReport extends SendMessageReport
{
    private final String text;
    private final int senderID;
    private final int receiverID;
    private final Position location;
    private final ChatType type;

    /**
     * @param senderID       The name of the player who sent the message
     * @param messageContent The text of the message
     * @param pos            The position of the player when they sent the message
     * @param type           The type of chat message this is
     */
    public ChatMessageToClientReport(int senderID, int receiverID, String messageContent, Position pos, ChatType type)
    {
        // Happens on client, thus it will always be loud
        super(0, false);
        this.text = messageContent;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.location = pos;
        this.type = type;
    }

    /**
     * @return The position of the player when they sent the message
     */
    public Position getPosition()
    {
        return location;
    }

    /**
     * @return The name of the player who sent the message
     */
    public int getSenderID()
    {
        return senderID;
    }

    /**
     * @return The text of the message
     */
    public String getChatText()
    {
        return text;
    }

    /**
     * @return The type of chat message this is
     */
    public ChatType getType()
    {
        return type;
    }

    /**
     * @return the player who receives the text
     */
    public int getReceiverID()
    {
        return receiverID;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + receiverID;
        result = prime * result + senderID;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
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
        ChatMessageToClientReport other = (ChatMessageToClientReport) obj;
        if (location == null)
        {
            if (other.location != null)
            {
                return false;
            }
        }
        else if (!location.equals(other.location))
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
        if (text == null)
        {
            if (other.text != null)
            {
                return false;
            }
        }
        else if (!text.equals(other.text))
        {
            return false;
        }
        if (type != other.type)
        {
            return false;
        }
        return true;
    }


}
