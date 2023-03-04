package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Derek
 */
public class RestfulLoginSuccessMessage extends Message implements Serializable
{
    // We have this because we need it :)
    private static final long serialVersionUID = 1L;

    public RestfulLoginSuccessMessage(int playerID, boolean quietMessage)
    {
        super(playerID, quietMessage);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null)
        {
            return false;
        }

        if (getClass() != o.getClass())
        {
            return false;
        }

        RestfulLoginSuccessMessage otherMessage = (RestfulLoginSuccessMessage) o;

        return Objects.equals(relevantPlayerID, otherMessage.getRelevantPlayerID());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(relevantPlayerID);
    }
}
