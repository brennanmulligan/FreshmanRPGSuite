package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public class DeleteObjectiveBody
{
    private final int objectiveID;
    private final int questID;

    @JsonCreator
    public DeleteObjectiveBody(@JsonProperty("objectiveID") int objectiveID, @JsonProperty("questID") int questID)
    {
        this.objectiveID = objectiveID;
        this.questID = questID;
    }

    public int getObjectiveID()
    {
        return objectiveID;
    }

    public int getQuestID()
    {
        return questID;
    }
}
