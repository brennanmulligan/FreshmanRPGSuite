package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePlayerInformation
{
    private final String playerName;
    private final String password;

    public String getPlayerName()
    {
        return playerName;
    }

    public String getPassword()
    {
        return password;
    }

    @JsonCreator
    public ChangePlayerInformation(@JsonProperty("playerName") String playerName, @JsonProperty("password") String password)
    {
        this.playerName = playerName;
        this.password = password;
    }
}
