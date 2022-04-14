package model;

import dataDTO.VanityDTO;
import datasource.DatabaseException;

import java.util.ArrayList;

/**
 * Command to change the player's appearance type.
 */
public class CommandChangePlayerAppearance extends Command
{
	private int playerID;
	private ArrayList<VanityDTO> newWearing;

	/**
	 * Construct and initialize a CommandChangePlayerAppearance.
	 *
	 * @param playerId - the player ID
	 */
	public CommandChangePlayerAppearance(int playerId, ArrayList<VanityDTO> newWearing)
	{
		this.playerID = playerId;
		this.newWearing = newWearing;
	}

	/**
	 * Execute the command.
	 *
	 * @return true if successful
	 */
	@Override
	boolean execute()
	{
		Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);
		player.setVanityItems(newWearing);
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
	 * @return the list of new things to wear
	 */
	public ArrayList<VanityDTO> getNewWearing()
	{
		return newWearing;
	}
}
