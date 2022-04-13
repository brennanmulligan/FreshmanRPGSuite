package datatypes;

/**
 * The players that are in the test database
 *
 * @author Merlin
 *
 */
public enum NPCsForTest
{

	/**
	 *
	 */
	NPC1(PlayersForTest.MOCK_NPC.getPlayerID(), "model.NPCMockBehavior"),
	/**
	 *
	 */
	NPC2(PlayersForTest.QUIZBOT.getPlayerID(), "model.QuizBotBehavior"),
	/**
	 *
	 */
	NPC3(PlayersForTest.MOCK_NPC3.getPlayerID(), "model.NPCMockBehavior"),

	/**
	 *
	 */
	REDHAT(PlayersForTest.RED_HAT.getPlayerID(), "model.RedHatBehavior"),
	/**
	 *
	 */
	TUTOR(PlayersForTest.TUTOR.getPlayerID(), "model.TutorBehavior"),
	/**
	 *
	 */
	RANDOM_FACTS_NPC_1(PlayersForTest.RANDOM_FACTS_NPC_1.getPlayerID(), "model.RandomFactsNPCBehavior"),

	/**
	 *
	 */
	RANDOM_FACTS_NPC_2(PlayersForTest.RANDOM_FACTS_NPC_2.getPlayerID(), "model.RandomFactsNPCBehavior"),

	/**
	 *
	 */
	HUO(PlayersForTest.HUO.getPlayerID(), "model.RandomFactsNPCBehavior"),

	/**
	 *
	 */
	QUAD_GUY(PlayersForTest.QUAD_GUY.getPlayerID(), "model.RandomFactsNPCBehavior"),

	/**
	 *
	 */
	TIM_NPC(PlayersForTest.TIM_NPC.getPlayerID(), "model.RandomFactsNPCBehavior"),

	/**
	 *
	 */
	MOONEY_NPC(PlayersForTest.MOONEY_NPC.getPlayerID(), "model.RandomFactsNPCBehavior"),

	//make change in the behavior class to reflect the new behavior that will be added
	RockPaperScissors_NPC(PlayersForTest.RockPaperScissors_NPC.getPlayerID(), "model.NPCBehaviorRPS");

	private String behaviorClass;
	private int playerID;

	NPCsForTest(int playerID, String behaviorClass)
	{
		this.playerID = playerID;
		this.behaviorClass = behaviorClass;
	}

	/**
	 * @return the map name the pin for the current connection is good for
	 */
	public String getBehaviorClass()
	{
		return behaviorClass;
	}

	/**
	 * @return this NPCs playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

}