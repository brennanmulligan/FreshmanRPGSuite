package datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The players that are in the test database
 *
 * @author Merlin
 */
public enum PlayersForTest
{
	/**
	 * John must be player 1 for the current quest state report tests to pass
	 */

	JOHN(1, "John", "null_pointer_exception", 91, 7, "pw", "theGreen.tmx", 1111, null, 42, 45, Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING, 1, 4, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 * Merlin must be player 2 for the player mapper tests to pass. That number maps
	 * to player 2 in the quest and adventure states in QuestStatesForTest and
	 * AdventureStatesForTest
	 */

	MERLIN(2, "Merlin", "merlin", 91, 7, "pw", "theGreen.tmx", 1111, null, 42, 46, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 1, 0, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */

	NICK(3, "Nick", "off_by_one", 7, 13, "pw", "homework.tmx", 1111, null, 0, 35, Crew.OFF_BY_ONE, Major.COMPUTER_SCIENCE, 2, 0, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */

	JOSH(4, "Josh", "Ninja", 91, 7, "pw", "theGreen.tmx", 1111, null, 0, 25, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 3, 0, false, new ArrayList<>(Arrays.asList("Quiznasium", "SortingRoom"))),
	/**
	 *
	 */

	MATT(5, "Matt", "male_a", 91, 7, "pw", "theGreen.tmx", 1111, null, 0, 12, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */

	STEVE(6, "Steve", "knight_with_straw_hat", 91, 7, "pw", "theGreen.tmx", 1111, null, 0, 13, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */

	FRANK(7, "Frank", "out_of_bounds", 91, 7, "pw", "theGreen.tmx", 1441, null, 0, 13, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING, 2, 0, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */

	GA(8, "Ga", "off_by_one", 91, 7, "pw", "theGreen.tmx", 1111, null, 0, 15, Crew.OFF_BY_ONE, Major.ELECTRICAL_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */

	ANDY(9, "Andy", "off_by_one", 3, 10, "pw", "wellingtonRoom.tmx", 1111, null, 0, 33, Crew.NULL_POINTER, Major.ELECTRICAL_ENGINEERING, 1, 0, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */

	DAVE(10, "Dave", "out_of_bounds", 4, 13, "pw", null, 1111, null, 0, 12, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING, 2, 0, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */

	LOSER(11, "Loser", "off_by_one", 4, 13, "pw", null, 1111, null, 0, 3, Crew.OFF_BY_ONE, Major.ELECTRICAL_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Quiznasium"))),

	/**
	 *
	 */

	MOCK_NPC(12, "NPC1", "Magi", 4, 13, "", "silly.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING, 2, 0, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 * Necessary for the game
	 */

	QUIZBOT(13, "QuizBot", "Magi", 4, 19, "", "quiznasium.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("TheGreen"))),

	/**
	 *
	 */

	MOCK_NPC3(14, "NPC3", "Magi", 4, 13, "", "silly.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 1, 0, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */

	RYAN(15, "Ryan", "female_a", 91, 7, "pw", "theGreen.tmx", 1111, null, 0, 13, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 * Newbie must have the default_player appearance.
	 */

	NEWBIE(16, "Newbie", "default_player", 11, 7, "pw", "sortingRoom.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 2, 0, false, new ArrayList<>()),

	/**
	 *
	 */

	RED_HAT(17, "Red Hat", "RedHat", 9, 7, "", "sortingRoom.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */

	MARTY(18, "Marty", "off_by_one", 95, 7, "pw", "theGreen.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.SOFTWARE_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */
	HERSH(19, "Hersh", "null_pointer_exception", 92, 7, "pw", "theGreen.tmx", 1111, null, 0, 0, Crew.NULL_POINTER, Major.SOFTWARE_ENGINEERING, 2, 0, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),
	/**
	 *
	 */
	JEFF(20, "Jeff", "null_pointer_exception", 92, 7, "pw", "theGreen.tmx", 1111, null, 0, 0, Crew.NULL_POINTER, Major.SOFTWARE_ENGINEERING, 2, 50, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */
	JAWN(21, "Jawn", "null_pointer_exception", 91, 7, "pw", "theGreen.tmx", 1111, null, 0, 0, Crew.NULL_POINTER, Major.SOFTWARE_ENGINEERING, 2, 0, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),
	/**
	 *
	 */
	DATBOI(22, "DAT_BOI", "merlin", 91, 7, "pw", "theGreen.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 2, 50, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),
	/**
	 *
	 */
	TUTOR(23, "Tutor", "tutor", 10, 3, "pw", "homework.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),
	/**
	 *
	 */
	RANDOM_FACTS_NPC_1(24, "RandomFacts", "merlin", 88, 21, "", "theGreen.tmx", 0, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 3, 0, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 *
	 */
	RANDOM_FACTS_NPC_2(25, "RandomFactsGuru", "merlin", 17, 67, "", "homework.tmx", 0, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 3, 0, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 * duck NPC
	 */
	HUO(26, "Huo", "Magi", 38, 8, "", "Ducktopia.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.SOFTWARE_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("Ducktopia"))),

	/**
	 * Quad NPC
	 */
	QUAD_GUY(27, "Quad_GUY", "tutor", 88, 10, "", "theGreen.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("theGreen"))),

	/**
	 * tim
	 */
	TIM_NPC(28, "Tim", "Magi", 55, 47, "", "mct1.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.SOFTWARE_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("mct1"))),

	/**
	 * Merlin must be player 2 for the player mapper tests to pass. That number maps
	 * to player 2 in the quest and adventure states in QuestStatesForTest and
	 * AdventureStatesForTest
	 */

	MERLIN_OFFLINE(29, "Merlin_Offline", "merlin", 91, 7, "pw", "theGreen.tmx", 1111, null, 42, 46, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Quiznasium", "TheGreen"))),

	/**
	 * Dr. Mooney npc
	 */
	MOONEY_NPC(30, "DJMoon", "Magi", 95, 10, "", "theGreen.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.COMPUTER_SCIENCE, 3, 0, true, new ArrayList<>(Arrays.asList("theGreen"))),

	/**
	 * NPC for playing rock, paper, scissors
	 */
	RockPaperScissors_NPC(31, "RPSGuy", "andy", 23, 15, "", "current.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.COMPUTER_SCIENCE, 3, 0, true, new ArrayList<>(Arrays.asList("theGreen")));


	private int playerID;
	private String appearanceType;
	private int row;
	private int col;
	private String playerName;
	private String password;
	private String mapName;
	private int pin;
	private String changedOn;
	private int knowledgePoints;
	private Crew crew;
	private Major major;
	private int section;
	private int buffPool;
	private boolean online;
	private ArrayList<String> mapsVisited;

	/**
	 * @return the pin for the current connection
	 */
	public int getPin()
	{
		return pin;
	}

	/**
	 * @return the time when the pin for the current connection was set
	 */
	public String getChangedOn()
	{
		return changedOn;
	}

	private int experiencePoints;

	PlayersForTest(int id, String playerName, String type, int row, int col, String password, String mapName, int pin, String changedOn, int quizScore, int experiencePoints,
				   Crew crew, Major major, int section, int buffPool, boolean online, ArrayList<String> mapsVisited)

	{
		this.playerID = id;
		this.playerName = playerName;
		this.appearanceType = type;
		this.row = row;
		this.col = col;
		this.password = password;
		this.mapName = mapName;
		this.pin = pin;
		this.changedOn = changedOn;
		this.experiencePoints = experiencePoints;
		this.crew = crew;
		this.knowledgePoints = quizScore;
		this.major = major;
		this.section = section;
		this.buffPool = buffPool;
		this.online = online;
		this.mapsVisited = mapsVisited;
	}

	/**
	 * Get the player's player type
	 *
	 * @return a string that matches the name of one of the members of the enum
	 *         PlayerTypes
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * Get the name of the map the player was most recently on
	 *
	 * @return the map name
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * Get the player's unique ID
	 *
	 * @return the id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Get the player's unique name
	 *
	 * @return the name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @return the player's password
	 */
	public String getPlayerPassword()
	{
		return password;
	}

	/**
	 * get this player's most recent position
	 *
	 * @return this player's position
	 */
	public Position getPosition()
	{
		return new Position(row, col);
	}

	/**
	 * @param pos - the new position
	 */
	public void setPosition(Position pos)
	{
		row = pos.getRow();
		col = pos.getColumn();
	}

	/**
	 * get this player's current quiz score
	 *
	 * @return this player's score
	 */
	public int getKnowledgeScore()
	{
		return knowledgePoints;
	}

	/**
	 * @return this player's experience points
	 */
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

	/**
	 * @return the crew to which this player belongs
	 */
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * @return the major for this student
	 */
	public Major getMajor()
	{
		return major;
	}

	/**
	 * @return the section number of a player
	 */
	public int getSection()
	{
		return section;
	}

	/**
	 * Gets buffPool
	 *
	 * @return buffPool
	 */
	public int getBuffPool()
	{
		return this.buffPool;
	}

	/**
	 * get the maps visited by the player
	 * @return mapsVisited the list of maps
	 */
	public ArrayList<String> getMapsVisited()
	{
		return this.mapsVisited;
	}

	/**
	 * add a map to the player
	 * @param mapName name of the map
	 */
	public void addMapVisited(String mapName)
	{
		mapsVisited.add(mapName);
	}

	/**
	 * remove a map from the visited list
	 * @param mapName the map to be removed
	 */
	public void removeMapVisited(String mapName)
	{
		mapsVisited.remove(mapName);
	}

	/**
	 * Sets buffPool
	 *
	 * @param buffPool the new buff
	 */
	public void setBuffPool(int buffPool)
	{
		this.buffPool = buffPool;
	}

	/**
	 * get the online status
	 * @return online
	 */
	public boolean getOnline()
	{
		return this.online;
	}

	/**
	 * set the online status
	 * @param online true if the player is currently playing
	 */
	public void setOnline(boolean online)
	{
		this.online = online;
	}


	public static final Map<Integer, PlayersForTest> ALL = EnumSet.allOf(PlayersForTest.class)
			.stream().collect(Collectors.toMap(PlayersForTest::getPlayerID, Function.identity()));

	public static final Map<String, PlayersForTest> ALLBYNAME = EnumSet.allOf(PlayersForTest.class)
			.stream().collect(Collectors.toMap(PlayersForTest::getPlayerName, Function.identity()));

	public static PlayersForTest grabPlayerId(int playerID)
	{
		return ALL.get(playerID);
	}

	/**
	 * Gets the player data by their name
	 * @param playerName
	 * @return
	 */
	public static PlayersForTest grabPlayerName(String playerName)
	{
		return ALLBYNAME.get(playerName);
	}

}
