package model;

/**
 * @author Alec Waddelow & Drew Rife
 *
 */
public class GameManagerPlayer
{
	private int playerID;
	private String password;
	private int crew;
	private int major;
	private int section;
	private String name;


	/**
	 * @param playerID
	 * 			Id of the player
	 * @param password
	 * 			players pass
	 * @param crew
	 * 			players crew
	 * @param major
	 * 			crew of the player
	 * @param section
	 * 			section id of the player
	 * @param name
	 * 			name of the player
	 */
	protected GameManagerPlayer(int playerID, String password, int crew, int major, int section, String name)
	{
		super();
		this.playerID = playerID;
		this.password = password;
		this.crew = crew;
		this.major = major;
		this.section = section;
		this.name = name;
	}

	/**
	 * @return playerID
	 */
	protected int getPlayerID()
	{
		return playerID;
	}


	/**
	 * @param playerID the id for this player
	 */
	protected void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * @return password
	 */
	protected String getPassword()
	{
		return password;
	}

	/**
	 * @param password this player's password
	 */
	protected void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return crew
	 */
	protected int getCrew()
	{
		return crew;
	}

	/**
	 * @param crew the crew this player belongs to
	 */
	protected void setCrew(int crew)
	{
		this.crew = crew;
	}

	/**
	 * @return major
	 */
	protected int getMajor()
	{
		return major;
	}

	/**
	 * @param major this player's major
	 */
	protected void setMajor(int major)
	{
		this.major = major;
	}

	/**
	 * @return section
	 */
	protected int getSection()
	{
		return section;
	}

	/**
	 * @param section the section of the course this player is enrolled in
	 */
	protected void setSection(int section)
	{
		this.section = section;
	}

	/**
	 * @return name
	 */
	protected String getName()
	{
		return name;
	}

	/**
	 * @param name this player's name
	 */
	protected void setName(String name)
	{
		this.name = name;
	}
}
