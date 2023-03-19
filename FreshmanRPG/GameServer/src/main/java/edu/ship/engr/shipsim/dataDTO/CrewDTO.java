package edu.ship.engr.shipsim.dataDTO;

import java.util.Objects;

/**
 * Contains the information of a player.
 */
public class CrewDTO
{
    private int crewID;
    private String name;

    /**
     * Default constructor.
     */
    public CrewDTO()
    {

    }

    /**
     * Testing constructor for game manager
     *
     * @param crewID         the crew's unique ID
     * @param name            the crew's name
     */
    public CrewDTO(int crewID, String name)
    {
        this.crewID = crewID;
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        CrewDTO crewDTO = (CrewDTO) o;
        return crewID == crewDTO.crewID &&
                Objects.equals(name, crewDTO.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(crewID, name);
    }

    public int getCrewID()
    {
        return crewID;
    }

    public void setCrewID(int crewID)
    {
        this.crewID = crewID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
