package model;

import datasource.DatabaseException;

/**
 * Command to change the player's appearance type.
 */
public class CommandChangePlayerAppearance extends Command
{
	private int playerID;
	private String appearanceType;

	/**
	 * Construct and initialize a CommandChangePlayerAppearance.
	 *
	 * @param playerId - the player ID
	 * @param appearanceType - the appearance type we want to change to
	 */
	public CommandChangePlayerAppearance(int playerId, String appearanceType)
	{
		this.playerID = playerId;
		this.appearanceType = appearanceType;
	}

	/**
	 * Execute the command.
	 *
	 * @return true if successful
	 */
	@Override
	boolean execute()
	{
		try
		{
			PlayerManager.getSingleton().editPlayerAppearance(playerID, appearanceType);
		}
		catch (DatabaseException e)
		{
			System.err.println("Failure while updateing players appearance. DatabaseException.");
			e.printStackTrace();
		}
		catch (IllegalQuestChangeException e)
		{
			System.err.println("Failure while updateing players appearance. IllegalQuestChangeException.");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @return player id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return player appearance
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

}
