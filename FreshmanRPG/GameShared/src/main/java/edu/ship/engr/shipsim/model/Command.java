package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author merlin
 */
public abstract class Command implements InfoPacket, Serializable
{

    /**
     * perform the action associated with this command
     */
    abstract void execute() throws DuplicateNameException, DatabaseException;

    /**
     * @return true if the command should dump the queue of other commands after
     * it executes
     */
    protected boolean doDump()
    {
        return false;
    }

}
