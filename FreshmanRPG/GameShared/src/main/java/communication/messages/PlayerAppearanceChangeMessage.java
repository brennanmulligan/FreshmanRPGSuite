package communication.messages;

import java.io.Serializable;

/**
 * @author rh5172
 *
 */
public class PlayerAppearanceChangeMessage implements Message, Serializable
{
	private static final long serialVersionUID = 1L;

	private String appearanceType;

	private int playerID;


	/**
	 * @param playerID ID of the player
	 * @param appearanceType Appearance type of the player
	 */
	public PlayerAppearanceChangeMessage(int playerID, String appearanceType)
	{
		this.appearanceType = appearanceType;
		this.playerID = playerID;
	}

	/**
	 * @return knowledgePoints
	 */
	public String getAppearanceType()
	{
		return appearanceType;
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
		return "Appearance Change: Appearance changed to: " + appearanceType;
	}


}
