package edu.ship.engr.shipsim.datasource;

/**
 * Creates objectives for the DB
 *
 * @author merlin (copied to client and edited by : cody, scott)
 */
public enum ObjectivesForTest
{
    /**
     *
     */
    ONE(1, "Objective Description 1", 1, 1),
    /**
     *
     */
    TWO(2, "Objective Description 2", 1, 2),
    /**
     *
     */
    THREE(1, "Another Objective Description 1", 2, 3),
    /**
     *
     */
    FOUR(2, "Another Objective Description 2", 2, 4),
    /**
     *
     */
    FIVE(1, "Objective for Quest 3", 3, 5),
    /**
     *
     */
    ANOTHER_FOR_QUEST_1(3, "One more Objective", 1, 3),
    /**
     *
     */
    QUEST_4_OBJECTIVE_1(1, "Quest 4 Objective", 4, 5),
    /**
     *
     */
    QUEST_4_OBJECTIVE_2(2, "Quest 4 Objective 2", 4, 5);


    private int objectiveID;
    private String objectiveDescription;
    private int questID;
    private int experiencePointsGained;

    /**
     * Constructor for Objectives Enum
     *
     * @param objectiveID            this objective's unique ID
     * @param objectiveDescription   what the player has to do
     * @param questID                the ID of the quest that contains this objective
     * @param experiencePointsGained the number of experience points you gain for completing this objective
     */
    ObjectivesForTest(int objectiveID, String objectiveDescription, int questID, int experiencePointsGained)
    {
        this.objectiveID = objectiveID;
        this.objectiveDescription = objectiveDescription;
        this.questID = questID;
        this.experiencePointsGained = experiencePointsGained;
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

}