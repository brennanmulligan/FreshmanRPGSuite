package datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import criteria.QuestListCompletionParameter;

import criteria.GameLocationDTO;
import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datasource.PlayerTableDataGateway;

/**
 * Creates objectives for the DB
 *
 * @author merlin
 *
 */
public enum QuestsForTest
{


	ONE_BIG_QUEST(1, "t0", "Quest 1", new GameLocationDTO("quad.tmx", new Position(92, 7)), 5, 2, QuestCompletionActionType.TELEPORT, new GameLocationDTO("quad.tmx", new Position(3, 3)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	THE_OTHER_QUEST(2, "t1", "Quest 2", new GameLocationDTO("quad.tmx", new Position(92, 7)), 4, 2, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	ONE_SAME_LOCATION_QUEST(3, "t2", "Quest 3", new GameLocationDTO("quad.tmx", new Position(92, 7)), 3, 2, QuestCompletionActionType.TELEPORT, new GameLocationDTO("quad.tmx", new Position(3, 3)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */

	THE_LITTLE_QUEST(4, "t3", "Quest 4", new GameLocationDTO("quad.tmx", new Position(2, 32)), 5, 1, QuestCompletionActionType.TELEPORT, new GameLocationDTO("quad.tmx", new Position(3, 3)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	CHAT_TO_AN_NPC_QUEST(5, "t4", "Quest 5", new GameLocationDTO("quad.tmx", new Position(0, 0)), 5, 1, QuestCompletionActionType.TELEPORT, new GameLocationDTO("quad.tmx", new Position(3, 3)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	TELEPORT_QUEST(6, "t5", "Teleporting Quest", new GameLocationDTO("quad.tmx", new Position(2, 32)), 1, 1, QuestCompletionActionType.TELEPORT, new GameLocationDTO("sortingRoom.tmx", new Position(3, 3)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	INVALID_QUEST(7, "t6", "Quest 7", new GameLocationDTO("quad.tmx", new Position(92, 7)), 4, 2, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(2015, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	EXPIRED_QUEST(8, "t7", "Quest 8", new GameLocationDTO("quad.tmx", new Position(92, 7)), 4, 2, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(1970, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(2000, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	FINISHED_QUEST(9, "t8", "Quest 9", new GameLocationDTO("quad.tmx", new Position(92, 7)), 4, 2, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(1970, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(2000, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	DOUBLOON_QUEST(10, "Doubloon Quest", "Quest 10", new GameLocationDTO("quad.tmx", new Position(92, 7)), 5, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.MARCH, 21).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	TRIGGERED_STATE_TO_EXPIRED_STATE_QUEST(11, "Quest 11", "Quest should go from triggered to expired", new GameLocationDTO("quad.tmx", new Position(3, 3)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2016, Calendar.APRIL, 26).getTime(), new GregorianCalendar(2016, Calendar.APRIL, 26).getTime()),
	/**
	 *
	 */
	INTERACTABLE_ITEM_QUEST(12, "Quest 12", "Interactable Items should be a viable method of completing quests", new GameLocationDTO("quad.tmx", new Position(92, 7)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	INTERACTABLE_ITEM_QUEST_SUB(13, "Quest 13", "Interact with an item", new GameLocationDTO("current.tmx", new Position(8, 9)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),

	/**
	 *
	 */
	FIRST_FRIEND_QUEST(14, "Quest 14", "Make your first friend", new GameLocationDTO("current.tmx", new Position(8, 9)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	MANY_FRIENDS_QUEST(15, "Quest 15", "Make multiple friends", new GameLocationDTO("current.tmx", new Position(8, 9)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),
	/**
	 *
	 */
	TEST_CHAT_RESPONSE_QUEST(16, "Chat Response", "Get talked at by NPC", new GameLocationDTO("mowrey.tmx", new Position(29, 36)), 1, 1, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),


	/**
	 * The real opening quest
	 */
	ONRAMPING_QUEST(100, "Introductory Quest",
			"Welcome!  For your first quest, you need to learn a little bit about this world.  Press Q to see what you need to do.  "
					+ "Double clicking on a quest in the quest screen will show you its objectives.",
			PlayerTableDataGateway.INITIAL_GAME_LOCATION, 5, 9, QuestCompletionActionType.TELEPORT,
			new GameLocationDTO("quad.tmx", new Position(92, 7)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
			new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),

	/**
	 * Real quest to make them explore
	 */
	EXPLORATION_QUEST(101, "Exploration", "Explore your new school", new GameLocationDTO("sortingRoom.tmx", new Position(4, 13)), 2, 5, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),


	/**
	 * Quest for real life tutor
	 */
	MEET_REAL_LIFE_TUTOR_QUEST(102, "Meet Real Life Tutor", "Talk with the Real Tutor during their available hours", new GameLocationDTO("sortingRoom.tmx", new Position(4, 13)), 2, 5, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),


	/**
	 * Real quest for them to meet the tutor
	 */
	MEET_NPC_TUTOR_QUEST(103, "Meet The Tutor", "Talk with the NPC tutor.", new GameLocationDTO("library.tmx", new Position(1, 1)), 10, 1,
			QuestCompletionActionType.TRIGGER_QUESTS, new QuestListCompletionParameter(new ArrayList<>(Arrays.asList(MEET_REAL_LIFE_TUTOR_QUEST.getQuestID()))), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),

	/**
	 * Recipe Quest
	 */
	RECIPE_QUEST(104, "Curiouser and Curiouser!", "Find the special tea recipe in The Green.",
			new GameLocationDTO("quad.tmx", new Position(92, 7)), 2, 1, QuestCompletionActionType.NO_ACTION,
			null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
			new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),

	/**
	 * Tea Ingredients Quest
	 */
	COLLECT_TEA_INGREDIENTS(105, "Flowers for Tea", "TEAcher wants to try that tea recipe you found. Find four flowers to help him make it.",
			new GameLocationDTO("quad.tmx", new Position(92, 7)), 1, 5, QuestCompletionActionType.NO_ACTION,
			null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
			new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),

	/**
	 * Riddle Quest
	 * GameLocationDTO("library.tmx", new Position(10,3))
	 */
	RIDDLE_QUEST(106, "Solving Riddles", "You need to solve these riddles by finding to locations that they describe. Good Luck!",
			new GameLocationDTO("quad.tmx", new Position(92,7)), 10, 3, QuestCompletionActionType.NO_ACTION,
			null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
			new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),

	ROCK_PAPER_SCISSORS(107, "Roshambo", "A mysterious figure issues you a challenge, but first you must find the secret password...",
			new GameLocationDTO("quad.tmx", new Position(92, 7)), 1, 2, QuestCompletionActionType.NO_ACTION,
			null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
			new GregorianCalendar(9999, Calendar.MARCH, 21).getTime()),

	TEMP_QUEST(108, "Triggered by object", "This quest was triggered by an interacting with an object. ",
			new GameLocationDTO("quad.tmx", new Position(83, 33)), 1, 1, QuestCompletionActionType.NO_ACTION,
			null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
			new GregorianCalendar(9999, Calendar.MARCH, 21).getTime());


	private int questID;
	private String questTitle;
	private String questDescription;
	private GameLocationDTO gameLocation;
	private int experienceGained;
	private int objectiveForFulfillment;
	private QuestCompletionActionType completionActionType;
	private QuestCompletionActionParameter completionActionParameter;
	private Date startDate;
	private Date endDate;

	/**
	 * Constructor for Quests Enum
	 *
	 * @param questID
	 *            this quest's unique ID
	 * @param questTitle
	 *            this quest's title
	 * @param questDescription
	 *            what the player has to do
	 * @param experienceGained
	 *            the number of experience points you get when you fulfill the
	 *            quest
	 * @param objectiveForFulfillment
	 *            the number of objectives you must complete to fulfill the
	 *            quest
	 * @param completionActionType
	 *            TThe type of action that should be taken when this quest
	 *            complete
	 * @param completionActionParam
	 *            The parameter for the completion action
	 * @param startDate
	 *            The first day that this quest is available
	 * @param endDate
	 *            This last day that this quest is available
	 */
	QuestsForTest(int questID, String questTitle, String questDescription, GameLocationDTO gameLocation,
				  int experienceGained, int objectiveForFulfillment, QuestCompletionActionType completionActionType,
				  QuestCompletionActionParameter completionActionParam, Date startDate, Date endDate)
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
	 *         quest
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
	 *
	 * @return the parameter describing the details of the completion action for
	 *         this quest
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

}
