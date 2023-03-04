package edu.ship.engr.shipsim.communication.messages;



import java.io.Serializable;
import java.util.Objects;

/**
 * All messages extend the abstract class Message now.
 * All messages will use the inherited getRelevantPlayerID and equals/hashcode.
 * There are two constructors because some messages need an empty version of the
 * message class and other need a constructor that uses relevantPlayerID as the
 * parameter.
 *
 * @author Evan Paules and Yong Hang Lin
 */

public abstract class Message implements Serializable
{
    int relevantPlayerID;
    boolean quietMessage;

    public boolean isQuietMessage()
    {
        return quietMessage;
    }

    public Message(int relevantPlayerID, boolean quietMessage)
    {
        this.relevantPlayerID = relevantPlayerID;
        this.quietMessage = quietMessage;
    }

    public int getRelevantPlayerID()
    {
        return relevantPlayerID;
    }

    public Message(int relevantPlayerID)
    {
        this.relevantPlayerID = relevantPlayerID;
    }

    public Message()
    {

    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Message))
        {
            return false;
        }
        Message message = (Message) o;
        return relevantPlayerID == message.relevantPlayerID;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(relevantPlayerID);
    }
}
