package datatypes;

import criteria.GameLocationDTO;
import criteria.QuestCompletionActionParameter;
import criteria.QuestListCompletionParameter;
import dataENUM.QuestCompletionActionType;
import datasource.PlayerTableDataGateway;

import java.util.*;

/**
 * Creates objectives for the DB
 *
 * @author merlin
 *
 */
public enum QuestsForProduction
{

	/**
	 * The real opening quest
	 */
	ONRAMPING_QUEST(100, "Introductory Quest",
			"Welcome!  For your first quest, you need to learn a little bit about this world.  Press Q to see what you need to do.  "
					+ "Double clicking on a quest in the quest screen will show you its objectives.",
			PlayerTableDataGateway.INITIAL_GAME_LOCATION, 5, 11,
			QuestCompletionActionType.TELEPORT,
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
			new GregorianCalendar(9999, Calendar.MARCH, 21).getTime());



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
	QuestsForProduction(int questID, String questTitle, String questDescription, GameLocationDTO gameLocation,
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
