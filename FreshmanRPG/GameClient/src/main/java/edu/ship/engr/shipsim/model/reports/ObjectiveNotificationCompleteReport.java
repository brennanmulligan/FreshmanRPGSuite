package edu.ship.engr.shipsim.model.reports;

import java.util.Objects;

/**
 * @author Ryan
 */
public final class ObjectiveNotificationCompleteReport extends SendMessageReport
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
        // Happens on client, thus it will always be loud
        super(0, false);
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ObjectiveNotificationCompleteReport that))
        {
            return false;
        }
        return getPlayerID() == that.getPlayerID() &&
                getQuestID() == that.getQuestID() &&
                getObjectiveID() == that.getObjectiveID()&&
                this.getRelevantPlayerID() == that.getRelevantPlayerID() &&
                this.isQuiet() == that.isQuiet();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getPlayerID(), getQuestID(), getObjectiveID(), getRelevantPlayerID(), isQuiet());
    }
}
