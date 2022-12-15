package edu.ship.engr.shipsim.datatypes;

import edu.ship.engr.shipsim.criteria.CriteriaIntegerDTO;
import edu.ship.engr.shipsim.criteria.CriteriaStringDTO;
import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.criteria.ObjectiveCompletionCriteria;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;

/**
 * Creates objectives for the DB
 *
 * @author merlin
 */
public enum ObjectivesForProduction
{

    /****************************************************/
    /* Onramping Objectives */
    /****************************************************/

    /**
     *
     */
    ONRAMPING_CHAT_WITH_SORTING_HAT(1,
            "Use the chat window to say something to the Red Hat",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(),
            2, ObjectiveCompletionType.CHAT, new CriteriaStringDTO("Sorting Big Red")),
    /**
     *
     */
    ONRAMPING_PRESS_Q(2, "Press the Q key to see your active quests and objectives",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.KEYSTROKE,
            new CriteriaStringDTO("q")),
    /**
     *
     */
    ONRAMPING_MOVE_FORWARD(3, "Press up arrow to move up",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("Up")),
    /**
     *
     */
    ONRAMPING_MOVE_BACKWARD(4, "Press down arrow to move down",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("Down")),
    /**
     *
     */
    ONRAMPING_MOVE_LEFT(5, "Press left arrow to move left",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("Left")),
    /**
     *
     */
    ONRAMPING_MOVE_RIGHT(6, "Press right arrow to move right",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("Right")),

    /**
     *
     */
    ONRAMPING_TERMINAL_HELP(7, "Type 'help' in the terminal",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.TERMINAL, new CriteriaStringDTO("help")),

    /****************************************************/

    /**
     * Onramping XP BUFF
     */
    ONRAMPING_XP_BUFF(8, "Use 'E' to interact with the XP potion on the left bookcase",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.INTERACT,
            new CriteriaIntegerDTO(InteractableItemsForTest.XP_BUFF.getItemID())),

    /**
     *
     */
    ONRAMPING_INTERACTABLE(9, "Press 'E' to interact with the fireplace",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.INTERACT,
            new CriteriaIntegerDTO(InteractableItemsForTest.FIREPLACE.getItemID())),

    /**
     *
     */
    ONRAMPING_OPEN_CLOSET(10, "Press 'I' to open your closet to change clothing",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("i")),

    /**
     *
     */
    ONRAMPING_PRESS_U(11, "Press 'U' to view the shop",
            QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.KEYSTROKE, new CriteriaStringDTO("u")),
    /**
     * Find Tea Recipe and Ingredients Objectives
     */
    FIND_TEA_RECIPE(1, "Rumor has it the recipe is pinned to a tree nearby.",
            QuestsForTest.RECIPE_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.INTERACT,
            new CriteriaIntegerDTO(InteractableItemsForTest.TEA_RECIPE.getItemID())),

