package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Derek
 */
public final class LogoutInformation
{
    private final String authKey;

    @JsonCreator
    public LogoutInformation(@JsonProperty("authKey") String authKey)
    {
        this.authKey = authKey;
    }

    public String getAuthKey()
    {
        return authKey;
    }
}
