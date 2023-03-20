package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePlayerInformation
{
    private final String username;
    private final String password;

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    @JsonCreator
    public ChangePlayerInformation(@JsonProperty("username") String username, @JsonProperty("password") String password)
    {
        this.username = username;
        this.password = password;
    }
}