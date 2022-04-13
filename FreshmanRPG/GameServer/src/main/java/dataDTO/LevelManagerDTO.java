package dataDTO;

import java.util.ArrayList;
import java.util.Collections;

import datasource.DatabaseException;
import datasource.LevelRecord;
import datasource.LevelTableDataGateway;
import datasource.LevelTableDataGatewayMock;
import datasource.LevelTableDataGatewayRDS;
import model.OptionsManager;

/**
 *
 * @author Merlin
 *
 */
public class LevelManagerDTO
{

	private static LevelManagerDTO singleton;
	private static ArrayList<LevelRecord> allLevels = new ArrayList<>();

	/**
	 * @return the only LevelManager in the system
	 * @throws DatabaseException shouln't
	 */
	public static synchronized LevelManagerDTO getSingleton() throws DatabaseException
	{
		if (singleton == null)
		{
			singleton = new LevelManagerDTO();
		}
		return singleton;
	}

	private LevelTableDataGateway gateway;

	private LevelManagerDTO() throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			gateway = LevelTableDataGatewayMock.getSingleton();
		}
		else
		{
			gateway = LevelTableDataGatewayRDS.getSingleton();
		}
		allLevels = gateway.getAllLevels();
		Collections.sort(allLevels);
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
