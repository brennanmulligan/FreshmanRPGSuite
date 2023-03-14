package edu.ship.engr.shipsim.datatypes;

import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.criteria.QuestCompletionActionParameter;
import edu.ship.engr.shipsim.criteria.QuestListCompletionParameter;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.PlayerTableDataGateway;

import java.util.*;

/**
 * Creates objectives for the DB
 *
 * @author merlin
 */
public enum QuestsForTest
{


    ONE_BIG_QUEST(1, "t0", "Quest 1",
            new GameLocationDTO(PlayerTableDataGateway.INITIAL_GAME_LOCATION.getMapName(),
                    PlayerTableDataGateway.INITIAL_GAME_LOCATION.getPosition()), 5, 2,
            QuestCompletionActionType.TELEPORT,
            new GameLocationDTO(
                    "quad.tmx", new Position(3, 3)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    THE_OTHER_QUEST(2, "t1", "Quest 2", new GameLocationDTO("quad.tmx", new Position(92, 7)), 4, 2, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    ONE_SAME_LOCATION_QUEST(3, "t2", "Quest 3", new GameLocationDTO("quad.tmx", new Position(92, 7)), 3, 2, QuestCompletionActionType.TELEPORT, new GameLocationDTO("quad.tmx", new Position(3, 3)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */

    THE_LITTLE_QUEST(4, "t3", "Quest 4", new GameLocationDTO("quad.tmx", new Position(3, 32)), 5, 1, QuestCompletionActionType.TELEPORT, new GameLocationDTO("quad.tmx", new Position(3, 3)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    CHAT_TO_AN_NPC_QUEST(5, "t4", "Quest 5", new GameLocationDTO("quad.tmx", new Position(0, 0)), 5, 1, QuestCompletionActionType.TELEPORT, new GameLocationDTO("quad.tmx", new Position(3, 3)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    TELEPORT_QUEST(6, "t5", "Teleporting Quest", new GameLocationDTO("quad.tmx", new Position(2, 32)), 1, 1, QuestCompletionActionType.TELEPORT, new GameLocationDTO("sortingRoom.tmx", new Position(3, 3)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    INVALID_QUEST(7, "t6", "Quest 7", new GameLocationDTO("quad.tmx", new Position(92, 7)), 4, 2, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(2015, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    EXPIRED_QUEST(8, "t7", "Quest 8", new GameLocationDTO("quad.tmx", new Position(92, 7)), 4, 2, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(1970, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(2000, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    FINISHED_QUEST(9, "t8", "Quest 9", new GameLocationDTO("quad.tmx", new Position(92, 7)), 4, 2, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(1970, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(2000, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    DOUBLOON_QUEST(10, "Doubloon Quest", "Quest 10", new GameLocationDTO("quad.tmx", new Position(92, 7)), 5, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.MARCH, 21).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    TRIGGERED_STATE_TO_EXPIRED_STATE_QUEST(11, "Quest 11", "Quest should go from triggered to expired", new GameLocationDTO("quad.tmx", new Position(3, 3)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2016, Calendar.APRIL, 26).getTime(), new GregorianCalendar(2016, Calendar.APRIL, 26).getTime(), false),
    /**
     *
     */
    INTERACTABLE_ITEM_QUEST(12, "Quest 12", "Interactable Items should be a viable method of completing quests", new GameLocationDTO("quad.tmx", new Position(92, 7)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    INTERACTABLE_ITEM_QUEST_SUB(13, "Quest 13", "Interact with an item",
            new GameLocationDTO("current.tmx", new Position(8, 9)), 1, 2, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),

    /**
     *
     */
    FIRST_FRIEND_QUEST(14, "Quest 14", "Make your first friend", new GameLocationDTO("current.tmx", new Position(8, 9)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    MANY_FRIENDS_QUEST(15, "Quest 15", "Make multiple friends", new GameLocationDTO("current.tmx", new Position(8, 9)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),
    /**
     *
     */
    TEST_CHAT_RESPONSE_QUEST(16, "Chat Response", "Get talked at by NPC", new GameLocationDTO("mowrey.tmx", new Position(29, 36)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),

    INVENTORY_QUEST_TEST(17, "Test Quest Reward", "Testing adding swag to inventory on quest complete", new GameLocationDTO("quad.tmx", new Position(92, 7)), 0, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),

    /**
     * The real opening quest
     */
    ONRAMPING_QUEST(100, "Introductory Quest",
            "Welcome!  For your first quest, you need to learn a little bit about this world.  Press Q to see what you need to do.  "
                    + "Double clicking on a quest in the quest screen will show you its objectives.",
            PlayerTableDataGateway.INITIAL_GAME_LOCATION, 5, 11,
            QuestCompletionActionType.TELEPORT,
            new GameLocationDTO("quad.tmx", new Position(92, 7)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
            new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),

    /**
     * Real quest to make them explore
     */
    EXPLORATION_QUEST(101, "Exploration", "Explore your new school", new GameLocationDTO("sortingRoom.tmx", new Position(4, 13)), 2, 5, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),


    /**
     * Quest for real life tutor
     */
    MEET_REAL_LIFE_TUTOR_QUEST(102, "Meet Real Life Tutor", "Talk with the Real Tutor during their available hours", new GameLocationDTO("sortingRoom.tmx", new Position(4, 13)), 2, 5, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),


    /**
     * Real quest for them to meet the tutor
     */
    MEET_NPC_TUTOR_QUEST(103, "Meet The Tutor", "Talk with the NPC tutor.", new GameLocationDTO("library.tmx", new Position(1, 1)), 10, 1,
            QuestCompletionActionType.TRIGGER_QUESTS, new QuestListCompletionParameter(new ArrayList<>(Arrays.asList(MEET_REAL_LIFE_TUTOR_QUEST.getQuestID()))), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),

    /**
     * Recipe Quest
     */
    RECIPE_QUEST(104, "Curiouser and Curiouser!", "Find the special tea recipe in The Green.",
            new GameLocationDTO("quad.tmx", new Position(92, 7)), 2, 1, QuestCompletionActionType.NO_ACTION,
            null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
            new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),

    /**
     * Tea Ingredients Quest
     */
    COLLECT_TEA_INGREDIENTS(105, "Flowers for Tea", "TEAcher wants to try that tea recipe you found. Find four flowers to help him make it.",
            new GameLocationDTO("quad.tmx", new Position(92, 7)), 1, 5, QuestCompletionActionType.NO_ACTION,
            null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
            new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),

    /**
     * Riddle Quest
     * GameLocationDTO("library.tmx", new Position(10,3))
     */
    RIDDLE_QUEST(106, "Solving Riddles", "You need to solve these riddles by finding to locations that they describe. Good Luck!",
            new GameLocationDTO("quad.tmx", new Position(92, 7)), 10, 3, QuestCompletionActionType.NO_ACTION,
            null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
            new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),

    ROCK_PAPER_SCISSORS(107, "Roshambo", "A mysterious figure issues you a challenge, but first you must find the secret password...",
            new GameLocationDTO("quad.tmx", new Position(92, 7)), 1, 2, QuestCompletionActionType.NO_ACTION,
            null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
            new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false),

    TEMP_QUEST(108, "Triggered by object", "This quest was triggered by an interacting with an object. ",
            new GameLocationDTO("quad.tmx", new Position(83, 33)), 1, 1, QuestCompletionActionType.NO_ACTION,
            null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
            new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false);



    private final int questID;
    private final String questTitle;
    private final String questDescription;
    private final GameLocationDTO gameLocation;
    private final int experienceGained;
    private final int objectiveForFulfillment;
    private final QuestCompletionActionType completionActionType;
    private final QuestCompletionActionParameter completionActionParameter;
    private final Date startDate;
    private final Date endDate;
    private final boolean isEasterEgg;

    /**
     * Constructor for Quests Enum
     *
     * @param questID                 this quest's unique ID
     * @param questTitle              this quest's title
     * @param questDescription        what the player has to do
     * @param experienceGained        the number of experience points you get when you fulfill the
     *                                quest
     * @param objectiveForFulfillment the number of objectives you must complete to fulfill the
     *                                quest
     * @param completionActionType    TThe type of action that should be taken when this quest
     *                                complete
     * @param completionActionParam   The parameter for the completion action
     * @param startDate               The first day that this quest is available
     * @param endDate                 This last day that this quest is available
     * @param isEasterEgg             This is a boolean for if the quest is an Easter egg or not
     */
    QuestsForTest(int questID, String questTitle, String questDescription, GameLocationDTO gameLocation,
                  int experienceGained, int objectiveForFulfillment, QuestCompletionActionType completionActionType,
                  QuestCompletionActionParameter completionActionParam, Date startDate, Date endDate, boolean isEasterEgg)
    {
        this.questID = questID;
        this.questTitle = questTitle;
        this.questDescription = questDescription;
        this.gameLocation = gameLocation;
        this.experienceGained = experienceGained;
        this.objectiveForFulfillment = objectiveForFulfillment;
        this.completionActionType = completionActionType;
        this.completionActionParameter = completionActionParam;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isEasterEgg = isEasterEgg;
    }

    /**
     * Constructor for Quests Enum
     *
     * @param questID                 this quest's unique ID
     * @param questTitle              this quest's title
     * @param questDescription        what the player has to do
     * @param experienceGained        the number of experience points you get when you fulfill the
     *                                quest
     * @param objectiveForFulfillment the number of objectives you must complete to fulfill the
     *                                quest
     * @param completionActionType    TThe type of action that should be taken when this quest
     *                                complete
     * @param completionActionParam   The parameter for the completion action
     * @param startDate               The first day that this quest is available
     * @param endDate                 This last day that this quest is available
     *
     */
    QuestsForTest(int questID, String questTitle, String questDescription, GameLocationDTO gameLocation,
                  int experienceGained, int objectiveForFulfillment, QuestCompletionActionType completionActionType,
                  QuestCompletionActionParameter completionActionParam, Date startDate, Date endDate, Boolean isEasterEgg)
    {
        this.questID = questID;
        this.questTitle = questTitle;
        this.questDescription = questDescription;
        this.gameLocation = gameLocation;
        this.experienceGained = experienceGained;
        this.objectiveForFulfillment = objectiveForFulfillment;
        this.completionActionType = completionActionType;
        this.completionActionParameter = completionActionParam;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isEasterEgg = isEasterEgg;
    }

    /**
     * @return the number of objectives you must complete to fulfill the quest
     */
    public int getObjectiveForFulfillment()
    {
        return objectiveForFulfillment;
    }

    /**
     * @return the number of experience points you get for fulfilling the quest
     */
    public int getExperienceGained()
    {
        return experienceGained;
    }

    /**
     * @return the name of the map that contains the trigger point for this
     * quest
     */
    public String getMapName()
    {
        return gameLocation.getMapName();
    }

    /**
     * @return the position of the trigger point for this quest
     */
    public Position getPosition()
    {
        return gameLocation.getPosition();
    }

    /**
     * @return the questDescription
     */
    public String getQuestDescription()
    {
        return questDescription;
    }

    /**
     * @return the questID
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return the completion action type for this quest
     */
    public QuestCompletionActionType getCompletionActionType()
    {

        return completionActionType;
    }

    /**
     * @return the parameter describing the details of the completion action for
     * this quest
     */
    public QuestCompletionActionParameter getCompletionActionParameter()
    {
        return completionActionParameter;
    }

    /**
     * @return this quest's title
     */
    public String getQuestTitle()
    {
        return questTitle;
    }

    /**
     * @return this quest's start date
     */
    public Date getStartDate()
    {
        return startDate;
    }

    /**
     * @return this quest's end date
     */
    public Date getEndDate()
    {
        return endDate;
    }

    public Boolean getIsEasterEgg()
    {
        return isEasterEgg;
    }

}
