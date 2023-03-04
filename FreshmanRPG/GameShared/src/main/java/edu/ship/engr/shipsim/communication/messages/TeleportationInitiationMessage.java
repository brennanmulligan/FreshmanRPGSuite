package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.Position;

import java.io.Serializable;

/**
 * A message that allows a client to ask for the server & port number that are
 * managing a given map (tmx file)
 *
 * @author Merlin
 */
public class TeleportationInitiationMessage extends Message implements Serializable
{

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
        result = prime * result + relevantPlayerID;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
        TeleportationInitiationMessage other = (TeleportationInitiationMessage) obj;
        if (mapName == null)
        {
            if (other.mapName != null)
            {
                return false;
            }
        }
        else if (!mapName.equals(other.mapName))
        {
            return false;
        }
        if (relevantPlayerID != other.relevantPlayerID)
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
        return true;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String mapName;
    private Position position;

    /**
     * Create the request message
     *
     * @param playerID    the player that is making this move
     * @param mapName     the name of the tmx file of the map we are interested in
     * @param newPosition the position in which the player should be placed when
     *                    the move is complete
     */
    public TeleportationInitiationMessage(int playerID, boolean quietMessage, String mapName, Position newPosition)
    {
        super(playerID, quietMessage);
        this.mapName = mapName;
        this.position = newPosition;
    }

    /**
     * Get the name of the map we are interested in
     *
     * @return the map name
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "TeleportationInitiationMessage: playerID = " + relevantPlayerID + " mapName = " + mapName + " position = "
                + position;
    }

    /**
     * Get the position the player should be in in the new map
     *
     * @return the position
     */
    public Position getPosition()
    {
        return position;
    }
}
