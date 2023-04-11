package edu.ship.engr.shipsim.datatypes;

import edu.ship.engr.shipsim.dataDTO.QuestStateRecordDTO;

import java.util.ArrayList;

/**
 * Creates objectives for the DB
 *
 * @author merlin
 */
public enum QuestStatesForTest
{
    /**
     *
     */
    PLAYER1_QUEST1(1, 1, QuestStateEnum.AVAILABLE, true, false),
    /**
     *
     */
    PLAYER1_QUEST2(1, 2, QuestStateEnum.FULFILLED, true, false),
    /**
     *
     */
    PLAYER1_QUEST3(1, 3, QuestStateEnum.AVAILABLE, true, false),
    PLAYER1_QUEST102(1, 102, QuestStateEnum.TRIGGERED, false, false),
    /**
     *
     */
    PLAYER1_QUEST4(1, 105, QuestStateEnum.AVAILABLE, true, false),
    /**
     *
     */
    PLAYER1_QUEST5(1, 106, QuestStateEnum.AVAILABLE, true, false),
    /**
     *
     */
    PLAYER1_QUEST6(1, 107, QuestStateEnum.TRIGGERED, true, false),

    PLAYER1_QUEST100(1, 100, QuestStateEnum.TRIGGERED, false, false),

    /**
     *
     */
    PLAYER5_QUEST4(5, 4, QuestStateEnum.AVAILABLE, true, false),

    /**
     *
     */
    PLAYER2_QUEST1(2, 1, QuestStateEnum.TRIGGERED, true, false),
    /**
     *
     */
    PLAYER2_QUEST2(2, 2, QuestStateEnum.AVAILABLE, true, false),
    /**
     *
     */
    PLAYER2_QUEST4(2, 4, QuestStateEnum.FULFILLED, true, false),
    /**
     *
     */
    PLAYER4_QUEST3(4, 3, QuestStateEnum.TRIGGERED, true, false),
    /**
     *
     */
    PLAYER4_QUEST4(4, 4, QuestStateEnum.AVAILABLE, true, false),
    PLAYER1_QUEST17(1, 17, QuestStateEnum.TRIGGERED, false, false),
    /**
     *
     */
    PLAYER7_QUEST1(7, 1, QuestStateEnum.TRIGGERED, false, false),
    /**
     * A quest that is ready to be triggered (with no states for the included
     * objectives)
     */
    PLAYER7_QUEST2(7, 2, QuestStateEnum.AVAILABLE, false, false),
    /**
     *
     */
    MARTY_QUEST5(PlayersForTest.MARTY.getPlayerID(), 5, QuestStateEnum.TRIGGERED, false, false),
    /**
     *
     */
    MARTY_QUEST11(PlayersForTest.MARTY.getPlayerID(), 11, QuestStateEnum.EXPIRED, false, false),
    /**
     *
     */
    PLAYER8_QUEST2(8, 2, QuestStateEnum.TRIGGERED, false, false),
    /**
     *
     */
    PLAYER8_QUEST10(8, 10, QuestStateEnum.TRIGGERED, true, false),
    /**
     *
     */
    PLAYER19_QUEST6(19, 6, QuestStateEnum.TRIGGERED, false, false),
    /**
     *
     */
    PLAYER19_QUEST7(19, 7, QuestStateEnum.AVAILABLE, false, false),
    /**
     *
     */
    PLAYER19_QUEST8(19, 8, QuestStateEnum.EXPIRED, false, false),
    /**
     *
     */
    PLAYER19_QUEST9(19, 9, QuestStateEnum.COMPLETED, false, false),
    /**
     * Newbie should have the first quest already triggered
     */
    NEWBIE_ONRAMPING(PlayersForTest.NEWBIE.getPlayerID(), QuestsForTest.ONRAMPING_QUEST.getQuestID(),
            QuestStateEnum.TRIGGERED, true, false),
    /**
     *
     */
    TEST_EASTEREGG(PlayersForTest.EASTEREGG_PLAYER.getPlayerID(), QuestsForTest.EASTER_EGG_QUEST.getQuestID(),
            QuestStateEnum.TRIGGERED, true, true),
    /**
     *
     */
    TEST_HISTORYQUEST(PlayersForTest.BIKE_PLAYER.getPlayerID(), QuestsForTest.TEST_HISTORY_RESPONSE_QUEST.getQuestID(),
            QuestStateEnum.TRIGGERED, false, false),
    /**
     *
     */
    TEST_TIMED_OBJECTIVE_QUEST(PlayersForTest.JEFF.getPlayerID(), QuestsForTest.TIMED_OBJECTIVE_QUEST.getQuestID(),
            QuestStateEnum.AVAILABLE, false, false),
    /**
     *
     */
    MERLIN_EXPLORING(PlayersForTest.MERLIN.getPlayerID(),
            QuestsForTest.EXPLORATION_QUEST.getQuestID(), QuestStateEnum.TRIGGERED, false, false),
    /**
     *
     */
    PLAYER29_QUEST1(29, 1, QuestStateEnum.TRIGGERED, false, false),
    /**
     *
     */
    PLAYER29_QUEST2(29, 2, QuestStateEnum.AVAILABLE, false, false),
    /**
     *
     */
    PLAYER29_QUEST4(29, 4, QuestStateEnum.FULFILLED, false, false);




//	TEST_TRIGGER_QUEST(1, 106, QuestStateEnum.AVAILABLE, true);

    private final int playerID;
    private final int questID;
    private final QuestStateEnum questState;
    private final boolean needingNotification;
    private final boolean easterEgg;

    /**
     * Constructor for Quest State Enum
     *
     * @param playerID            a player's unique ID
     * @param questID             a quest's unique ID
     * @param questState          the state of the quest for the specified player
     * @param needingNotification true if this player should be notified about the
     *                            state of this quest
     */
    QuestStatesForTest(int playerID, int questID, QuestStateEnum questState, boolean needingNotification, boolean easterEgg)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.questState = questState;
        this.needingNotification = needingNotification;
        this.easterEgg = easterEgg;
    }

    /**
     * @return the player's unique ID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the state of the quest for the given player
     */
    public QuestStateEnum getState()
    {
        return questState;
    }

    /**
     * @return the questID
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return true if the player should be notified of this state
     */
    public boolean isNeedingNotification()
    {
        return needingNotification;
    }

    public boolean isEasterEgg()
    {
        return easterEgg;
    }

    /**
     * For testing purposes
     *
     * @return the queststateRecord DTO with the data for this enum item
     */
    public QuestStateRecordDTO getQuestStateRecordDTO()
    {
        return new QuestStateRecordDTO(this.playerID, this.questID, this.questState, this.needingNotification);
    }

    /**
     * for testing purposes
     *
     * @return arraylist of all queststaterecordDTOs for testing.
     */
    public static ArrayList<QuestStateRecordDTO> getAllQuestStateRecordDTOs()
    {
        ArrayList<QuestStateRecordDTO> queststates = new ArrayList<>();
        for (QuestStatesForTest qsft : QuestStatesForTest.values())
        {
            queststates.add(qsft.getQuestStateRecordDTO());
        }
        return queststates;
    }
}
