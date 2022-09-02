package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;

import java.io.Serializable;

/**
 * Create a ChatMessage that is used for sending messages.
 */
public class ChatMessageToClient implements Message, Serializable
{


    private static final long serialVersionUID = 1L;
    private final String chatText;
    private final Position position;
    private final ChatType type;
    private int senderID;
    private int receiverID;

    /**
     * Create a chat message
     *
     * @param senderID user sending the message
     * @param chatText the message to be sent
     * @param location the location of the sender
     * @param type     the type of chat message being sent
     */
    public ChatMessageToClient(int senderID, int receiverID, String chatText, Position location, ChatType type)
    {
        this.chatText = chatText;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.position = location;
        this.type = type;
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
        ChatMessageToClient other = (ChatMessageToClient) obj;
        if (chatText == null)
        {
            if (other.chatText != null)
            {
                return false;
            }
        }
        else if (!chatText.equals(other.chatText))
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


    /**
     * @return the text the player is sending
     */
    public String getChatText()
    {
        return chatText;
    }


    /**
     * @return the ID of the player sending the text
     */
    public int getSenderID()
    {
        return senderID;
    }

    /**
     * @return the ID of the player who is receiving the text
     */
    public int getReceiverID()
    {
        return receiverID;
    }

    /**
     * @return the location of the sender
     */
    public Position getPosition()
    {
        return this.position;
    }

    /**
     * @return the type of chat message being sent
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
        result = prime * result + ((chatText == null) ? 0 : chatText.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + receiverID;
        result = prime * result + senderID;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "ChatMessageToClient [chatText=" + chatText + ", position=" + position +
                ", type=" + type + ", playerID="
                + senderID + "]";
    }

}
