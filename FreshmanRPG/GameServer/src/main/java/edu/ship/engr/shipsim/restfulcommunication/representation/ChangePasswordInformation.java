package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordInformation
{
    private final String username;
    private final String newPassword;

    public String getUsername()
    {
        return username;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    @JsonCreator
    public ChangePasswordInformation(@JsonProperty("name") String username, @JsonProperty("password") String newPassword)
    {
        this.username = username;
        this.newPassword = newPassword;
    }
}
