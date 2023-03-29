package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;

import java.io.Serializable;

/**
 * Create a ChatMessage that is used for sending messages.
 */
public class ChatMessageToClient extends Message implements Serializable
{


    private static final long serialVersionUID = 1L;
    private final String chatText;
    private final Position position;
    private final ChatType type;
    private int senderID;

    /**
     * Create a chat message
     * @param receiverID this is the relevant player for this message
     * @param senderID user sending the message
     * @param chatText the message to be sent
     * @param location the location of the sender
     * @param type     the type of chat message being sent
     */
    public ChatMessageToClient(int senderID, int receiverID, boolean quietMessage, String chatText, Position location, ChatType type)
    {
        super(receiverID, quietMessage);
        this.chatText = chatText;
        this.senderID = senderID;
        this.position = location;
        this.type = type;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }

        ChatMessageToClient that = (ChatMessageToClient) o;

        if (senderID != that.senderID)
        {
            return false;
        }
        if (!chatText.equals(that.chatText))
        {
            return false;
        }
        if (!position.equals(that.position))
        {
            return false;
        }
        return type == that.type;
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
        return relevantPlayerID;
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
        result = prime * result + relevantPlayerID;
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
