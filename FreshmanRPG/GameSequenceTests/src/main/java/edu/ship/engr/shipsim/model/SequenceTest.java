package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;

import java.util.ArrayList;

/**
 * Defines what is required to model one message in a message protocol sequence
 *
 * @author Merlin
 */
public abstract class SequenceTest
{
    protected ArrayList<Interaction> interactions = new ArrayList<>();
    /**
     * The list of server ids this sequence needs to run on
     */
    protected ArrayList<ServerType> serverList = new ArrayList<>();

    /**
     * @return the server numbers for this sequence to run on
     */
    public final ArrayList<ServerType> getServerList()
    {
        //noinspection unchecked
        return (ArrayList<ServerType>) serverList.clone();
    }

    /**
     * Reset any gateways this test has changed so that more tests can be run
     */
    public abstract void resetNecessarySingletons();

    /**
     * Set up anything in the singletons (like OptionsManager) that is required
     * by this test
     *
     * @throws DatabaseException if Player Manager goofs
     */
    public abstract void setUpMachines() throws DatabaseException;

    protected ArrayList<Interaction> getInteractions()
    { //noinspection unchecked
        return (ArrayList<Interaction>) interactions.clone();
    }

}
