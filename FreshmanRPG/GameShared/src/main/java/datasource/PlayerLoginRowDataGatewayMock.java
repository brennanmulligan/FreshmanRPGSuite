package datasource;

import java.util.HashMap;

import datatypes.PlayersForTest;

/**
 * This is a mock data gateway that returns data without looking at the
 * database. Its initial data comes from PlayersForTest
 *
 * @see PlayersForTest
 * @author Merlin
 *
 */
public class PlayerLoginRowDataGatewayMock extends PlayerLoginRowDataGateway
{

	private class PlayerInfo
	{
		private int playerID;
		private String playerName;
		private String password;

		public PlayerInfo(int playerID, String playerName, String password)
		{
			this.playerName = playerName;
			this.password = password;
			this.playerID = playerID;
		}

		public String getPassword()
		{
			return password;
		}

		public String getPlayerName()
		{
			return playerName;
		}

		public int getPlayerID()
		{
			return this.playerID;
		}

	}

	/**
	 * Map player ID to player information
	 */
	private static HashMap<Integer, PlayerInfo> playerLogins;
	private static int nextKey = 1;
	private int playerID;
	private PlayerInfo playerInfo;

	/**
	 * Finder Constructor: will initialize itself from the data in the data
	 * source
	 *
	 * @param playerName the name of the player
	 * @throws DatabaseException if we can't find that player
	 */
	public PlayerLoginRowDataGatewayMock(String playerName) throws DatabaseException
	{
		if (playerLogins == null)
		{
			resetData();
		}
		boolean found = false;
		for (Integer key : playerLogins.keySet())
		{
			PlayerInfo info = playerLogins.get(key);
			if (info.getPlayerName().equalsIgnoreCase(playerName))
			{
				playerID = key;
				playerInfo = info;
				found = true;
			}
		}
		if (!found)
		{
			throw new DatabaseException("Couldn't find player named " + playerName);
		}
	}

	/**
	 * Create constructor: will add the data to the data source
	 * @param playerID - The id of the player
	 * @param playerName - The name of the player
	 * @param password - The player's password
	 * @throws DatabaseException duplicate player name
	 */
	public PlayerLoginRowDataGatewayMock(int playerID, String playerName, String password) throws DatabaseException
	{
		this.playerID = playerID;
		if (playerLogins == null)
		{
			resetData();
		}
		for (PlayerInfo info : playerLogins.values())
		{
			if (info.getPlayerName().equals(playerName) && info.getPlayerID() == playerID)
			{
				throw new DatabaseException("Tried to create duplicate user " + playerName);
			}
		}

		playerLogins.put(playerID, new PlayerInfo(playerID, playerName, password));
	}

	/**
	 * Finder constructor
	 *
	 * @param playerID the player's unique ID
	 * @throws DatabaseException if we can't find the player
	 */
	public PlayerLoginRowDataGatewayMock(int playerID) throws DatabaseException
	{
		if (playerLogins == null)
		{
			resetData();
		}
		if (playerLogins.containsKey(playerID))
		{
			this.playerID = playerID;
			this.playerInfo = playerLogins.get(playerID);
		}
		else
		{
			throw new DatabaseException("Couldn't find player with ID " + playerID);
		}
	}

	/**
	 * Default constructor.
	 */
	public PlayerLoginRowDataGatewayMock()
	{
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#getPassword()
	 */
	@Override
	public String getPassword()
	{
		return playerInfo.getPassword();
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#getPlayerName()
	 */
	@Override
	public String getPlayerName()
	{
		return playerInfo.getPlayerName();
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		playerLogins.put(playerID, playerInfo);
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		playerLogins = new HashMap<>();
		nextKey = 1;
		for (PlayersForTest p : PlayersForTest.values())
		{
			playerLogins.put(nextKey, new PlayerInfo(p.getPlayerID(), p.getPlayerName(), p.getPlayerPassword()));
			nextKey++;
		}
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password)
	{
		this.playerInfo.password = password;
	}

	/**
	 * deletes a row specified by playerID
	 */
	@Override
	public void deleteRow()
	{
		playerLogins.remove(this.playerID);
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#setName(java.lang.String)
	 */
	@Override
	public void setName(String playerName)
	{
		this.playerInfo.playerName = playerName;
	}

	/**
	 * @return the salt
	 */
	@Override
	public byte[] getSalt()
	{
		return null;
	}

	/**
	 * Set the salt
	 */
	@Override
	public void setSalt(byte[] salt)
	{
	}

	/**
	 * @param password The password to test
	 * @return True if the password is correct
	 */
	@Override
	public boolean checkPassword(String candidate_password)
	{
		return candidate_password.equals(this.playerInfo.password);
	}

}
