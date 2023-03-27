package edu.ship.engr.shipsim.datatypes;

/**
 * The players that are in the test database
 *
 * @author Merlin
 */
public enum NPCsForTest
{

    /**
     *
     */
    NPC1(PlayersForTest.MOCK_NPC.getPlayerID(), "edu.ship.engr.shipsim.model.NPCMockBehavior"),
    /**
     *
     */
    NPC2(PlayersForTest.QUIZBOT.getPlayerID(), "edu.ship.engr.shipsim.model.QuizBotBehavior"),
    /**
     *
     */
    NPC3(PlayersForTest.MOCK_NPC3.getPlayerID(), "edu.ship.engr.shipsim.model.NPCMockBehavior"),

    /**
     *
     */
    REDHAT(PlayersForTest.RED_HAT.getPlayerID(), "edu.ship.engr.shipsim.model.RedHatBehavior"),
    /**
     *
     */
    TUTOR(PlayersForTest.TUTOR.getPlayerID(), "edu.ship.engr.shipsim.model.TutorBehavior"),
    /**
     *
     */
    BIG_RED(PlayersForTest.BIG_RED.getPlayerID(), "edu.ship.engr.shipsim.model.RandomFactsNPCBehavior"),

    /**
     *
     */
    RANDOM_FACTS_NPC_2(PlayersForTest.RANDOM_FACTS_NPC_2.getPlayerID(), "edu.ship.engr.shipsim.model.RandomFactsNPCBehavior"),

    /**
     *
     */
    PROFESSOR_H(PlayersForTest.PROFESSOR_H.getPlayerID(), "edu.ship.engr.shipsim.model.RandomFactsNPCBehavior"),

    /**
     *
     */
    QUAD_GUY(PlayersForTest.QUAD_GUY.getPlayerID(), "edu.ship.engr.shipsim.model.RandomFactsNPCBehavior"),

    /**
     *
     */
    IT_GUY_NPC(PlayersForTest.IT_GUY_NPC.getPlayerID(), "edu.ship.engr.shipsim.model.RandomFactsNPCBehavior"),

    /**
     *
     */
    TEACHER_NPC(PlayersForTest.TEACHER_NPC.getPlayerID(), "edu.ship.engr.shipsim.model.RandomFactsNPCBehavior"),

    //make change in the behavior class to reflect the new behavior that will be added
    ROCK_PAPER_SCISSORS_NPC(PlayersForTest.ROCK_PAPER_SCISSORS_NPC.getPlayerID(), "edu.ship.engr.shipsim.model.NPCBehaviorRPS"),

    /**
     *
     */
    MOWREY_FRONTDESK_NPC(PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID(), "edu.ship.engr.shipsim.model.RoamingInfoNPCBehavior", "Assets/MowreyInfoXML.xml"),

    PRESIDENT_NPC(PlayersForTest.PRESIDENT_NPC.getPlayerID(), "edu.ship.engr.shipsim.model.RoamingInfoNPCBehavior", "Assets/Npc_info.xml"),

    HISTORY_NPC(PlayersForTest.HISTORY_NPC.getPlayerID(), "edu.ship.engr.shipsim.model.RandomFactsNPCBehavior"),
    HISTORY_NPC_2(PlayersForTest.HISTORY_NPC_2.getPlayerID(), "edu.ship.engr.shipsim.model.RandomFactsNPCBehavior"),
    HISTORY_NPC_3(PlayersForTest.HISTORY_NPC_3.getPlayerID(), "edu.ship.engr.shipsim.model.RandomFactsNPCBehavior");

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
