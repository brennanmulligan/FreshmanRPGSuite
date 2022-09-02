package edu.ship.engr.shipsim.datatypes;

/**
 * @author Merlin
 */
public enum MapAreasForTest
{
    /**
     *
     */
    ONE_MAP_AREA("areaname", 2);

    private String areaName;
    private int questID;

    MapAreasForTest(String areaName, int questID)
    {
        this.areaName = areaName;
        this.questID = questID;
    }

    /**
     * @return the name of the map area
     */
    public String getAreaName()
    {
        return areaName;
    }

    /**
     * @return the ID of the quest associated with the map area
     */
    public int getQuestID()
    {
        return questID;
    }

}
