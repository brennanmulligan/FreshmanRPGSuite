package edu.ship.engr.shipsim.dataDTO;

import java.util.Objects;

/**
 * Contains the information of a player.
 */
public class CrewDTO
{
    private int id;
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
     * @param id         the crew's unique ID
     * @param name            the crew's name
     */
    public CrewDTO(int id, String name)
    {
        this.id = id;
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
        return id == crewDTO.id &&
                Objects.equals(name, crewDTO.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name);
    }

    public int getID()
    {
        return id;
    }

    public void setID(int id)
    {
        this.id = id;
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
