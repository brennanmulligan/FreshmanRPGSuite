package datatypes;

import java.util.ArrayList;
import java.util.Arrays;

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
	BIG_RED(PlayersForTest.BIG_RED.getPlayerID(), "model.RandomFactsNPCBehavior"),

	/**
	 *
	 */
	RANDOM_FACTS_NPC_2(PlayersForTest.RANDOM_FACTS_NPC_2.getPlayerID(), "model.RandomFactsNPCBehavior"),

	/**
	 *
	 */
	PROFESSOR_H(PlayersForTest.PROFESSOR_H.getPlayerID(), "model.RandomFactsNPCBehavior"),

	/**
	 *
	 */
	QUAD_GUY(PlayersForTest.QUAD_GUY.getPlayerID(), "model.RandomFactsNPCBehavior"),

	/**
	 *
	 */
	IT_GUY_NPC(PlayersForTest.IT_GUY_NPC.getPlayerID(), "model.RandomFactsNPCBehavior"),

	/**
	 *
	 */
	TEACHER_NPC(PlayersForTest.TEACHER_NPC.getPlayerID(), "model.RandomFactsNPCBehavior"),

	//make change in the behavior class to reflect the new behavior that will be added
	RockPaperScissors_NPC(PlayersForTest.RockPaperScissors_NPC.getPlayerID(), "model.NPCBehaviorRPS"),

	/**
	 *
	 */
	MOWREY_FRONTDESK_NPC(PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID(), "model.RoamingInfoNPCBehavior", "Assets/MowreyInfoXML.xml"),

	PRESIDENT_NPC(PlayersForTest.PRESIDENT_NPC.getPlayerID(), "model.RoamingInfoNPCBehavior", "Assets/Npc_info.xml");

	private String behaviorClass;
	private int playerID;
	private String filePath;

	NPCsForTest(int playerID, String behaviorClass, String filePath)
	{
		this.playerID = playerID;
		this.behaviorClass = behaviorClass;
		this.filePath = filePath;
	}
	NPCsForTest(int playerID, String behaviorClass)
	{
		this.playerID = playerID;
		this.behaviorClass = behaviorClass;
		this.filePath = "";
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

	public String getFilePath() 
	{
		return filePath;
	}

}
