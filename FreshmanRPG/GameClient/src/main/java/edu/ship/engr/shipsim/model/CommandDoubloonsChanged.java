package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;

/**
 * @author Matthew Croft
 * @author Evan Stevenson
 */
public class CommandDoubloonsChanged extends Command
{


    private int doubloons;
    private int playerID;
    private int buffPool;

    /**
     * constructor for the command
     *
     * @param msg InitializeClientsPlayerMessage which contains all the data to
     *            initialize this client player
     */
    public CommandDoubloonsChanged(InitializeThisClientsPlayerMessage msg)
    {
        this.doubloons = msg.getDoubloons();
    }


    /**
     * @param playerID  of the player
     * @param doubloons of the player
     * @param buffPool  of the player
     */
    public CommandDoubloonsChanged(int playerID, int doubloons, int buffPool)
    {
        this.doubloons = doubloons;
        this.playerID = playerID;
        this.buffPool = buffPool;
    }

    /**
     * @return the doubloons for this player
     */
    public int getDoubloons()
    {
        return doubloons;
    }

    /**
     * @return the playerID for this player
     */
    public int getPlayerID()
    {
        return playerID;
    }

    @Override
    boolean execute()
    {
        ThisClientsPlayer thisClientsPlayer = ClientPlayerManager.getSingleton().getThisClientsPlayer();
        thisClientsPlayer.doubloonsAndBuffPoolChanged(doubloons, buffPool);
        return true;
    }

}
