package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * Used to log a player into a server
 *
 * @author merlin
 */
public class StubMessage2 extends Message implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public StubMessage2(int relevantPlayerID)
    {
        super(relevantPlayerID);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "This is a stub message (2) - why are you outputting it!";
    }

}
