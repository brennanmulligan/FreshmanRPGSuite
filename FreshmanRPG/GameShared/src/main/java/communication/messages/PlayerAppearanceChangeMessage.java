package communication.messages;

import dataDTO.VanityDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rh5172
 *
 */
public class PlayerAppearanceChangeMessage implements Message, Serializable
{
	private static final long serialVersionUID = 1L;

	private ArrayList<VanityDTO> vanities;

	private int playerID;


	/**
	 * @param playerID ID of the player
	 * @param vanities the list of vanity objects the player is wearing
	 */
	public PlayerAppearanceChangeMessage(int playerID, VanityDTO bodyDTO, VanityDTO hatDTO)
	{
		this.vanities = new ArrayList<>(vanities);
		this.playerID = playerID;
	}

	public List<VanityDTO> getVanities()
	{
		return vanities;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}


	/**
	 * Creates a string that will be displayed by the message
	 * @return The message to print out what happened and what the appearanceType is now
	 */
	public String toString()
	{
		return "Appearance Change: Appearance changed to: " + "AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH fix this";
	}


}
