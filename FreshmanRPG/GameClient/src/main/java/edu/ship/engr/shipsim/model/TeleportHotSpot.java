package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.Position;

/**
 * Hotspots are formatted as
 * [key] x y
 * [value] mapfile x y
 * within the TMX file and parsed out into a Position and MapName
 *
 * @author Frank
 */
public class TeleportHotSpot
{
    final private String mapName;
    final private Position teleportPosition;

    /**
     * @param mapName  the name of the map to teleport to
     * @param position the position on the map to teleport to
     */
    public TeleportHotSpot(String mapName, Position position)
    {
        this.mapName = mapName;
        this.teleportPosition = position;
    }

    /**
     * @return mapName
     * the name of the map
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * @return teleportPosition
     * the position to teleport to
     */
    public Position getTeleportPosition()
    {
        return teleportPosition;
    }

}
