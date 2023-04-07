package edu.ship.engr.shipsim.model.reports;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Ryan
 */
@EqualsAndHashCode(callSuper = true)
public final class ObjectiveNotificationCompleteReport extends SendMessageReport
{
    @Getter private final int playerID;
    @Getter private final int questID;
    @Getter private final int objectiveID;

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
}
