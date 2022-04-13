package model;

import datasource.DatabaseException;

/**
 * Command to add a player
 * @author Darin Alleman / Darnell Martin
 *
 */
public class CommandAddPlayerInManager extends Command
{
	private String name;
	private String password;
	private int crew;
	private int major;
	private int sectionID;


	/**
	 * Construct the command to add a player
	 * @param name - Name of the player
	 * @param password - password for the new player
	 * @param crew - crew for the new player
	 * @param major - major for the new player
	 * @param sectionID - section ID for the new player
	 */
	public CommandAddPlayerInManager(String name, String password, int crew, int major, int sectionID)
	{
		this.name = name;
		this.password = password;
		this.crew = crew;
		this.major = major;
		this.sectionID = sectionID;
	}

	/**
	 * Add the player with the player manager
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerPlayerManager manager = GameManagerPlayerManager.getInstance();
			manager.addPlayer(this.name, this.password, this.crew, this.major, this.sectionID);
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return true;
	}
}
