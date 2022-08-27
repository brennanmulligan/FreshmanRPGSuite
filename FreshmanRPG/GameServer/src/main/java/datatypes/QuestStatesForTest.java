package datatypes;

import java.util.ArrayList;

import dataDTO.QuestStateRecordDTO;

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
	PLAYER1_QUEST1(1, 1, QuestStateEnum.AVAILABLE, true),
	/**
	 *
	 */
	PLAYER1_QUEST2(1, 2, QuestStateEnum.FULFILLED, true),
	/**
	 *
	 */
	PLAYER1_QUEST3(1, 3, QuestStateEnum.AVAILABLE, true),
	PLAYER1_QUEST102(1, 102, QuestStateEnum.TRIGGERED, false),
	/**
	 *
	 */
	PLAYER1_QUEST4(1, 105, QuestStateEnum.AVAILABLE, true),
	/**
	 *
	 */
	PLAYER1_QUEST5(1, 106, QuestStateEnum.AVAILABLE, true),
	/**
	 *
	 */
	PLAYER1_QUEST6(1, 107, QuestStateEnum.TRIGGERED, true),

	PLAYER1_QUEST100(1,100, QuestStateEnum.TRIGGERED,false),

	/**
	 *
	 */
	PLAYER5_QUEST4(5, 4, QuestStateEnum.AVAILABLE, true),

	/**
	 *
	 */
	PLAYER2_QUEST1(2, 1, QuestStateEnum.TRIGGERED, true),
	/**
	 *
	 */
	PLAYER2_QUEST2(2, 2, QuestStateEnum.AVAILABLE, true),
	/**
	 *
	 */
	PLAYER2_QUEST4(2, 4, QuestStateEnum.FULFILLED, true),
	/**
	 *
	 */
	PLAYER4_QUEST3(4, 3, QuestStateEnum.TRIGGERED, true),
	/**
	 *
	 */
	PLAYER4_QUEST4(4, 4, QuestStateEnum.AVAILABLE, true),
	PLAYER1_QUEST17(1, 17, QuestStateEnum.TRIGGERED, false),
	/**
	 *
	 */
	PLAYER7_QUEST1(7, 1, QuestStateEnum.TRIGGERED, false),
	/**
	 * A quest that is ready to be triggered (with no states for the included
	 * objectives)
	 */
	PLAYER7_QUEST2(7, 2, QuestStateEnum.AVAILABLE, false),
	/**
	 *
	 */
	MARTY_QUEST5(PlayersForTest.MARTY.getPlayerID(), 5, QuestStateEnum.TRIGGERED, false),
	/**
	 *
	 */
	MARTY_QUEST11(PlayersForTest.MARTY.getPlayerID(), 11, QuestStateEnum.EXPIRED, false),
	/**
	 *
	 */
	PLAYER8_QUEST2(8, 2, QuestStateEnum.TRIGGERED, false),
	/**
	 *
	 */
	PLAYER8_QUEST10(8, 10, QuestStateEnum.TRIGGERED, true),
	/**
	 *
	 */
	PLAYER19_QUEST6(19, 6, QuestStateEnum.TRIGGERED, false),
	/**
	 *
	 */
	PLAYER19_QUEST7(19, 7, QuestStateEnum.AVAILABLE, false),
	/**
	 *
	 */
	PLAYER19_QUEST8(19, 8, QuestStateEnum.EXPIRED, false),
	/**
	 *
	 */
	PLAYER19_QUEST9(19, 9, QuestStateEnum.COMPLETED, false),
	/**
	 * Newbie should have the first quest already triggered
	 */

	NEWBIE_ONRAMPING(PlayersForTest.NEWBIE.getPlayerID(), QuestsForTest.ONRAMPING_QUEST.getQuestID(),
			QuestStateEnum.TRIGGERED, true),

	MERLIN_EXPLORING(PlayersForTest.MERLIN.getPlayerID(),
			QuestsForTest.EXPLORATION_QUEST.getQuestID(),QuestStateEnum.TRIGGERED, false),
	/**
	 *
	 */
	PLAYER29_QUEST1(29, 1, QuestStateEnum.TRIGGERED, false),
	/**
	 *
	 */
	PLAYER29_QUEST2(29, 2, QuestStateEnum.AVAILABLE, false),
	/**
	 *
	 */
	PLAYER29_QUEST4(29, 4, QuestStateEnum.FULFILLED, false);


//	TEST_TRIGGER_QUEST(1, 106, QuestStateEnum.AVAILABLE, true);

	private final int playerID;
	private final int questID;
	private final QuestStateEnum questState;
	private final boolean needingNotification;

	/**
	 * Constructor for Quest State Enum
	 *
	 * @param playerID a player's unique ID
	 * @param questID a quest's unique ID
	 * @param questState the state of the quest for the specified player
	 * @param needingNotification true if this player should be notified about the
	 *        state of this quest
	 */
	QuestStatesForTest(int playerID, int questID, QuestStateEnum questState, boolean needingNotification)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.questState = questState;
		this.needingNotification = needingNotification;
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
