package edu.ship.engr.shipsim.model;

/**
 * Command that will trigger a players quest.
 *
 * @author Ethan
 */
public class CommandAreaCollision extends Command
{
    /**
     * Constructor for CommandAreaCollision
     *
     * @param playerID the players ID
     * @param areaName the areaName the player collided with.
     */
    public CommandAreaCollision(int playerID, String areaName)
    {
    }

    /**
     * Will trigger the quest for a player.
     */
    @Override
    boolean execute()
    {
        return true;
    }

    /**
     * Gets the ID of the player that collided with the area.
     *
     * @return the players ID
     */
    public int getPlayerID()
    {
        return 0;
    }

    /**
     * Gets the name of the area the player collided with.
     *
     * @return the name of the area
     */
    public String getAreaName()
    {
        return "";
    }

}
