package datasource;

import java.util.ArrayList;

import dataDTO.PlayerMapLocationDTO;
import datatypes.PlayersForTest;

/**
 * Row data gateway for the maps that a player has visited. 
 *
 */
public class VisitedMapsGatewayMock implements VisitedMapsGateway
{
	private ArrayList<String> mapsVisited;
	private PlayerMapLocationDTO position;
	private String mapName;

	/**
	 * finder constructor to find the maps a player has visited. 
	 * @param playerID the player's ID
	 */
	public VisitedMapsGatewayMock(int playerID)
	{
		for (PlayersForTest p : PlayersForTest.values())
		{
			if (p.getPlayerID() == playerID)
			{
				mapsVisited = p.getMapsVisited();
			}
		}
	}

	/**
	 * adder constructor to add a map to a player
	 * @param playerID the player's ID
	 * @param mapTitle the map the player has just visited
	 */
	public VisitedMapsGatewayMock(int playerID, String mapTitle)
	{
		for (PlayersForTest p : PlayersForTest.values())
		{
			if (p.getPlayerID() == playerID)
			{
				p.addMapVisited(mapTitle);
				mapsVisited = p.getMapsVisited();
			}
		}
	}

	/**
	 * Get the map
	 * @param mapTitle the maps name
	 */
	public VisitedMapsGatewayMock(String mapTitle)
	{
		this.mapName = mapTitle + ".tmx";
		position = new PlayerMapLocationDTO(1, 1);
	}

	/**
	 * get the maps visited
	 * @return mapsVisited the list of maps a player has visited. 
	 */
	@Override
	public ArrayList<String> getMaps()
	{
		return mapsVisited;
	}

	/**
	 * get the maps name
	 * @return mapName the mapName...
	 */
	@Override
	public String getMapName()
	{
		return mapName;
	}
}
