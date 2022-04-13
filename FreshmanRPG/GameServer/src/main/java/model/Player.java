package model;

import java.util.ArrayList;

import dataDTO.LevelManagerDTO;
import dataDTO.PlayerDTO;
import datasource.DatabaseException;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import model.reports.ExperienceChangedReport;
import model.reports.KnowledgePointsChangeReport;
import model.reports.NoMoreBuffReport;
import model.reports.PlayerMovedReport;

/**
 * The class that hold all the data we need for a player.
 *
 * @author Merlin
 */
public class Player
{

	private PlayerLogin playerLogin;

	private int knowledgePoints;

	private PlayerMapper playerMapper;

	private String appearanceType;

	private int playerID;

	private Position playerPosition;

	private String mapName;

	private int experiencePoints;

	private Crew crew;

	final private int LOCAL_CHAT_RADIUS = 5;

	private Major major;

	private int section;

	private int buffPool;

	private boolean playerOnline;

	private ArrayList<String> playerVisitedMaps;

	private String lastVisitedMapTitle;

	Player(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * Add experience points and generates ExperienceChangedReport
	 *
	 * @param expPoints Player's experience points
	 * @throws DatabaseException shouldn't
	 */
	public void addExperiencePoints(int expPoints) throws DatabaseException
	{
		this.experiencePoints = experiencePoints + expPoints;
		ExperienceChangedReport report = new ExperienceChangedReport(this.playerID, this.experiencePoints, LevelManagerDTO.getSingleton().getLevelForPoints(this.experiencePoints));
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * @return - player login information
	 */
	private PlayerLogin getPlayerLogin()
	{
		return playerLogin;
	}

	/**
	 * Get the appearance type for how this player should be drawn
	 *
	 * @return a string matching one of the enum names in the PlayerType enum
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @return the crew to which this player belongs
	 */
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * Get the player's experience points
	 *
	 * @return experience points
	 */
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

	/**
	 * @return the name of the map this player is on
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * @return the id of this player
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Get the unique player name of this player
	 *
	 * @return the player's name
	 */
	public String getPlayerName()
	{
		return playerLogin.getPlayerName();
	}

	/**
	 * Get the player's position
	 *
	 * @return playerPosition Returns the player position. If a position is not set
	 *         should return null.
	 */
	public Position getPlayerPosition()
	{
		return playerPosition;
	}

	/**
	 * Get the quizScore
	 *
	 * @return the quiz score
	 */
	public int getQuizScore()
	{
		return this.knowledgePoints;
	}

	/**
	 * Increment quiz score; Checks the buffPool of the player first. If the player
	 * has buffPool point, knowledge point increases by 1. BuffPool point decreases
	 * by 1. Edited: Truc
	 */
	public void incrementQuizScore()
	{
		this.knowledgePoints++;

		if (buffPool > 0)
		{
			this.knowledgePoints++;
			this.buffPool--;
			if (this.buffPool == 0)
			{
				NoMoreBuffReport report = new NoMoreBuffReport(playerID);
				QualifiedObservableConnector.getSingleton().sendReport(report);
			}
		}

		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(playerID, this.knowledgePoints, this.buffPool);
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * Check the pin to make sure it is correct with regards to contents and
	 * expiration
	 *
	 * @param pinToCheck the pin we gave the player to connect to this area server
	 * @return true or false with pin validity
	 * @throws DatabaseException if the data source had an exception
	 */
	public boolean isPinValid(double pinToCheck) throws DatabaseException
	{
		PlayerConnection pl = new PlayerConnection(playerID);
		if (!pl.isPinValid(pinToCheck) || pl.isExpired())
		{
			return false;
		}
		return true;
	}

	/**
	 * store the information into the data source
	 *
	 * @throws DatabaseException if the data source fails to complete the
	 *         persistance
	 * @throws IllegalQuestChangeException shouldn't
	 */
	public void persist() throws DatabaseException, IllegalQuestChangeException
	{
		playerMapper.persist();
	}

	/**
	 * Set the appearance type for this player
	 *
	 * @param appearanceType the new appearance type
	 */
	public void setAppearanceType(String appearanceType)
	{
		this.appearanceType = appearanceType;
	}

	/**
	 * @param crew the crew to which this player should belong
	 */
	public void setCrew(Crew crew)
	{
		this.crew = crew;
	}

	/**
	 * @param playerMapper the mapper that will be used to persist this player
	 */
	public void setDataMapper(PlayerMapper playerMapper)
	{
		this.playerMapper = playerMapper;
	}

	/**
	 * Set experience points and generates ExperienceChangedReport
	 *
	 * @param expPoints Player's experience points
	 * @throws DatabaseException shouldn't
	 */
	public void setExperiencePoints(int expPoints) throws DatabaseException
	{
		this.experiencePoints = expPoints;
	}

	/**
	 * @param mapName the name of the map this player should be on
	 */
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	}

	/**
	 * @param playerID this player's unique ID
	 */
	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * Tell this player what his login information is
	 *
	 * @param pl his login information
	 */
	public void setPlayerLogin(PlayerLogin pl)
	{
		this.playerLogin = pl;
	}

	/**
	 * Set the player's position
	 *
	 * @param playerPosition The new location the player is Assuming a valid
	 *        position. Error checking else where
	 */
	public void setPlayerPosition(Position playerPosition)
	{
		setPlayerPositionWithoutNotifying(playerPosition);

		sendReportGivingPosition();

	}

	/**
	 * Move a player without notifying the other players (used when moving from one
	 * map to another
	 *
	 * @param newPosition the position the player should move to
	 */
	public void setPlayerPositionWithoutNotifying(Position newPosition)
	{
		this.playerPosition = newPosition;
	}

	/**
	 * Set the quizScore
	 *
	 * @param score the new quiz score
	 */
	public void setQuizScore(int score)
	{
		this.knowledgePoints = score;
	}

	/**
	 * Get's the player's online status
	 * @param playerOnline the player's online status 
	 */
	public void setPlayerOnline(boolean playerOnline)
	{
		this.playerOnline = playerOnline;
	}


	/**
	 * When receiving a local message check if the player is close enough to hear
	 *
	 * @param position position of the sender
	 */
	protected boolean canReceiveLocalMessage(Position position)
	{
		Position myPosition = getPlayerPosition();

		return Math.abs(myPosition.getColumn() - position.getColumn()) <= LOCAL_CHAT_RADIUS && Math.abs(myPosition.getRow() - position.getRow()) <= LOCAL_CHAT_RADIUS;
	}

	/**
	 * Report our position to anyone who is interested
	 */
	public void sendReportGivingPosition()
	{
		PlayerMovedReport report = new PlayerMovedReport(playerID, this.getPlayerName(), playerPosition, this.mapName);

		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * @return the number of knowledge points of this player
	 */
	public int getKnowledgePoints()
	{
		return knowledgePoints;
	}

	/**
	 * @param price the number of points they are losing
	 */
	public void removeKnowledgePoints(int price)
	{
		knowledgePoints -= price;
		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(playerID, this.knowledgePoints, this.buffPool);
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * @return the major of the player
	 */
	public Major getMajor()
	{
		return major;
	}


	/**
	 * Determines if the player is currently online
	 * @return the players online status
	 */
	public boolean isPlayerOnline()
	{
		return playerOnline;
	}

	/**
	 * @param major of the player
	 */
	public void setMajor(Major major)
	{
		this.major = major;
	}

	/**
	 * @param section the section this player is in
	 */
	public void setSection(int section)
	{
		this.section = section;
	}

	/**
	 * @return the section number of this player
	 */
	public int getSection()
	{
		return section;
	}

	/**
	 * set value for buffPool
	 *
	 * @param buffPool - the value their buffPool should be set to
	 */
	public void setBuffPool(int buffPool)
	{
		this.buffPool = buffPool;
	}

	/**
	 * Get value of buffPool
	 *
	 * @return value of buffPool
	 */
	public int getBuffPool()
	{
		return buffPool;
	}

	/**
	 * Returns a DTO for the player.
	 *
	 * @return - PlayerInfo DTO
	 */
	public PlayerDTO getPlayerInfo()
	{
		PlayerDTO playerInfo = new PlayerDTO();
		playerInfo.setAppearanceType(getAppearanceType());
		playerInfo.setExperiencePoints(getExperiencePoints());
		playerInfo.setKnowledgePoints(getQuizScore());
		playerInfo.setPosition(getPlayerPosition());
		playerInfo.setCrew(getCrew());
		playerInfo.setMajor(getMajor());
		playerInfo.setSection(getSection());
		playerInfo.setMapName(getMapName());
		playerInfo.setPlayerID(getPlayerID());
		playerInfo.setPlayerName(getPlayerLogin().getPlayerName());
		playerInfo.setPlayerPassword(getPlayerLogin().getPlayerPassword());
		return playerInfo;
	}


	/**
	 * edits and persists the current player
	 *
	 * @param appearanceType the appearance type of a player
	 * @param quizScore the players quiz score
	 * @param experiencePoints amount of experience points
	 * @param crew the players crew
	 * @param major the players major
	 * @param section the section number
	 * @param name of the player
	 * @param password of the player
	 * @throws DatabaseException a database exception
	 */
	public void editPlayer(String appearanceType, int quizScore, int experiencePoints, Crew crew, Major major, int section, String name, String password) throws DatabaseException
	{
		this.getPlayerLogin().editPlayeLogin(name, password);
		setAppearanceType(appearanceType);
		setQuizScore(quizScore);
		setExperiencePoints(experiencePoints);
		setCrew(crew);
		setMajor(major);
		setSection(section);
		this.playerMapper.setPlayerToEdit(getPlayerInfo());
		try
		{
			this.playerMapper.persist();
		}
		catch (IllegalQuestChangeException e)
		{
			//This should never happen here - we aren't change any quest states
		}

	}

	/**
	 * hashCode
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + LOCAL_CHAT_RADIUS;
		result = prime * result + ((appearanceType == null) ? 0 : appearanceType.hashCode());
		result = prime * result + buffPool;
		result = prime * result + ((crew == null) ? 0 : crew.hashCode());
		result = prime * result + experiencePoints;
		result = prime * result + knowledgePoints;
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
		result = prime * result + playerID;
		result = prime * result + ((playerLogin == null) ? 0 : playerLogin.hashCode());
		result = prime * result + (playerOnline ? 1231 : 1237);
		result = prime * result + ((playerPosition == null) ? 0 : playerPosition.hashCode());
		result = prime * result + ((playerVisitedMaps == null) ? 0 : playerVisitedMaps.hashCode());
		result = prime * result + section;
		return result;
	}

	/**
	 * check if equal
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		// could have been NPC
		if (!(obj instanceof Player))
		{
			return false;
		}
		Player other = (Player) obj;
		if (appearanceType == null)
		{
			if (other.appearanceType != null)
			{
				return false;
			}
		}
		else if (!appearanceType.equals(other.appearanceType))
		{
			return false;
		}
		if (this.getClass().equals(Player.class) && buffPool != other.buffPool)
		{
			return false;
		}
		if (crew != other.crew)
		{
			return false;
		}
		if (experiencePoints != other.experiencePoints)
		{
			return false;
		}
		if (knowledgePoints != other.knowledgePoints)
		{
			return false;
		}
		if (major != other.major)
		{
			return false;
		}
		if (mapName == null)
		{
			if (other.mapName != null)
			{
				return false;
			}
		}
		else if (!mapName.equals(other.mapName))
		{
			return false;
		}
		if (playerID != other.playerID)
		{
			return false;
		}
		if (playerLogin == null)
		{
			if (other.playerLogin != null)
			{
				return false;
			}
		}
		else if (!playerLogin.equals(other.playerLogin))
		{
			return false;
		}
		if (playerOnline != other.playerOnline)
		{
			return false;
		}
		if (playerPosition == null)
		{
			if (other.playerPosition != null)
			{
				return false;
			}
		}
		else if (!playerPosition.equals(other.playerPosition))
		{
			return false;
		}
		if (section != other.section)
		{
			return false;
		}
		if (!playerVisitedMaps.equals(other.playerVisitedMaps))
		{
			return false;
		}
		return true;
	}

	/**
	 * Changes the players appearance in the database.
	 *
	 * @param appearanceType - The appearance to change to.
	 * @throws DatabaseException a database exception
	 * @throws IllegalQuestChangeException in case of an illegal quest change
	 */
	public void editPlayerAppearance(String appearanceType) throws DatabaseException, IllegalQuestChangeException
	{
		this.playerMapper.setPlayerToEdit(getPlayerInfo());
		this.playerMapper.setPlayerAppearanceType(appearanceType);
		this.playerMapper.persist();
	}

	/**
	 * get the player's maps visited
	 * @return playerVisitedMaps list of maps
	 */
	public ArrayList<String> getPlayerVisitedMaps()
	{
		return playerVisitedMaps;
	}

	/**
	 * set the maps a player has visited
	 * @param playerVisitedMaps list of maps
	 */
	public void setPlayerVisitedMaps(ArrayList<String> playerVisitedMaps)
	{
		this.playerVisitedMaps = playerVisitedMaps;
	}

	/**
	 * get the last visited map
	 * @return lastVisitedMap
	 */
	public String getLastVisitedMap()
	{
		return lastVisitedMapTitle;
	}

	/**
	 * add a newly visited map to the player
	 * @param mapName the title of the map you just visited. 
	 */
	public void addNewVisitedMap(String mapName)
	{
		String mapTitle = ServerMapManager.getSingleton().getMapTitleFromMapName(mapName);

		if (mapTitle != null)
		{
			if (!playerVisitedMaps.contains(mapTitle))
			{
				this.playerVisitedMaps.add(mapTitle);
			}
			this.lastVisitedMapTitle = mapTitle;
		}
	}
}