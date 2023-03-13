package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.Position;


import javax.management.ConstructorParameters;
import java.io.Serializable;
import java.util.Objects;

/**
 * Encodes the fact that a player has moved to a given location
 *
 * @author merlin
 */

public class PlayerMovedMessage extends Message implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Position position;

    public PlayerMovedMessage(int relevantPlayerID, boolean quietMessage, Position position)
    {
        super(relevantPlayerID, quietMessage);
        this.position = position;
    }

    public Position getPosition()
    {
        return position;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof PlayerMovedMessage))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        PlayerMovedMessage that = (PlayerMovedMessage) o;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), position);
    }

    @Override
    public String toString()
    {
        return "Movement Message: " +
                "relevantPlayerID = " + relevantPlayerID +
                ", position = " + position
                ;
    }
}
