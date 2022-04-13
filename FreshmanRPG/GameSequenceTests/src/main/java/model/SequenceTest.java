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

	/**
	 * The constructor of the subclass must fill this with the sequence of
	 * message flows for the test
	 */
	protected ArrayList<MessageFlow> messageSequence = new ArrayList<>();
	
	/**
	 * The list of server ids this sequence needs to run on
	 */
	protected ArrayList<ServerType> serverList = new ArrayList<>();

	/**
	 * @return the command that will initiate the sequence
	 */
	public abstract Command getInitiatingCommand();

	/**
	 * @return the type of server where the initiating command is run
	 */
	public abstract ServerType getInitiatingServerType();

	/**
	 * @return the sequence of message flows that define the protocol
	 */
	public ArrayList<MessageFlow> getMessageSequence()
	{
		return messageSequence;
	}
	
	/**
	 * 
	 * @return the server numbers for this sequence to run on
	 */
	public ArrayList<ServerType> getServerList()
	{
		return serverList;
	}

	/**
	 * @return the player ID of the player that is initiating this sequence
	 */
	public abstract int getInitiatingPlayerID();

	/**
	 * Set up anything in the singletons (like OptionsManager) that is required
	 * by this test
	 * @throws DatabaseException if Player Manager goofs
	 */
	public abstract void setUpMachines() throws DatabaseException;

	/**
	 * Reset any gateways this test has changed so that more tests can be run
	 */
	public abstract void resetDataGateways();

}
