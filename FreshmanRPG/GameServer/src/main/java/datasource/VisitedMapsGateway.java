package datasource;

import java.util.ArrayList;

import dataDTO.PlayerMapLocationDTO;

/**
 * A row data gateway interface for the maps a 
 * player has visited
 *
 */
public interface VisitedMapsGateway
{
	/**
	 * Get the list of maps visited
	 * @return maps visited
	 */
	public ArrayList<String> getMaps();

	/**
	 * get the map name
	 * @return mapName the map's name
	 */
	public String getMapName();
}