    /**
     *
     */
    FIND_FLOWER_1(1, "Red Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1,
            ObjectiveCompletionType.INTERACT,
            new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_1.getItemID())),

    /**
     *
     */
    FIND_FLOWER_2(2, "Purple Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(),
            1, ObjectiveCompletionType.INTERACT,
            new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_2.getItemID())),

    /**
     *
     */
    FIND_FLOWER_3(3, "Pink Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1,
            ObjectiveCompletionType.INTERACT,
            new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_3.getItemID())),

    /**
     *
     */
    FIND_FLOWER_4(4, "Yellow Flower", QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(),
            1, ObjectiveCompletionType.INTERACT,
            new CriteriaIntegerDTO(InteractableItemsForTest.TEA_FLOWER_4.getItemID())),

    /**
     *
     */
    GIVE_TEACHER_TEA(5,
            "You got the ingredients for the tea! Go give them to TEAcher for his cup of tea!",
            QuestsForTest.COLLECT_TEA_INGREDIENTS.getQuestID(), 1,
            ObjectiveCompletionType.MOVEMENT,
            new GameLocationDTO("quad.tmx", new Position(49, 36))),

    /**
     *
     */
    EXPLORING_FIND_REC_CENTER(1, "Find the Rec Center",
            QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("recCenter.tmx",
            new Position(21, 4))),
    /**
     *
     */
    EXPLORING_FIND_LIBRARY(2, "Find the Library",
            QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("library.tmx",
            new Position(24, 48))),
    /**
     *
     */
    EXPLORING_FIND_SECRET_ROOM(3, "Find the Secret Room!",
            QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2,
            ObjectiveCompletionType.MOVEMENT,
            new GameLocationDTO("recCenter.tmx", new Position(13, 22))),


    /**
     * Meet The Tutor Objective 1 for Tutor Quest: say "tutor" to the tutor NPC
     */
    TALK_WITH_DIGITAL_TUTOR(1,
            "Find the NPC tutor in the Study Hall, and say 'Tutor' to greet them.",
            QuestsForTest.MEET_NPC_TUTOR_QUEST.getQuestID(),
            5, ObjectiveCompletionType.CHAT, new CriteriaStringDTO("Tutor")),

    /**
     * Riddle #1
     */
    RIDDLE_1(1,
            "Stand in front of the monument to the mythical wizard of the Comp. Sci./Eng. Dept.",
            QuestsForTest.RIDDLE_QUEST.getQuestID(),
            2, ObjectiveCompletionType.MOVEMENT,
            new GameLocationDTO("current.tmx", new Position(19, 46))),

    /**
     * Riddle #2
     */
    RIDDLE_2(2,
            "Across from the place of bought knowledge, lies a room of red relaxation. Come in from the south.",
            QuestsForTest.RIDDLE_QUEST.getQuestID(),
            2, ObjectiveCompletionType.MOVEMENT,
            new GameLocationDTO("cub.tmx", new Position(40, 86))),

    /**
     * Riddle #3
     */
    RIDDLE_3(3,
            "As you walk into his domain, be sure to have read the FAQ before you ask any stupid questions.",
            QuestsForTest.RIDDLE_QUEST.getQuestID(),
            2, ObjectiveCompletionType.MOVEMENT,
            new GameLocationDTO("mct1.tmx", new Position(53, 48))),


    SECRET_WORD(1,
            "There is a tree in the quad that is not quite like the others, check it out.",
            QuestsForTest.ROCK_PAPER_SCISSORS.getQuestID(),
            2, ObjectiveCompletionType.MOVEMENT,
            new GameLocationDTO("quad.tmx", new Position(118, 74))),

    SAY_IT_OUT_LOUD(2,
            "Use the secret password you learned inside of the 'current' map. Who knows what will happen...",
            QuestsForTest.ROCK_PAPER_SCISSORS.getQuestID(),
            2, ObjectiveCompletionType.MOVEMENT, new GameLocationDTO("current.tmx",
            new Position(24, 15)));


    private int objectiveID;
    private String objectiveDescription;
    private int questID;
    private int experiencePointsGained;
    private ObjectiveCompletionType completionType;
    private ObjectiveCompletionCriteria completionCriteria;

    /**
     * Constructor for Objectives Enum
     *
     * @param objectiveID            this objective's unique ID
     * @param objectiveDescription   what the player has to do
     * @param questID                the ID of the quest that contains this objective
     * @param experiencePointsGained the number of experience points the player gets
     *                               when he completes the objective
     * @param completionType         the method the player must use to complete this
     *                               objective
     * @param signatureSpecification the rules about who can sign for an outside
     *                               objective
     */
    ObjectivesForProduction(int objectiveID, String objectiveDescription, int questID,
                            int experiencePointsGained,
                            ObjectiveCompletionType completionType,
                            ObjectiveCompletionCriteria signatureSpecification)
    {
        this.objectiveID = objectiveID;
        this.objectiveDescription = objectiveDescription;
        this.questID = questID;
        this.experiencePointsGained = experiencePointsGained;
        this.completionType = completionType;
        this.completionCriteria = signatureSpecification;
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

    /**
     * @return the number of points you get for completing the objective
     */
    public int getExperiencePointsGained()
    {
        return experiencePointsGained;
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
     * @return the questID
     */
    public int getQuestID()
    {
        return questID;
    }

}
