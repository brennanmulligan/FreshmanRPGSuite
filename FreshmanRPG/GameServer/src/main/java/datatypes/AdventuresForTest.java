package datatypes;

import criteria.AdventureCompletionCriteria;
import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.GameLocationDTO;
import dataENUM.AdventureCompletionType;
import datatypes.InteractableItemsForTest;
import datatypes.Position;
import datatypes.QuestsForTest;

/**
 * Creates adventures for the DB
 *
 * @author merlin
 */
public enum AdventuresForTest
{
	/**
	 *
	 */
	QUEST1_ADVENTURE_1(1, "Quest 1: Adventure 1: MOVE!!!!", 1, 1, AdventureCompletionType.MOVEMENT,
			new GameLocationDTO("theGreen.tmx", new Position(PlayersForTest.MERLIN.getPosition().getRow() + 1,
					PlayersForTest.MERLIN.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST1_ADVENTURE2(2, "Quest 1: Adventure Description 2", 1, 2, AdventureCompletionType.CHAT,
			new CriteriaStringDTO("QuizBot")),
	/**
	 *
	 */
	QUEST2_ADVENTURE1(1, "Quest 2: Adventure Description 1", 2, 3, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Department Secretary")),
	/**
	 *
	 */
	QUEST2_ADVENTURE2(2, "Quest 2: Adventure Description 2", 2, 4, AdventureCompletionType.MOVEMENT,
			new GameLocationDTO("theGreen.tmx", new Position(4, 15))),
	/**
	 *
	 */
	QUEST2_ADVENTURE3(3, "Quest 2: Adventure Description 3", 2, 3, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST2_ADVENTURE4(4, "Quest 2: Adventure Description 4", 2, 33, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST3_ADVENTURE1(1, "Adventure 1 for Quest 3", 3, 5, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST3_ADVENTURE2(2, "Adventure 2 for Quest 3", 3, 5, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST3_ADVENTURE3(3, "Adventure 3 for Quest 3", 3, 5, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST1_ADVENTURE3(3, "One more Adventure", 1, 3, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST4_ADVENTURE1(1, "Quest 4 Adventure", 4, 5, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST4_ADVENTURE2(2, "Quest 4 Adventure 2", 4, 5, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST4_ADVENTURE3(3, "Quest 4 Adventure 3", 4, 5, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST4_ADVENTURE4(4, "Quest 4 Adventure 4", 4, 5, AdventureCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),

	/**
	 *
	 */
	QUEST5_ADVENTURE1(1, "Quest 5 Adventure 1", 5, 1, AdventureCompletionType.CHAT, new CriteriaStringDTO("QuizBot")),
	/**
	 *
	 */
	QUEST6_ADVENTURE_1(1, "Quest 6: Adventure 1: MOVE!!!!", 6, 1, AdventureCompletionType.MOVEMENT,
			new GameLocationDTO("theGreen.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 1,
					PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST6_ADVENTURE_2(2, "Quest 6: Adventure 2: MOVE!!!!", 6, 2, AdventureCompletionType.MOVEMENT,
			new GameLocationDTO("theGreen.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 2,
					PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST7_ADVENTURE_1(1, "Quest 7: Adventure 1: MOVE!!!!", 7, 1, AdventureCompletionType.MOVEMENT,
			new GameLocationDTO("theGreen.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 1,
					PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST7_ADVENTURE_2(2, "Quest 7: Adventure 2: MOVE!!!!", 7, 0, AdventureCompletionType.MOVEMENT,
			new GameLocationDTO("theGreen.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 2,
					PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST8_ADVENTURE_1(1, "Quest 8: Adventure 1: I'm dead", 8, 0, AdventureCompletionType.MOVEMENT,
			new GameLocationDTO("theGreen.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 3,
					PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST8_ADVENTURE_2(2, "Quest 8: Adventure 2: Get points!", 8, 1, AdventureCompletionType.KNOWLEDGE_POINTS,
			new CriteriaIntegerDTO(5)),
	/**
	 *
	 */
	QUEST8_ADVENTURE_3(3, "Quest 8: Adventure 3: Get points!", 8, 1, AdventureCompletionType.KNOWLEDGE_POINTS,
			new CriteriaIntegerDTO(5)),
	/**
	 *
	 */
	QUEST8_ADVENTURE_4(4, "Quest 8: Adventure 4: Get points!", 8, 1, AdventureCompletionType.KNOWLEDGE_POINTS,
			new CriteriaIntegerDTO(5)),
	/**
	 *
	 */
	QUEST10_ADVENTURE_1(1, "Quest 10: Adventure 1: Get points!", QuestsForTest.KNOWLEDGE_QUEST.getQuestID(), 1,
			AdventureCompletionType.KNOWLEDGE_POINTS, new CriteriaIntegerDTO(5)),
	/**
	 *
	 */
	QUEST11_ADVENTURE_1(1, "Quest 11: Adventure 1: Expired Quest!",
			QuestsForTest.TRIGGERED_STATE_TO_EXPIRED_STATE_QUEST.getQuestID(), 2, AdventureCompletionType.KEYSTROKE,
			new CriteriaStringDTO("e")),

	/**
	 *
	 */
	QUEST12_ADVENTURE_1(1, "Quest 12: Adventure 1: Interact!", 12, 1, AdventureCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.MSG_TEST.getItemID())),
	/**
	 *
	 */
	QUEST13_ADVENTURE_1(1, "Quest 13: Adventure 1: Interact with an item near you", 13, 1, AdventureCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.CHEST.getItemID())),
	/**
	 *
	 */
	QUEST_14_ADVENTURE_1(1, "Quest 14: Adventure 1: Make a friend!", 14, 1, AdventureCompletionType.FRIENDS, new CriteriaIntegerDTO(1)),
	/**
	 *
	 */
	QUEST_15_ADVENTURE_1(1, "Quest 15: Adventure 1: Make another friend!", 15, 1, AdventureCompletionType.FRIENDS, new CriteriaIntegerDTO(2)),

	/****************************************************/
	/* Onramping Adventures */
	/****************************************************/

	/**
	 *
	 */
	ONRAMPING_CHAT_WITH_SORTING_HAT(1, "Use the chat window to say something to the Red Hat", QuestsForTest.ONRAMPING_QUEST.getQuestID(),
			2, AdventureCompletionType.CHAT, new CriteriaStringDTO("Red Hat")),
	/**
	 *
	 */
	ONRAMPING_PRESS_Q(2, "Press the Q key to see your active quests and adventures",
			QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2, AdventureCompletionType.KEYSTROKE,
			new CriteriaStringDTO("q")),
	/**
	 *
	 */
	ONRAMPING_MOVE_FORWARD(3, "Press up arrow to move up", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			AdventureCompletionType.KEYSTROKE, new CriteriaStringDTO("Up")),
	/**
	 *
	 */
	ONRAMPING_MOVE_BACKWARD(4, "Press down arrow to move down", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			AdventureCompletionType.KEYSTROKE, new CriteriaStringDTO("Down")),
	/**
	 *
	 */
	ONRAMPING_MOVE_LEFT(5, "Press left arrow to move left", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			AdventureCompletionType.KEYSTROKE, new CriteriaStringDTO("Left")),
	/**
	 *
	 */
	ONRAMPING_MOVE_RIGHT(6, "Press right arrow to move right", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			AdventureCompletionType.KEYSTROKE, new CriteriaStringDTO("Right")),

	/**
	 *
	 */
	ONRAMPING_TERMINAL_MAN(7, "Type 'man' in the terminal", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			AdventureCompletionType.TERMINAL, new CriteriaStringDTO("man")),

	/****************************************************/

	/**
	 * Onramping XP BUFF
	 */
	ONRAMPING_XP_BUFF(8, "Use 'E' to interact with the XP potion on the left bookcase", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			AdventureCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.XP_BUFF.getItemID())),

	/**
	 *
	 */
	ONRAMPING_INTERACTABLE(9, "Press 'E' to interact with the fireplace", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			AdventureCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.FIREPLACE.getItemID())),

	/**
	 * Find Tea Recipe and Ingredients Adventures
	 */
	FIND_TEA_RECIPE(1, "Rumor has it the recipe is pinned to a tree nearby.", QuestsForTest.RECIPE_QUEST.getQuestID(), 2,
			AdventureCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.TEA_RECIPE.getItemID())),

	/**
	 *
	 */
	FIND_FLOWER_1(1, "Red Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1, AdventureCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_1.getItemID())),

	/**
	 *
	 */
	FIND_FLOWER_2(2, "Purple Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1, AdventureCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_2.getItemID())),

	/**
	 *
	 */
	FIND_FLOWER_3(3, "Pink Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1, AdventureCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_3.getItemID())),

	/**
	 *
	 */
	FIND_FLOWER_4(4, "Yellow Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1, AdventureCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_4.getItemID())),

	/**
	 *
	 */
	GIVE_MOONEY_TEA(5, "You got the ingredients for the tea! Go give them to Dr. Mooney for his cup of tea!", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1, AdventureCompletionType.MOVEMENT, new GameLocationDTO("theGreen.tmx", new Position(94, 9))),

	/**
	 *
	 */
	EXPLORING_FIND_QUIZNASIUM(1, "Find the Quiznasium", QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2,
			AdventureCompletionType.MOVEMENT, new GameLocationDTO("quiznasium.tmx", new Position(4, 23))),
	/**
	 *
	 */
	EXPLORING_FIND_STUDY_HALL(2, "Find the Study Hall", QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2,
			AdventureCompletionType.MOVEMENT, new GameLocationDTO("homework.tmx", new Position(48, 24))),
	/**
	 *
	 */
	EXPLORING_FIND_SECRET_ROOM(3, "Find the Secret Room!", QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2,
			AdventureCompletionType.MOVEMENT, new GameLocationDTO("Quiznasium.tmx", new Position(13, 22))),


	/**
	 * Meet The Tutor Adventure 1 for Tutor Quest: say "tutor" to the tutor NPC
	 */
	TALK_WITH_DIGITAL_TUTOR(1, "Find the NPC tutor in the Study Hall, and say 'Tutor' to greet them.", QuestsForTest.MEET_NPC_TUTOR_QUEST.getQuestID(),
			5, AdventureCompletionType.CHAT, new CriteriaStringDTO("Tutor")),

	/**
	 *
	 */
	TALK_WITH_REAL_TUTOR(2, "Go To Room MCT 164 and talk to the real tutor during their availability hours!", QuestsForTest.MEET_REAL_LIFE_TUTOR_QUEST.getQuestID(), 5,
			AdventureCompletionType.REAL_LIFE, new CriteriaStringDTO("Met With Tutor")),

	/**
	 * Riddle #1
	 */
	RIDDLE_1(1, "Stand in front of the monument to the mythical wizard of the Comp. Sci./Eng. Dept.", QuestsForTest.RIDDLE_QUEST.getQuestID(),
			2, AdventureCompletionType.MOVEMENT, new GameLocationDTO("current.tmx", new Position(19, 46))),

	/**
	 * Riddle #2
	 */
	RIDDLE_2(2, "Across from the place of bought knowledge, lies a room of red relaxation. Come in from the south.", QuestsForTest.RIDDLE_QUEST.getQuestID(),
			2, AdventureCompletionType.MOVEMENT, new GameLocationDTO("cub.tmx", new Position(40, 86))),

	/**
	 * Riddle #3
	 */
	RIDDLE_3(3,"As you walk into his domain, be sure to have read the FAQ before you ask any stupid questions.", QuestsForTest.RIDDLE_QUEST.getQuestID(),
			2, AdventureCompletionType.MOVEMENT, new GameLocationDTO("mct1.tmx", new Position(53, 48))),


	SECRET_WORD(1,"There is a tree in the green that is not quite like the others, check it out.", QuestsForTest.ROCK_PAPER_SCISSORS.getQuestID(),
			2, AdventureCompletionType.MOVEMENT, new GameLocationDTO("theGreen.tmx", new Position(87, 29))),

	SAY_IT_OUT_LOUD(2,"Use the secret password you learned inside of the 'current' map. Who knows what will happen...", QuestsForTest.ROCK_PAPER_SCISSORS.getQuestID(),
			2, AdventureCompletionType.MOVEMENT, new GameLocationDTO("current.tmx", new Position(24, 15)));


	private int adventureID;
	private String adventureDescription;
	private int questID;
	private int experiencePointsGained;
	private AdventureCompletionType completionType;
	private AdventureCompletionCriteria completionCriteria;

	/**
	 * Constructor for Adventures Enum
	 *
	 * @param adventureID this adventure's unique ID
	 * @param adventureDescription what the player has to do
	 * @param questID the ID of the quest that contains this adventure
	 * @param experiencePointsGained the number of experience points the player gets
	 *        when he completes the adventure
	 * @param completionType the method the player must use to complete this
	 *        adventure
	 * @param signatureSpecification the rules about who can sign for an outside
	 *        adventure
	 */
	AdventuresForTest(int adventureID, String adventureDescription, int questID, int experiencePointsGained,
					  AdventureCompletionType completionType, AdventureCompletionCriteria signatureSpecification)
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.questID = questID;
		this.experiencePointsGained = experiencePointsGained;
		this.completionType = completionType;
		this.completionCriteria = signatureSpecification;
	}

	/**
	 * @return the adventureDescription
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return the adventureID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the number of points you get for completing the adventure
	 */
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * @return the questID
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return the rules about who confirms completion of an outside quest
	 */
	public AdventureCompletionCriteria getCompletionCriteria()
	{
		return completionCriteria;
	}

	/**
	 * @return the type of action the player uses to complete this adventure
	 */
	public AdventureCompletionType getCompletionType()
	{
		return completionType;
	}

}
