package datatypes;

import criteria.*;
import dataENUM.ObjectiveCompletionType;

/**
 * Creates objectives for the DB
 *
 * @author merlin
 */
public enum ObjectivesForTest
{
	/**
	 *
	 */
	QUEST1_OBJECTIVE_1(1, "Quest 1: Objective 1: MOVE!!!!", 1, 1, ObjectiveCompletionType.MOVEMENT,
			new GameLocationDTO("quad.tmx", new Position(PlayersForTest.MERLIN.getPosition().getRow() + 1,
					PlayersForTest.MERLIN.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST1_OBJECTIVE2(2, "Quest 1: Objective Description 2", 1, 2, ObjectiveCompletionType.CHAT,
			new CriteriaStringDTO("QuizBot")),
	/**
	 *
	 */
	QUEST2_OBJECTIVE1(1, "Quest 2: Objective Description 1", 2, 3, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Department Secretary")),
	/**
	 *
	 */
	QUEST2_OBJECTIVE2(2, "Quest 2: Objective Description 2", 2, 4, ObjectiveCompletionType.MOVEMENT,
			new GameLocationDTO("quad.tmx", new Position(4, 15))),
	/**
	 *
	 */
	QUEST2_OBJECTIVE3(3, "Quest 2: Objective Description 3", 2, 3, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST2_OBJECTIVE4(4, "Quest 2: Objective Description 4", 2, 33, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST3_OBJECTIVE1(1, "Objective 1 for Quest 3", 3, 5, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST3_OBJECTIVE2(2, "Objective 2 for Quest 3", 3, 5, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST3_OBJECTIVE3(3, "Objective 3 for Quest 3", 3, 5, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST1_OBJECTIVE3(3, "One more Objective", 1, 3, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST4_OBJECTIVE1(1, "Quest 4 Objective", 4, 5, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST4_OBJECTIVE2(2, "Quest 4 Objective 2", 4, 5, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST4_OBJECTIVE3(3, "Quest 4 Objective 3", 4, 5, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),
	/**
	 *
	 */
	QUEST4_OBJECTIVE4(4, "Quest 4 Objective 4", 4, 5, ObjectiveCompletionType.REAL_LIFE,
			new CriteriaStringDTO("Lab Instructor")),

	/**
	 *
	 */
	QUEST5_OBJECTIVE1(1, "Quest 5 Objective 1", 5, 1, ObjectiveCompletionType.CHAT, new CriteriaStringDTO("QuizBot")),
	/**
	 *
	 */
	QUEST6_OBJECTIVE_1(1, "Quest 6: Objective 1: MOVE!!!!", 6, 1, ObjectiveCompletionType.MOVEMENT,
			new GameLocationDTO("quad.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 1,
					PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST6_OBJECTIVE_2(2, "Quest 6: Objective 2: MOVE!!!!", 6, 2, ObjectiveCompletionType.MOVEMENT,
			new GameLocationDTO("quad.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 2,
					PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST7_OBJECTIVE_1(1, "Quest 7: Objective 1: MOVE!!!!", 7, 1, ObjectiveCompletionType.MOVEMENT,
			new GameLocationDTO("quad.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 1,
					PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST7_OBJECTIVE_2(2, "Quest 7: Objective 2: MOVE!!!!", 7, 0, ObjectiveCompletionType.MOVEMENT,
			new GameLocationDTO("quad.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 2,
					PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST8_OBJECTIVE_1(1, "Quest 8: Objective 1: I'm dead", 8, 0, ObjectiveCompletionType.MOVEMENT,
			new GameLocationDTO("quad.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 3,
					PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 *
	 */
	QUEST8_OBJECTIVE_2(2, "Quest 8: Objective 2: Get points!", 8, 1, ObjectiveCompletionType.DOUBLOONS,
			new CriteriaIntegerDTO(5)),
	/**
	 *
	 */
	QUEST8_OBJECTIVE_3(3, "Quest 8: Objective 3: Get points!", 8, 1, ObjectiveCompletionType.DOUBLOONS,
			new CriteriaIntegerDTO(5)),
	/**
	 *
	 */
	QUEST8_OBJECTIVE_4(4, "Quest 8: Objective 4: Get points!", 8, 1, ObjectiveCompletionType.DOUBLOONS,
			new CriteriaIntegerDTO(5)),
	/**
	 *
	 */
	QUEST10_OBJECTIVE_1(1, "Quest 10: Objective 1: Get points!", QuestsForTest.DOUBLOON_QUEST.getQuestID(), 1,
			ObjectiveCompletionType.DOUBLOONS, new CriteriaIntegerDTO(5)),
	/**
	 *
	 */
	QUEST11_OBJECTIVE_1(1, "Quest 11: Objective 1: Expired Quest!",
			QuestsForTest.TRIGGERED_STATE_TO_EXPIRED_STATE_QUEST.getQuestID(), 2, ObjectiveCompletionType.KEYSTROKE,
			new CriteriaStringDTO("e")),

	/**
	 *
	 */
	QUEST12_OBJECTIVE_1(1, "Quest 12: Objective 1: Interact!", 12, 1, ObjectiveCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.MSG_TEST.getItemID())),
	/**
	 *
	 */
	QUEST13_OBJECTIVE_1(1, "Quest 13: Objective 1: Interact with an item near you", 13, 1, ObjectiveCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.CHEST.getItemID())),
	/**
	 *
	 */
	QUEST_14_OBJECTIVE_1(1, "Quest 14: Objective 1: Make a friend!", 14, 1, ObjectiveCompletionType.FRIENDS, new CriteriaIntegerDTO(1)),
	/**
	 *
	 */
	QUEST_15_OBJECTIVE_1(1, "Quest 15: Objective 1: Make another friend!", 15, 1, ObjectiveCompletionType.FRIENDS, new CriteriaIntegerDTO(2)),

	QUEST_17_OBJECTIVE_1(1, "Press 'U' to view the shop", 17, 2,
			ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("u")),

	/****************************************************/
	/* Onramping Objectives */
	/****************************************************/

	/**
	 *
	 */
	ONRAMPING_CHAT_WITH_SORTING_HAT(1, "Use the chat window to say something to the Red Hat", QuestsForTest.ONRAMPING_QUEST.getQuestID(),
			2, ObjectiveCompletionType.CHAT, new CriteriaStringDTO("Red Hat")),
	/**
	 *
	 */
	ONRAMPING_PRESS_Q(2, "Press the Q key to see your active quests and objectives",
			QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2, ObjectiveCompletionType.KEYSTROKE,
			new CriteriaStringDTO("q")),
	/**
	 *
	 */
	ONRAMPING_MOVE_FORWARD(3, "Press up arrow to move up", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("Up")),
	/**
	 *
	 */
	ONRAMPING_MOVE_BACKWARD(4, "Press down arrow to move down", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("Down")),
	/**
	 *
	 */
	ONRAMPING_MOVE_LEFT(5, "Press left arrow to move left", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("Left")),
	/**
	 *
	 */
	ONRAMPING_MOVE_RIGHT(6, "Press right arrow to move right", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("Right")),

	/**
	 *
	 */
	ONRAMPING_TERMINAL_MAN(7, "Type 'man' in the terminal", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.TERMINAL, new CriteriaStringDTO("man")),

	/****************************************************/

	/**
	 * Onramping XP BUFF
	 */
	ONRAMPING_XP_BUFF(8, "Use 'E' to interact with the XP potion on the left bookcase", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.XP_BUFF.getItemID())),

	/**
	 *
	 */
	ONRAMPING_INTERACTABLE(9, "Press 'E' to interact with the fireplace", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.FIREPLACE.getItemID())),

	/**
	 *
	 */
	ONRAMPING_OPEN_CLOSET(10,"Press 'I' to open your closet to change clothing", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("i")),

	/**
	 *
	 */
	ONRAMPING_PRESS_U(11, "Press 'U' to view the shop", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("u")),

	/**
	 * Find Tea Recipe and Ingredients Objectives
	 */
	FIND_TEA_RECIPE(1, "Rumor has it the recipe is pinned to a tree nearby.", QuestsForTest.RECIPE_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.TEA_RECIPE.getItemID())),

	/**
	 *
	 */
	FIND_FLOWER_1(1, "Red Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1, ObjectiveCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_1.getItemID())),

	/**
	 *
	 */
	FIND_FLOWER_2(2, "Purple Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1, ObjectiveCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_2.getItemID())),

	/**
	 *
	 */
	FIND_FLOWER_3(3, "Pink Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1, ObjectiveCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_3.getItemID())),

	/**
	 *
	 */
	FIND_FLOWER_4(4, "Yellow Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1, ObjectiveCompletionType.INTERACT, new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_4.getItemID())),

	/**
	 *
	 */
	GIVE_TEACHER_TEA(5, "You got the ingredients for the tea! Go give them to TEAcher for his cup of tea!", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1, ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("quad.tmx", new Position(49, 36))),

	/**
	 *
	 */
	EXPLORING_FIND_REC_CENTER(1, "Find the Rec Center", QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("recCenter.tmx", new Position(4, 23))),
	/**
	 *
	 */
	EXPLORING_FIND_LIBRARY(2, "Find the Library", QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("library.tmx",
			new Position(24, 48))),
	/**
	 *
	 */
	EXPLORING_FIND_SECRET_ROOM(3, "Find the Secret Room!", QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2,
			ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("recCenter.tmx", new Position(13, 22))),


	/**
	 * Meet The Tutor Objective 1 for Tutor Quest: say "tutor" to the tutor NPC
	 */
	TALK_WITH_DIGITAL_TUTOR(1, "Find the NPC tutor in the Study Hall, and say 'Tutor' to greet them.", QuestsForTest.MEET_NPC_TUTOR_QUEST.getQuestID(),
			5, ObjectiveCompletionType.CHAT, new CriteriaStringDTO("Tutor")),

	/**
	 *
	 */
	TALK_WITH_REAL_TUTOR(2, "Go To Room MCT 164 and talk to the real tutor during their availability hours!", QuestsForTest.MEET_REAL_LIFE_TUTOR_QUEST.getQuestID(), 5,
			ObjectiveCompletionType.REAL_LIFE, new CriteriaStringDTO("Met With Tutor")),

	/**
	 * Riddle #1
	 */
	RIDDLE_1(1, "Stand in front of the monument to the mythical wizard of the Comp. Sci./Eng. Dept.", QuestsForTest.RIDDLE_QUEST.getQuestID(),
			2, ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("current.tmx", new Position(19, 46))),

	/**
	 * Riddle #2
	 */
	RIDDLE_2(2, "Across from the place of bought knowledge, lies a room of red relaxation. Come in from the south.", QuestsForTest.RIDDLE_QUEST.getQuestID(),
			2, ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("cub.tmx", new Position(40, 86))),

	/**
	 * Riddle #3
	 */
	RIDDLE_3(3,"As you walk into his domain, be sure to have read the FAQ before you ask any stupid questions.", QuestsForTest.RIDDLE_QUEST.getQuestID(),
			2, ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("mct1.tmx", new Position(53, 48))),


	SECRET_WORD(1,"There is a tree in the quad that is not quite like the others, check it out.", QuestsForTest.ROCK_PAPER_SCISSORS.getQuestID(),
			2, ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("quad.tmx", new Position(118, 74))),

	SAY_IT_OUT_LOUD(2,"Use the secret password you learned inside of the 'current' map. Who knows what will happen...", QuestsForTest.ROCK_PAPER_SCISSORS.getQuestID(),
			2, ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("current.tmx", new Position(24, 15))),

	/****************************************************/
	/* NPC response objectives */
	/****************************************************/

	/**
	 *
	 */
	RESPONSE_FROM_MOWREY_NPC(1, "Say hi to the Mowrey NPC", QuestsForTest.TEST_CHAT_RESPONSE_QUEST.getQuestID(),
			2, ObjectiveCompletionType.CHAT_RECEIVED,
			new NPCResponseDTO("Hello, student", "MowreyInfoPerson"));

	private int objectiveID;
	private String objectiveDescription;
	private int questID;
	private int experiencePointsGained;
	private ObjectiveCompletionType completionType;
	private ObjectiveCompletionCriteria completionCriteria;

	/**
	 * Constructor for Objectives Enum
	 *
	 * @param objectiveID this objective's unique ID
	 * @param objectiveDescription what the player has to do
	 * @param questID the ID of the quest that contains this objective
	 * @param experiencePointsGained the number of experience points the player gets
	 *        when he completes the objective
	 * @param completionType the method the player must use to complete this
	 *        objective
	 * @param signatureSpecification the rules about who can sign for an outside
	 *        objective
	 */
	ObjectivesForTest(int objectiveID, String objectiveDescription, int questID, int experiencePointsGained,
					  ObjectiveCompletionType completionType, ObjectiveCompletionCriteria signatureSpecification)
	{
		this.objectiveID = objectiveID;
		this.objectiveDescription = objectiveDescription;
		this.questID = questID;
		this.experiencePointsGained = experiencePointsGained;
		this.completionType = completionType;
		this.completionCriteria = signatureSpecification;
	}

	/**
	 * @return the objectiveDescription
	 */
	public String getObjectiveDescription()
	{
		return objectiveDescription;
	}

	/**
	 * @return the objectiveID
	 */
	public int getObjectiveID()
	{
		return objectiveID;
	}

	/**
	 * @return the number of points you get for completing the objective
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
	public ObjectiveCompletionCriteria getCompletionCriteria()
	{
		return completionCriteria;
	}

	/**
	 * @return the type of action the player uses to complete this objective
	 */
	public ObjectiveCompletionType getCompletionType()
	{
		return completionType;
	}

}
