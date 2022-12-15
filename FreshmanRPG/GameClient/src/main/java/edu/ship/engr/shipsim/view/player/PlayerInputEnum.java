package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.Input.Keys;

/**
 * @author Ryan Handley and Darnell Martin
 */
public enum PlayerInputEnum
{
    /**
     *
     */
    UP(Keys.UP),
    /**
     *
     */
    DOWN(Keys.DOWN),
    /**
     *
     */
    LEFT(Keys.LEFT),
    /**
     *
     */
    RIGHT(Keys.RIGHT),
    /**
     *
     */
    W(Keys.W),
    /**
     *
     */
    A(Keys.A),
    /**
     *
     */
    S(Keys.S),
    /**
     *
     */
    D(Keys.D),
    /**
     *
     */
    SHIFT_LEFT(Keys.SHIFT_LEFT);

    private int key;

    PlayerInputEnum(int key)
    {
        this.key = key;
    }

    /**
     * This method is a non-static way to get the direction of a key.
     *
     * @return the direction of the specific key.
     */
    public Direction getDirection()
    {
        switch (this.key)
        {
            case Keys.UP:
                return Direction.North;
            case Keys.DOWN:
                return Direction.South;
            case Keys.LEFT:
                return Direction.West;
            case Keys.RIGHT:
                return Direction.East;
            case Keys.W:
                return Direction.North;
            case Keys.S:
                return Direction.South;
            case Keys.A:
                return Direction.West;
            case Keys.D:
                return Direction.East;
            default:
                return null;
        }
    }

    /**
     * Static method for finding the direction of a key.
     *
     * @param key the keycode that will correspond with a direction
     * @return the direction the player will face after directional input
     */
    public static Direction getDirection(int key)
    {
        switch (key)
        {
            case Keys.UP:
                return Direction.North;
            case Keys.DOWN:
                return Direction.South;
            case Keys.LEFT:
                return Direction.West;
            case Keys.RIGHT:
                return Direction.East;
            case Keys.W:
                return Direction.North;
            case Keys.S:
                return Direction.South;
            case Keys.A:
                return Direction.West;
            case Keys.D:
                return Direction.East;
            default:
                return null;
        }
    }

}

