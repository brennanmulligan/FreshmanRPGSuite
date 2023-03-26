package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.math.Vector2;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.VanityType;

import java.util.List;

/**
 * Facing direction of the sprite
 */
public enum Direction
{
    /**
     * Looking down the screen
     */
    South(List.of(VanityType.BODY, VanityType.PANTS, VanityType.SHOES, VanityType.EYES, VanityType.HAIR, VanityType.SHIRT, VanityType.HAT, VanityType.NAMEPLATE, VanityType.BIKE)),
    /**
     * Looking left on the screen
     */
    West(List.of(VanityType.BODY, VanityType.PANTS, VanityType.SHOES, VanityType.SHIRT, VanityType.EYES, VanityType.HAIR, VanityType.HAT, VanityType.NAMEPLATE, VanityType.BIKE)),
    /**
     * Looking right on the screen
     */
    East(List.of(VanityType.BODY, VanityType.PANTS, VanityType.SHOES, VanityType.SHIRT, VanityType.EYES, VanityType.HAIR, VanityType.HAT, VanityType.NAMEPLATE, VanityType.BIKE)),
    /**
     * Looking up the screen
     */
    North(List.of(VanityType.EYES, VanityType.BODY, VanityType.PANTS, VanityType.SHOES, VanityType.SHIRT, VanityType.HAIR, VanityType.HAT, VanityType.NAMEPLATE, VanityType.BIKE));

    List<VanityType> vanityOrder;

    Direction(List<VanityType> vanityOrder)
    {
        this.vanityOrder = vanityOrder;
    }

    public List<VanityType> getVanityOrder()
    {
        return vanityOrder;
    }

    /**
     * Get the direction of facing between two points
     *
     * @param from source position
     * @param to   destination position
     * @return the direction of facing between point a to point b
     */
    public static Direction getFacing(Vector2 from, Vector2 to)
    {
        Direction out = North;
        float diffX = Math.abs(to.x - from.x);
        float diffY = Math.abs(to.y - from.y);

        if (diffX >= diffY)
        {

            if (from.x > to.x)
            {
                out = West;
            }
            else
            {
                out = East;
            }

        }
        else
        {
            if (from.y < to.y)
            {
                out = North;
            }
            else
            {
                out = South;
            }
        }

        return out;
    }

    /**
     * Get the next step from a position in a direction
     *
     * @param p   starting position
     * @param dir direction to walk
     * @return next position
     */
    public static Position getPositionInDirection(Position p, Direction dir)
    {
        Position next = p;
        switch (dir)
        {
            case North:
                next = new Position(p.getRow() - 1, p.getColumn());
                break;
            case South:
                next = new Position(p.getRow() + 1, p.getColumn());
                break;
            case East:
                next = new Position(p.getRow(), p.getColumn() + 1);
                break;
            case West:
                next = new Position(p.getRow(), p.getColumn() - 1);
                break;
        }
        return next;

    }
}
