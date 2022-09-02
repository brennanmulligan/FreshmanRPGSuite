package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datasource.LevelTableDataGateway;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Merlin
 */
public class LevelManagerDTO
{

    private static LevelManagerDTO singleton;
    private static ArrayList<LevelRecord> allLevels = new ArrayList<>();

    private LevelManagerDTO()
    {
        LevelTableDataGateway gateway =
                LevelTableDataGateway.getSingleton();
        try
        {
            allLevels = gateway.getAllLevels();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        Collections.sort(allLevels);
    }

    /**
     * @return the only LevelManager in the system
     */
    public static synchronized LevelManagerDTO getSingleton()
    {
        if (singleton == null)
        {
            singleton = new LevelManagerDTO();
        }
        return singleton;
    }

    /**
     * Return the level record based on the amount of points sent in
     *
     * @param levelUpPoints the experience points being sent in
     * @return level record based on the level up points
     */
    public LevelRecord getLevelForPoints(int levelUpPoints)
    {
        int i = 0;
        while (levelUpPoints >= allLevels.get(i).getLevelUpPoints())
        {
            i++;
        }
        return allLevels.get(i);
    }

    /**
     * @param experiencePoints how many points the player has
     * @return the next level the player can achieve
     */
    public LevelRecord getNextLevelForPoints(int experiencePoints)
    {
        int i = 0;
        while (experiencePoints >= allLevels.get(i).getLevelUpPoints())
        {
            i++;
        }
        return allLevels.get(Math.min(i, allLevels.size() - 1));
    }

}
