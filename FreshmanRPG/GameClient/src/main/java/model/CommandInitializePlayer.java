package model;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

/**
 * Command the puts a new player in the system when that new player joins our
 * area server.
 *
 * @author merlin
 *
 */
public class CommandInitializePlayer extends Command
{

	private int playerID;
	private String playerName;

	private String appearanceType;

	private Position position;

	private Crew crew;

	private Major major;

	private int section;

	/**
	 * For now, we just know his name
	 *
	 * @param playerID
	 *            the unique player name of the new player
	 * @param playerName
	 *            this player's name
	 * @param appearanceType
	 *            The appearance type of this player
	 * @param position
	 *            The position of this player
	 * @param crew
	 *            the crew to which this player belongs
	 * @param major
	 * 			  the major to which this player belongs
	 * @param section
	 * 			  the section number of the player
	 */
	public CommandInitializePlayer(int playerID, String playerName,
								   String appearanceType, Position position, Crew crew, Major major, int section)
	{
		this.playerID = playerID;
		this.playerName = playerName;
		this.appearanceType = appearanceType;
		this.position = position;
		this.crew = crew;
		this.major = major;
		this.section = section;
	}

	/**
	 * @see Command#execute()
	 */
	@Override
	boolean execute()
	{
		ClientPlayerManager.getSingleton().initializePlayer(playerID, playerName,
				appearanceType, position, crew, major, section);
		return true;
	}

	/**
	 * @return the appearance type of the new player
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @return the crew of the new player
	 */
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * @return the id of the new player
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the new player's name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @return where the new player is standing
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 * @return the major of the player
	 */
	public Major getMajor()
	{
		return major;
	}

	/**
	 * @return section number of the player
	 */
	public int getSection()
	{
		return section;
	}

}
