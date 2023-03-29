package edu.ship.engr.shipsim.datatypes;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.util.*;
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

    JOHN(1, "John", "null_pointer_exception", 52, 52, "pw", "quad.tmx", 1111, null, 42,
            45, Crew.FORTY_PERCENT, Major.COMPUTER_ENGINEERING, 1, 4, false,
            new ArrayList<>(Arrays.asList("Rec Center", "Quad", "Mowrey"))),

    /**
     * Merlin must be player 2 for the player mapper tests to pass. That number maps
     * to player 2 in the quest and objective states in QuestStatesForTest and
     * ObjectiveStatesForTest
     */

    MERLIN(2, "Merlin", "merlin", 52, 52, "pw", "quad.tmx", 1111, null, 42, 46,
            Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 1, 0, true,
            new ArrayList<>(Arrays.asList("Rec Center", "Quad", "Map1"))),

    /**
     *
     */

    NICK(3, "Nick", "off_by_one", 7, 13, "pw", "library.tmx", 1111, null, 0, 35, Crew.OFF_BY_ONE, Major.COMPUTER_SCIENCE, 2, 0, false, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */

    JOSH(4, "Josh", "Ninja", 52, 52, "pw", "quad.tmx", 1111, null, 0, 25, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 3, 0, false, new ArrayList<>(Arrays.asList("Rec Center", "SortingRoom"))),
    /**
     *
     */

    MATT(5, "Matt", "male_a", 52, 52, "pw", "quad.tmx", 1111, null, 0, 12, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */

    STEVE(6, "Steve", "knight_with_straw_hat", 52, 52, "pw", "quad.tmx", 1111, null, 0, 13, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */

    FRANK(7, "Frank", "out_of_bounds", 52, 52, "pw", "quad.tmx", 1441, null, 0, 13, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING, 2, 0, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */

    GA(8, "Ga", "off_by_one", 52, 52, "pw", "quad.tmx", 1111, null, 0, 15, Crew.OFF_BY_ONE, Major.ELECTRICAL_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */

    ANDY(9, "Andy", "off_by_one", 3, 10, "pw", "wellingtonRoom.tmx", 1111, null, 0, 33, Crew.FORTY_PERCENT, Major.ELECTRICAL_ENGINEERING, 1, 0, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */

    DAVE(10, "Dave", "out_of_bounds", 4, 13, "pw", ServersForTest.CUB.getMapName(), 1111, null, 0, 12,
            Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING, 2, 0, false, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */

    LOSER(11, "Loser", "off_by_one", 4, 13, "pw", null, 1111, null, 0, 3, Crew.OFF_BY_ONE, Major.ELECTRICAL_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Rec Center"))),

    /**
     *
     */

    MOCK_NPC(12, "NPC1", "Magi", 4, 13, "", "silly.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING, 2, 0, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     * Necessary for the game
     */

    QUIZBOT(13, "QuizBot", "Magi", 4, 19, "", "recCenter.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("Quad"))),

    /**
     *
     */

    MOCK_NPC3(14, "NPC3", "Magi", 4, 13, "", "silly.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 1, 0, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */

    RYAN(15, "Ryan", "female_a", 52, 52, "pw", "quad.tmx", 1111, null, 0, 13, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     * Newbie must have the default_player appearance.
     */

    NEWBIE(16, "Newbie", "default_player", 11, 7, "pw", "sortingRoom.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 2, 0, false, new ArrayList<>()),

    /**
     *
     */

    RED_HAT(17, "Red Hat", "RedHat", 9, 7, "", "sortingRoom.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */

    MARTY(18, "Marty", "off_by_one", 52, 52, "pw", "quad.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.SOFTWARE_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */
    HERSH(19, "Hersh", "null_pointer_exception", 52, 52, "pw", "quad.tmx", 1111, null, 0, 0, Crew.FORTY_PERCENT, Major.SOFTWARE_ENGINEERING, 2, 0, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),
    /**
     *
     */
    JEFF(20, "Jeff", "null_pointer_exception", 52, 52, "pw", "quad.tmx", 1111, null, 0, 0, Crew.FORTY_PERCENT, Major.SOFTWARE_ENGINEERING, 2, 50, false, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */
    JAWN(21, "Jawn", "null_pointer_exception", 52, 52, "pw", "quad.tmx", 1111, null, 0, 0, Crew.FORTY_PERCENT, Major.SOFTWARE_ENGINEERING, 2, 0, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),
    /**
     *
     */
    DATBOI(22, "DAT_BOI", "merlin", 52, 52, "pw", "quad.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 2, 50, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),
    /**
     *
     */
    TUTOR(23, "Tutor", "tutor", 14, 48, "pw", "library.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),
    /**
     *
     */
    BIG_RED(24, "Big Red", "merlin", 75, 40, "", "quad.tmx", 0, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     *
     */
    RANDOM_FACTS_NPC_2(25, "RandomFactsGuru", "merlin", 17, 67, "", "library.tmx", 0, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 3, 0, false, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     * duck NPC
     */
    PROFESSOR_H(26, "Professor H", "Magi", 38, 8, "", "Ducktopia.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.SOFTWARE_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("Ducktopia"))),

    /**
     * Quad NPC
     */
    QUAD_GUY(27, "Quad_GUY", "tutor", 64, 55, "", "quad.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("quad"))),

    /**
     * tim
     */
    IT_GUY_NPC(28, "IT Guy", "Magi", 55, 47, "", "mct1.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.SOFTWARE_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("mct1"))),

    /**
     * Merlin must be player 2 for the player mapper tests to pass. That number maps
     * to player 2 in the quest and objective states in QuestStatesForTest and
     * ObjectiveStatesForTest
     */

    MERLIN_OFFLINE(29, "Merlin_Offline", "merlin", 52, 52, "pw", "quad.tmx", 1111, null, 42, 46, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 1, 0, false, new ArrayList<>(Arrays.asList("Rec Center", "Quad"))),

    /**
     * TEAcher npc, used for the tea quest
     */
    TEACHER_NPC(30, "TEAcher", "Magi", 67, 70, "", "quad.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.COMPUTER_SCIENCE, 3, 0, true, new ArrayList<>(Arrays.asList("quad"))),

    /**
     * NPC for playing rock, paper, scissors
     */
    ROCK_PAPER_SCISSORS_NPC(31, "RPSGuy", "andy", 30, 30, "", "quad.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.COMPUTER_SCIENCE, 3, 0, true, new ArrayList<>(Arrays.asList("quad"))),

    /**
     * NPC for mowrey info person
     */
    MOWREY_FRONTDESK_NPC(32, "MowreyInfoPerson", "Magi", 48, 56, "", "mowrey.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.CS_AND_E_GENERAL, 3, 0, true, new ArrayList<>(Arrays.asList("quad", "Mowrey"))),

    PRESIDENT_NPC(33, "President", "Magi", 89, 30, "", "quad.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.ELECTRICAL_ENGINEERING, 3, 0, true, new ArrayList<>(Arrays.asList("quad", "Mowrey"))),

    QUIET_PLAYER(34, "ShyGuy", "default_player", 11, 7, "pw", "sortingRoom.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 2, 0, false, new ArrayList<>()),

    EASTEREGG_PLAYER(35, "Egg", "default_player", 52, 52, "pw", "quad.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 2, 0, false, new ArrayList<>()),

    BIKE_PLAYER(36, "Micycle", "default_player",  11, 7, "pw", "oldMainInside.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 2, 0, false, new ArrayList<>()),

    HISTORY_NPC(37, "HistoryNPC", "History Nerd", 51, 56, "", "quad.tmx", 1111, null, 0, 0, Crew.NPCS, Major.HISTORY, 3, 0, true, new ArrayList<>(Arrays.asList("Quad"))),
    HISTORY_NPC_2(38, "HistoryNPC2", "History Nerd", 51, 58, "", "quad.tmx", 1111, null, 0, 0, Crew.NPCS, Major.HISTORY, 3, 0, true, new ArrayList<>(Arrays.asList("Quad"))),
    HISTORY_NPC_3(39, "HistoryNPC3", "History Nerd", 51, 60, "", "quad.tmx", 1111, null, 0, 0, Crew.NPCS, Major.HISTORY, 3, 0, true, new ArrayList<>(Arrays.asList("Quad")))
    ;


    private final int playerID;
    private final String appearanceType;
    private final int row;
    private final int col;
    private final String playerName;
    private final String password;
    private final String mapName;
    private final int pin;
    private final String changedOn;
    private final int doubloons;
    private final Crew crew;
    private final Major major;
    private final int section;
    private final int buffPool;
    private final boolean online;
    private final ArrayList<String> mapsVisited;

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

    PlayersForTest(int id, String playerName, String type, int row, int col, String password, String mapName, int pin, String changedOn, int doubloons, int experiencePoints,
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
        this.doubloons = doubloons;
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
     * PlayerTypes
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
     * get this player's current doubloons
     *
     * @return this player's doubloons
     */
    public int getDoubloons()
    {
        return doubloons;
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
     *
     * @return mapsVisited the list of maps
     */
    public ArrayList<String> getMapsVisited()
    {
        return this.mapsVisited;
    }

    /**
     * get the online status
     *
     * @return online
     */
    public boolean getOnline()
    {
        return this.online;
    }

    /**
     * @return
     */
    public List<VanityDTO> getOwnedItems()
    {
        List<VanityDTO> items = new ArrayList<>();
        for (VanityInventoryItemsForTest inventoryItem : VanityInventoryItemsForTest.values())
        {
            if (inventoryItem.getPlayerID() == playerID)
            {
                VanityItemsForTest item = VanityItemsForTest.values()[inventoryItem.getVanityID() - 1];
                items.add(new VanityDTO(item.getId(), item.getName(), item.getDescription(), item.getTextureName(), VanityType.fromInt(item.getVanityType())));
            }
        }
        for (DefaultItemsForTest dItem : DefaultItemsForTest.values())
        {
            VanityItemsForTest item = VanityItemsForTest.values()[dItem.getDefaultID() - 1];
            items.add(new VanityDTO(item.getId(), item.getName(), item.getDescription(), item.getTextureName(), VanityType.fromInt(item.getVanityType())));
        }
        return items;
    }

    public List<VanityDTO> getVanityItems()
    {
        List<VanityDTO> items = new ArrayList<>();
        for (VanityInventoryItemsForTest inventoryItem : VanityInventoryItemsForTest.values())
        {
            if (inventoryItem.getPlayerID() == playerID && inventoryItem.getIsWearing() == 1)
            {
                VanityItemsForTest item = VanityItemsForTest.values()[inventoryItem.getVanityID() - 1];
                items.add(new VanityDTO(item.getId(), item.getName(), item.getDescription(), item.getTextureName(), VanityType.fromInt(item.getVanityType())));
            }
        }
        return items;
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
     *
     * @param playerName
     * @return
     */
    public static PlayersForTest grabPlayerName(String playerName)
    {
        return ALLBYNAME.get(playerName);
    }

}
