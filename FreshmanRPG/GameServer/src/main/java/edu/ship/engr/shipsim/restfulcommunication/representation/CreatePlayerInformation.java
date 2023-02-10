package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePlayerInformation
{
    private final String username;
    private final String password;
    private final int crewNum;
    private final int majorNum;

    public int getCrewNum()
    {
        return crewNum;
    }

    public int getMajorNum()
    {
        return majorNum;
    }

    public int getSection()
    {
        return section;
    }

    private final int section;

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    @JsonCreator
    public CreatePlayerInformation(@JsonProperty("name") String username, @JsonProperty("password") String password,
                                   @JsonProperty("crew") int crewNum, @JsonProperty("major") int majorNum,
                                   @JsonProperty("section") int section)
    {
        this.username = username;
        this.password = password;
        this.crewNum = crewNum;
        this.majorNum = majorNum;
        this.section = section;
    }
}
