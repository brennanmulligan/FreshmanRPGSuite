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
	protected Interaction interaction;
	protected ArrayList<MessageFlow> messageSequence;

	/**
	 * The list of server ids this sequence needs to run on
	 */
	protected ArrayList<ServerType> serverList = new ArrayList<>();

	/**
	 * @return the command that will initiate the sequence
	 */
	public final Command getInitiatingCommand()
	{
			return interaction.getInitiatingCommand();
	}

	/**
	 * @return the type of server where the initiating command is run
	 */
	public final ServerType getInitiatingServerType()
	{
		return interaction.getInitiatingServerType();
	}

	/**
	 * @return the sequence of message flows that define the protocol
	 */
	public final ArrayList<MessageFlow> getMessageSequence()
	{
		return interaction.getMessageSequence();
	}
	
	/**
	 * 
	 * @return the server numbers for this sequence to run on
	 */
	public final ArrayList<ServerType> getServerList()
	{
		return serverList;
	}

	/**
	 * @return the player ID of the player that is initiating this sequence
	 */
	public final int getInitiatingPlayerID()
	{
		return interaction.getInitiatingPlayerID();
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
