package model;

import java.util.ArrayList;
import datasource.DatabaseException;

/**
 * Defines what is required to model one message in a message protocol sequence
 * 
 * @author Merlin
 *
 */
public abstract class SequenceTest
{
	protected ArrayList<Interaction> interactions = new ArrayList<>();

	protected ArrayList<Interaction> getInteractions() { //noinspection unchecked
		return (ArrayList<Interaction>) interactions.clone(); }

	/**
	 * The list of server ids this sequence needs to run on
	 */
	protected ArrayList<ServerType> serverList = new ArrayList<>();

	/**
	 *
	 * @return the server numbers for this sequence to run on
	 */
	public final ArrayList<ServerType> getServerList()
	{
		//noinspection unchecked
		return (ArrayList<ServerType>) serverList.clone();
	}

	/**
	 * Set up anything in the singletons (like OptionsManager) that is required
	 * by this test
	 * @throws DatabaseException if Player Manager goofs
	 */
	public abstract void setUpMachines() throws DatabaseException;

	/**
	 * Reset any gateways this test has changed so that more tests can be run
	 */
	public abstract void resetNecessarySingletons();

}
