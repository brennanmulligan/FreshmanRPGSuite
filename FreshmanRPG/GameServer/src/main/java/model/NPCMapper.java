package model;

import java.util.ArrayList;

import datasource.DatabaseException;
import datasource.NPCRowDataGateway;
import datasource.NPCRowDataGatewayMock;
import datasource.NPCRowDataGatewayRDS;

/**
 * Maps all information about an NPC into the datasource. Inherits functionality
 * related to fields NPC inherits from Player and adds functionality for fields
 * that are unique to NPC
 *
 * @author Merlin
 *
 */
public class NPCMapper extends PlayerMapper
{

	/**
	 * Finds all of the NPCs on a given map and creates mappers for them all
	 *
	 * @param mapName the name of the map
	 * @return mappers for all of the MPCs on the given map
	 * @throws DatabaseException if the data source layer cannot answer the
	 *             request
	 */
	protected static ArrayList<NPCMapper> findNPCsOnMap(String mapName) throws DatabaseException
	{
		ArrayList<NPCRowDataGateway> gateways;
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			gateways = NPCRowDataGatewayMock.getNPCsForMap(mapName);
		}
		else
		{
			gateways = NPCRowDataGatewayRDS.getNPCsForMap(mapName);
		}
		ArrayList<NPCMapper> mappers = new ArrayList<>();
		for (NPCRowDataGateway gateway : gateways)
		{
			mappers.add(new NPCMapper(gateway.getPlayerID()));
		}
		return mappers;
	}

	private NPCRowDataGateway NPCGateway;

	/**
	 * Find constructor
	 *
	 * @param playerID the unique ID of the NPC we are interested in
	 * @throws DatabaseException if the data source can't find a player with the
	 *             given ID
	 */
	protected NPCMapper(int playerID) throws DatabaseException
	{
		super(playerID);
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			this.NPCGateway = new NPCRowDataGatewayMock(playerID);
		}
		else
		{
			this.NPCGateway = new NPCRowDataGatewayRDS(playerID);
		}
		NPC thisPlayer = (NPC) player;
		thisPlayer.setBehaviorClass(NPCGateway.getBehaviorClass());
	}

	/**
	 * @see model.PlayerMapper#createPlayerObject(int)
	 */
	@Override
	protected Player createPlayerObject(int playerID)
	{
		return new NPC(playerID);
	}

}
