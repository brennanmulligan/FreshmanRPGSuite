package edu.ship.engr.shipsim.model;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

/**
 * Command the puts a new player in the system when that new player joins our
 * area server.
 *
 * @author merlin
 */
public class CommandFinishLogin extends Command
{

    private final int playerID;


    /**
     * @param playerID the unique player name of the new player
     */
    public CommandFinishLogin(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        try
        {
            ClientPlayerManager.getSingleton().finishLogin(playerID);
        }
        catch (AlreadyBoundException | NotBoundException e)
        {
            e.printStackTrace();
        }
    }
}
