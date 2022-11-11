package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Derek
 */
public class RestfulLoginSuccessMessage implements Message, Serializable
{
    // We have this because we need it :)
    private static final long serialVersionUID = 1L;

    private final int playerID;

    public RestfulLoginSuccessMessage(int playerID)
    {
        this.playerID = playerID;
    }

    public int getPlayerID()
    {
        return playerID;
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

        return Objects.equals(playerID, otherMessage.getPlayerID());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerID);
    }
}
