package edu.ship.engr.shipsim.datatypes;

public enum RoamingNPCPathsForTest
{
    PATH1("30,89 31,89 32,89 33,89 34,89 35,89 34,89 33,89 32,89 31,89", 33);

    private String path;
    private int npcID;

    private RoamingNPCPathsForTest(String path, int npcID)
    {
        this.path = path;
        this.npcID = npcID;
    }

    public String getPath()
    {
        return this.path;
    }

    public int getNpcID()
    {
        return this.npcID;
    }
}
