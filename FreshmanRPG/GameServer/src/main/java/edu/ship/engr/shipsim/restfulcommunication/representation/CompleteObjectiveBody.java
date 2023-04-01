package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public final class CompleteObjectiveBody
{
    private final int playerID;
    private final int questID;
    private final int objectiveID;

    public CompleteObjectiveBody(
            @JsonProperty("playerID") int playerID,
            @JsonProperty("questID") int questID,
            @JsonProperty("objectiveID") int objectiveID)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.objectiveID = objectiveID;
    }
}
