package edu.ship.engr.shipsim.datatypes;

/**
 * A list of the states an objective can be in
 *
 * @author Merlin
 */
public enum ObjectiveStateEnum
{
    /**
     * This objective isn't yet available to the players. When this objective's
     * quest has yet to be triggered.
     */
    HIDDEN(""),
    /**
     * Objective is ready to be completed.
     */
    TRIGGERED("has triggered"),

    /**
     * Player has been notified, nothing left to do.
     */
    COMPLETED("is completed"),

    /**
     * Objective has expired because quest is expired
     */
    EXPIRED("is expired"),

    /**
     * Timer for the objective has expired
     */
    LATE("timer expired");

    private String description;

    /**
     * @return the English description of this objective state
     */
    public String getDescription()
    {
        return description;
    }

    ObjectiveStateEnum(String description)
    {
        this.description = description;
    }

    /**
     * @return the unique id of the enum
     */
    public int getID()
    {
        return this.ordinal();
    }

}
