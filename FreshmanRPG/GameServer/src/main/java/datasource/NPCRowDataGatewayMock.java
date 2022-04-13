package datasource;

import datatypes.NPCsForTest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A mock implementation for PlayerRowDataGateway
 *
 * @author Merlin
 *
 */
public class NPCRowDataGatewayMock implements NPCRowDataGateway
{

	private class NPCInfo
	{
		private String behaviorClass;

		public NPCInfo(String behaviorClass)
		{
			this.behaviorClass = behaviorClass;
		}

		public String getBehaviorClass()
		{
			return behaviorClass;
		}

	}

	/**
	 * Map player ID to player information
	 */
	private static HashMap<Integer, NPCInfo> npcInfo;
	private int playerID;
	private NPCInfo info;

	/**
	 * Finder constructor - will initialize itself from the stored information
	 *
	 * @param playerID the ID of the player we are looking for
	 * @throws DatabaseException if the playerID isn't in the data source
	 */
	public NPCRowDataGatewayMock(int playerID) throws DatabaseException
	{
		if (npcInfo == null)
		{
			resetData();
		}

		if (npcInfo.containsKey(playerID))
		{
			info = npcInfo.get(playerID);
			this.playerID = playerID;
		}
		else
		{
			throw new DatabaseException("Couldn't find NPC with ID " + playerID);
		}
	}

	/**
	 * Just used by tests to reset static information
	 */
	public NPCRowDataGatewayMock()
	{
	}

	/**
	 * @see datasource.PlayerRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		npcInfo = new HashMap<>();
		for (NPCsForTest p : NPCsForTest.values())
		{
			npcInfo.put(p.getPlayerID(), new NPCInfo(p.getBehaviorClass()));
		}
	}

	/**
	 * @see datasource.NPCRowDataGateway#getBehaviorClass()
	 */
	@Override
	public String getBehaviorClass()
	{
		return info.getBehaviorClass();
	}

	/**
	 * @see datasource.NPCRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Get all of the NPCS in the mock data for a given map name
	 *
	 * @param mapName the name of the map
	 * @return the NPCs that are managed by the server managing the given map
	 * @throws DatabaseException shouldn't
	 */
	public static ArrayList<NPCRowDataGateway> getNPCsForMap(String mapName) throws DatabaseException
	{
		if (npcInfo == null)
		{
			new NPCRowDataGatewayMock(NPCsForTest.NPC1.getPlayerID()).resetData();
		}
		ArrayList<NPCRowDataGateway> result = new ArrayList<>();
		for (Integer npcID : npcInfo.keySet())
		{
			PlayerConnectionRowDataGatewayMock playerGateway = new PlayerConnectionRowDataGatewayMock(npcID);
			if (playerGateway.getMapName().equals(mapName))
			{
				result.add(new NPCRowDataGatewayMock(npcID));
			}
		}
		return result;
	}

}
