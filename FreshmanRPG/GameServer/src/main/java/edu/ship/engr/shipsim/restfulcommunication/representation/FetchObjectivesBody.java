package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Derek
 */
@Data
public final class FetchObjectivesBody
{
    private final int playerID;

    @JsonCreator
    public FetchObjectivesBody(@JsonProperty("playerID") int playerID)
    {
        this.playerID = playerID;
    }
}
