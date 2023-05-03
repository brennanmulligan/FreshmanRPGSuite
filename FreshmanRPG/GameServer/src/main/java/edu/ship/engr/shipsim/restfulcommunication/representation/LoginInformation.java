package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Derek
 */
public final class LoginInformation
{
    private final String playerName;
    private final String password;

    @JsonCreator
    public LoginInformation(@JsonProperty("playerName") String playerName, @JsonProperty("password") String password)
    {
        this.playerName = playerName;
        this.password = password;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public String getPassword()
    {
        return password;
    }
}
