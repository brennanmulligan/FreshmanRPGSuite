package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePlayerInformation
{
    private final String playerName;
    private final String password;
    private final int crew;
    private final int major;

    public int getCrew()
    {
        return crew;
    }

    public int getMajor()
    {
        return major;
    }

    public int getSection()
    {
        return section;
    }

    private final int section;

    public String getPlayerName()
    {
        return playerName;
    }

    public String getPassword()
    {
        return password;
    }

    @JsonCreator
    public CreatePlayerInformation(@JsonProperty("playerName") String playerName, @JsonProperty("password") String password,
                                   @JsonProperty("crew") int crew, @JsonProperty("major") int major,
                                   @JsonProperty("section") int section)
    {
        this.playerName = playerName;
        this.password = password;
        this.crew = crew;
        this.major = major;
        this.section = section;
    }
}
