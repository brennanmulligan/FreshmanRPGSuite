package edu.ship.engr.shipsim.datatypes;

import java.util.Calendar;

/**
 * @author Merlin
 */
public enum LevelsForTest
{

    /**
     *
     */
    ONE("Serf", 45, Calendar.SEPTEMBER, 30),
    /**
     *
     */
    TWO("Freemerchant", 85, Calendar.OCTOBER, 31),
    /**
     *
     */
    THREE("Lord", 125, Calendar.NOVEMBER, 30),
    /**
     *
     */
    FOUR("King", Integer.MAX_VALUE, 0, 0);

    private String description;
    private int levelUpPoints;
    private int levelUpMonth;
    private int levelUpDate;

    LevelsForTest(String description, int levelUpPoints, int levelUpMonth, int levelUpDate)
    {
        this.description = description;
        this.levelUpPoints = levelUpPoints;
        this.levelUpMonth = levelUpMonth;
        this.levelUpDate = levelUpDate;
    }

    /**
     * @return the description of the level
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return the day of the month by which they are required to move passed
     * this level
     */
    public int getLevelUpDayOfMonth()
    {
        return levelUpDate;
    }

    /**
     * @return the month by which they are required to move passed this level
     */
    public int getLevelUpMonth()
    {
        return levelUpMonth;
    }

    /**
     * @return the number of points to level up out of this level
     */
    public int getLevelUpPoints()
    {
        return levelUpPoints;
    }
}
