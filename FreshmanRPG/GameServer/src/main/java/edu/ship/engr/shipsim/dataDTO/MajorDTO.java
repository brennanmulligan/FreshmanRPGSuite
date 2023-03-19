package edu.ship.engr.shipsim.dataDTO;

import java.util.Objects;

/**
 * Contains the information of a player.
 */
public class MajorDTO
{
    private int majorID;
    private String name;

    /**
     * Default constructor.
     */
    public MajorDTO()
    {

    }

    /**
     * Testing constructor for game manager
     *
     * @param majorID         the major's unique ID
     * @param name            the major's name
     */
    public MajorDTO(int majorID, String name)
    {
        this.majorID = majorID;
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
        MajorDTO majorDTO = (MajorDTO) o;
        return majorID == majorDTO.majorID &&
                Objects.equals(name, majorDTO.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(majorID, name);
    }

    public int getMajorID()
    {
        return majorID;
    }

    public void setMajorID(int majorID)
    {
        this.majorID = majorID;
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
