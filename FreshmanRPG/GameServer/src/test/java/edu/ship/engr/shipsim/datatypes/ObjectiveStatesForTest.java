package edu.ship.engr.shipsim.datatypes;

/**
 * Creates objectives for the DB
 *
 * @author merlin
 */
public enum ObjectiveStatesForTest
{
    /**
     *
     */
    PLAYER1_QUEST1_ADV1(1, 1, 1, ObjectiveStateEnum.TRIGGERED, true),
    /**
     *
     */
    PLAYER1_QUEST1_ADV2(1, 1, 2, ObjectiveStateEnum.COMPLETED, false),
    /**
     *
     */
    PLAYER1_QUEST2_ADV1(1, 2, 1, ObjectiveStateEnum.COMPLETED, false),
    /**
     *
     */
    PLAYER1_QUEST2_ADV2(1, 2, 2, ObjectiveStateEnum.COMPLETED, false),
    /**
     *
     */
    PLAYER1_QUEST2_ADV3(1, 2, 3, ObjectiveStateEnum.TRIGGERED, false),
    /**
     *
     */
    PLAYER1_QUEST3_ADV1(1, 3, 1, ObjectiveStateEnum.TRIGGERED, false),
    /**
     *
     */
    PLAYER1_QUEST3_ADV2(1, 3, 2, ObjectiveStateEnum.COMPLETED, false),
    /**
     *
     */
    PLAYER1_QUEST3_ADV3(1, 3, 3, ObjectiveStateEnum.TRIGGERED, false),
    /**
     *
     */
    PLAYER1_QUEST102_ADV2(1, 102, 2, ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    PLAYER1_QUEST105_ADV1(1, 105, 1, ObjectiveStateEnum.HIDDEN, false),
    /**
     *
     */
    PLAYER1_QUEST105_ADV2(1, 105, 2, ObjectiveStateEnum.HIDDEN, false),
    /**
     *
     */
    PLAYER1_QUEST105_ADV3(1, 105, 3, ObjectiveStateEnum.HIDDEN, false),
    /**
     *
     */
    PLAYER1_QUEST105_ADV4(1, 105, 4, ObjectiveStateEnum.HIDDEN, false),
    /**
     *
     */
    PLAYER1_QUEST105_ADV5(1, 105, 5, ObjectiveStateEnum.HIDDEN, false),
    /**
     *
     */
    PLAYER1_QUEST106_ADV1(1, 106, 1, ObjectiveStateEnum.HIDDEN, false),
    /**
     *
     */
    PLAYER1_QUEST106_ADV2(1, 106, 2, ObjectiveStateEnum.HIDDEN, false),
    /**
     *
     */
    PLAYER1_QUEST106_ADV3(1, 106, 3, ObjectiveStateEnum.HIDDEN, false),
    /**
     *
     */
    PLAYER1_QUEST100_ADV10(1, 100, 10, ObjectiveStateEnum.TRIGGERED, false),

    PLAYER1_QUEST100_ADV11(1, 100, 11, ObjectiveStateEnum.TRIGGERED, false),

    PLAYER1_QUEST17_ADV1(1, 17, 1, ObjectiveStateEnum.TRIGGERED, false),

    PLAYER1_QUEST4_ADV1(1, 107, 1, ObjectiveStateEnum.TRIGGERED, false),

    PLAYER1_QUEST4_ADV2(1, 107, 2, ObjectiveStateEnum.TRIGGERED, false),


    /**
     *
     */
    PLAYER2_QUEST1_ADV1(2, 1, 1, ObjectiveStateEnum.TRIGGERED, true),
    /**
     *
     */
    PLAYER2_QUEST1_ADV2(2, 1, 2, ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    PLAYER2_QUEST1_ADV3(2, 1, 3, ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    PLAYER2_QUEST4_ADV1(2, 4, 1, ObjectiveStateEnum.COMPLETED, false),

    /**
     *
     */
    PLAYER2_QUEST4_ADV2(2, 4, 2, ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    PLAYER2_QUEST4_ADV3(2, 4, 3, ObjectiveStateEnum.COMPLETED, false),

    /**
     *
     */
    PLAYER2_QUEST4_ADV4(2, 4, 4, ObjectiveStateEnum.TRIGGERED, false),
    /**
     *
     */
    PLAYER3_QUEST1_ADV1(3, 1, 1, ObjectiveStateEnum.COMPLETED, false),
    /**
     *
     */
    PLAYER4_QUEST3_ADV1(4, 3, 1, ObjectiveStateEnum.COMPLETED, false),
    /**
     *
     */
    PLAYER4_QUEST3_ADV2(4, 3, 2, ObjectiveStateEnum.TRIGGERED, false),
    /**
     *
     */
    PLAYER4_QUEST3_ADV3(4, 3, 3, ObjectiveStateEnum.COMPLETED, false),

    /**
     *
     */
    PLAYER7_QUEST1_ADV1(7, 1, 1, ObjectiveStateEnum.TRIGGERED, false),
    /**
     *
     */
    PLAYER7_QUEST1_ADV2(7, 1, 2, ObjectiveStateEnum.COMPLETED, false),
    /**
     *
     */
    PLAYER7_QUEST2_ADV1(7, 2, 1, ObjectiveStateEnum.HIDDEN, false),
    /**
     *
     */
    PLAYER29_QUEST1_ADV1(29, 1, 1, ObjectiveStateEnum.TRIGGERED, true),
    /**
     *
     */
    PLAYER29_QUEST1_ADV2(29, 1, 2, ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    PLAYER29_QUEST1_ADV3(29, 1, 3, ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    PLAYER29_QUEST4_ADV1(29, 4, 1, ObjectiveStateEnum.COMPLETED, false),

    /**
     *
     */
    PLAYER29_QUEST4_ADV2(29, 4, 2, ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    PLAYER29_QUEST4_ADV3(29, 4, 3, ObjectiveStateEnum.COMPLETED, false),

    /**
     *
     */
    PLAYER29_QUEST4_ADV4(29, 4, 4, ObjectiveStateEnum.TRIGGERED, false),


    /**
     *
     */
    MARTY_QUEST5_ADV1(PlayersForTest.MARTY.getPlayerID(), 5, 1, ObjectiveStateEnum.TRIGGERED, false),
    /**
     *
     */
    MARTY_QUEST11_ADV1(PlayersForTest.MARTY.getPlayerID(), 11, 1, ObjectiveStateEnum.TRIGGERED, false),
    /**
     *
     */
    HERSH_TELEPORTATION_ADV1(PlayersForTest.HERSH.getPlayerID(), QuestsForTest.TELEPORT_QUEST.getQuestID(), 1, ObjectiveStateEnum.TRIGGERED, true),
    /**
     *
     */
    HERSH_TELEPORTATION_ADV2(PlayersForTest.HERSH.getPlayerID(), QuestsForTest.TELEPORT_QUEST.getQuestID(), 2, ObjectiveStateEnum.TRIGGERED, true),
    /**
     *
     */
    HERSH_EXPIRED(PlayersForTest.HERSH.getPlayerID(), QuestsForTest.EXPIRED_QUEST.getQuestID(), 1, ObjectiveStateEnum.TRIGGERED, true),
    /**
     *
     */
    HERSH_QUEST_EXPIRED(PlayersForTest.HERSH.getPlayerID(), QuestsForTest.EXPIRED_QUEST.getQuestID(), 2, ObjectiveStateEnum.HIDDEN, true),

    /**
     *
     */
    PLAYER8_QUEST2_ADV2(8, 2, 2, ObjectiveStateEnum.TRIGGERED, false),
    /**
     *
     */
    PLAYER8_QUEST10_ADV1(PlayersForTest.GA.getPlayerID(), QuestsForTest.DOUBLOON_QUEST.getQuestID(), ObjectivesForTest.QUEST10_OBJECTIVE_1.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, true),

    // ----------------------------------------------------------------------------------------//
    // NEWBIE's onramping objectives //
    // ----------------------------------------------------------------------------------------//

    /**
     *
     */
    CHAT_WITH_RED_HAT(PlayersForTest.NEWBIE.getPlayerID(),
            ObjectivesForTest.ONRAMPING_CHAT_WITH_SORTING_HAT.getQuestID(),
            ObjectivesForTest.ONRAMPING_CHAT_WITH_SORTING_HAT.getObjectiveID(),
            ObjectiveStateEnum.TRIGGERED, false),
    /**
     *
     */
    NEWBIE_ONRAMPING_Q(PlayersForTest.NEWBIE.getPlayerID(), ObjectivesForTest.ONRAMPING_PRESS_Q.getQuestID(), ObjectivesForTest.ONRAMPING_PRESS_Q.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false),
    NEWBIE_ONRAMPING_I(PlayersForTest.NEWBIE.getPlayerID(),
            ObjectivesForTest.ONRAMPING_OPEN_CLOSET.getQuestID(),
            ObjectivesForTest.ONRAMPING_OPEN_CLOSET.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false),
    NEWBIE_ONRAMPING_U(PlayersForTest.NEWBIE.getPlayerID(),
            ObjectivesForTest.ONRAMPING_PRESS_U.getQuestID(),
            ObjectivesForTest.ONRAMPING_PRESS_U.getObjectiveID(),
            ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    NEWBIE_ONRAMPING_MOVE_UP(PlayersForTest.NEWBIE.getPlayerID(), ObjectivesForTest.ONRAMPING_MOVE_FORWARD.getQuestID(), ObjectivesForTest.ONRAMPING_MOVE_FORWARD.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    NEWBIE_ONRAMPING_MOVE_DOWN(PlayersForTest.NEWBIE.getPlayerID(), ObjectivesForTest.ONRAMPING_MOVE_BACKWARD.getQuestID(), ObjectivesForTest.ONRAMPING_MOVE_BACKWARD.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    NEWBIE_ONRAMPING_MOVE_RIGHT(PlayersForTest.NEWBIE.getPlayerID(), ObjectivesForTest.ONRAMPING_MOVE_RIGHT.getQuestID(), ObjectivesForTest.ONRAMPING_MOVE_RIGHT.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    NEWBIE_ONRAMPING_MOVE_LEFT(PlayersForTest.NEWBIE.getPlayerID(), ObjectivesForTest.ONRAMPING_MOVE_LEFT.getQuestID(), ObjectivesForTest.ONRAMPING_MOVE_LEFT.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    NEWBIE_ONRAMPING_TERMINAL_MAN(PlayersForTest.NEWBIE.getPlayerID(), ObjectivesForTest.ONRAMPING_TERMINAL_MAN.getQuestID(), ObjectivesForTest.ONRAMPING_TERMINAL_MAN.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    NEWBIE_ONRAMPING_INTERACTABLE(PlayersForTest.NEWBIE.getPlayerID(), ObjectivesForTest.ONRAMPING_INTERACTABLE.getQuestID(), ObjectivesForTest.ONRAMPING_INTERACTABLE.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false),

    /**
     *
     */
    EASTER_EGG_OBJ_STATE(PlayersForTest.EASTEREGG_PLAYER.getPlayerID(), ObjectivesForTest.EASTER_EGG_STEP_1.getQuestID(), ObjectivesForTest.EASTER_EGG_STEP_1.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false),
    NEWBIE_ONRAMPING_XP_BUFF(PlayersForTest.NEWBIE.getPlayerID(),
            ObjectivesForTest.ONRAMPING_XP_BUFF.getQuestID(), ObjectivesForTest.ONRAMPING_XP_BUFF.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false),
    MERLIN_FIND_THE_LIBRARY(PlayersForTest.MERLIN.getPlayerID(),
            ObjectivesForTest.EXPLORING_FIND_LIBRARY.getQuestID(),
            ObjectivesForTest.EXPLORING_FIND_LIBRARY.getObjectiveID(), ObjectiveStateEnum.TRIGGERED, false);


    private final int objectiveID;
    private final int questID;
    private final int playerID;
    private final ObjectiveStateEnum state;
    private final boolean needsNotification;

    /**
     * Constructor for Objectives Enum
     *
     * @param objectiveID this objective's unique ID
     * @param questID     the ID of the quest that contains this objective
     */
    ObjectiveStatesForTest(int playerID, int questID, int objectiveID, ObjectiveStateEnum state,
                           boolean needsNotification)
    {
        this.objectiveID = objectiveID;
        this.questID = questID;
        this.playerID = playerID;
        this.state = state;
        this.needsNotification = needsNotification;
    }

    /**
     * @return true if the state should be shared with the player
     */
    public boolean isNeedingNotification()
    {
        return needsNotification;
    }

    /**
     * @return the objectiveID
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }

    /**
     * @return the player's ID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the current state of the objective for that player
     */
    public ObjectiveStateEnum getState()
    {
        return state;
    }

    /**
     * @return the questID
     */
    public int getQuestID()
    {
        return questID;
    }

}
