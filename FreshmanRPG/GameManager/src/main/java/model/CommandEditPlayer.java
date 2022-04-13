package model;

import datasource.DatabaseException;
import datatypes.Crew;
import datatypes.Major;

/**
 * @author Regi, David
 *
 */
public class CommandEditPlayer extends Command
{

	private int playerID;
	private String appearanceType;
	private int quizScore;
	private int experiencePoints;
	private Crew crew;
	private Major major;
	private int section;
	private String password;
	private String name;

	/**
	 * @param playerID
	 * 			The player id
	 * @param name
	 * 			The name of the Player
	 * @param password
	 * 			The password of the player
	 * @param appearanceType
	 * 			the appearance type of the player
	 * @param quizScore
	 * 			The players quiz score
	 * @param experiecePoints
	 * 			The players exp points
	 * @param crew
	 * 			Crew of the player
	 * @param major
	 * 			major of the player
	 * @param section
	 * 			section number of the player
	 */
	public CommandEditPlayer(int playerID, String appearanceType, int quizScore, int experiecePoints, Crew crew, Major major, int section, String name, String password)
	{
		this.playerID = playerID;
		this.appearanceType = appearanceType;
		this.quizScore = quizScore;
		this.experiencePoints = experiecePoints;
		this.crew = crew;
		this.major = major;
		this.section = section;

		this.name = name;
		this.password = password;
	}


	/**
	 * Executes an edit on a player
	 */
	@Override
	boolean execute()
	{

		GameManagerPlayerManager p = null;
		try
		{
			p = GameManagerPlayerManager.getInstance();
			return p.editPlayer(playerID, appearanceType, quizScore, experiencePoints, crew, major, section, name, password);

		}
		catch (DatabaseException | IllegalQuestChangeException e)
		{
			System.out.println("Cound Not Edit Player With ID: " + playerID);
		}
		return false;

	}

}
