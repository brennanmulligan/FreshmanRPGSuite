package edu.ship.engr.shipsim.datatypes;

import java.util.Random;

/**
 * The list of crews. Each player must belong to one crew
 *
 * @author Merlin
 */
public enum Crew
{
    /**
     *
     */
    OFF_BY_ONE("Off By One", "off_by_one"),

    /**
     *
     */
    FORTY_PERCENT("Forty Percent", "null_pointer_exception"),

    /**
     *
     */
    OUT_OF_BOUNDS("Out Of Bounds", "out_of_bounds"),
    NPCS("NPCs", "out_of_bounds");

    private final String crewName;
    private final String appearanceType;

    Crew(String crewName, String appearanceType)
    {
        this.crewName = crewName;
        this.appearanceType = appearanceType;
    }

    /**
     * @return a unique ID for this crew
     */
    public int getID()
    {
        return ordinal();
    }

    /**
     * @param id the crewID we are interested in
     * @return the crew with the given ID
     */
    public static Crew getCrewForID(int id)
    {
        return Crew.values()[id];
    }

    /**
     * @return the user friendly name for this crew
     */
    public String getCrewName()
    {
        return crewName;
    }

    /**
     * @return the appearance type for this crew
     */
    public String getCrewAppearanceType()
    {
        return appearanceType;
    }

    /**
     * @return a random element in this enum
     */
    public static Crew getRandomCrew()
    {
        return values()[new Random().nextInt(values().length)];
    }
}
