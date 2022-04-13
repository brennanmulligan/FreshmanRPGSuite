package model;

import java.util.ArrayList;

import datasource.MapTableDataGatewayRDS;

/**
 *
 * @author abraham
 *
 */
public class GameManagerMapManager
{
	private static GameManagerMapManager instance;


	/**
	 * Get the singleton instance
	 * @return the singleton instance
	 */
	public static GameManagerMapManager getInstance()
	{
		if (instance == null)
		{
			instance = new GameManagerMapManager();
		}

		return instance;
	}

	/**
	 * @return All the maps
	 */
	public ArrayList<String> getMaps()
	{
		return MapTableDataGatewayRDS.getInstance().getMapNames();
	}

}
