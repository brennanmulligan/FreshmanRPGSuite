package edu.ship.engr.shipsim.model;

import java.io.IOException;

/**
 * @author merlin
 */
public abstract class Command implements InfoPacket
{

    /**
     * perform the action associated with this command
     */
    abstract void execute();

    /**
     * @return true if the command should dump the queue of other commands after
     * it executes
     */
    protected boolean doDump()
    {
        return false;
    }

}
