package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * @author Ryan
 */
public final class ObjectiveNotificationCompleteReport implements Report
{

    private final int playerID;
    private final int questID;
    private final int objectiveID;

    /**
     * Constructor
     *
     * @param playerID    id of the player
     * @param questID     id of the quest
     * @param objectiveID id of the objective
     */
    public ObjectiveNotificationCompleteReport(int playerID, int questID, int objectiveID)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.objectiveID = objectiveID;
    }

    /**
     * @return player id
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return quest id
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return objective id
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }

    /**
     * )
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + objectiveID;
        result = prime * result + playerID;
        result = prime * result + questID;
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        ObjectiveNotificationCompleteReport other = (ObjectiveNotificationCompleteReport) obj;
        if (objectiveID != other.objectiveID)
        {
            return false;
        }
        if (playerID != other.playerID)
        {
            return false;
        }
        if (questID != other.questID)
        {
            return false;
        }
        return true;
    }
}
