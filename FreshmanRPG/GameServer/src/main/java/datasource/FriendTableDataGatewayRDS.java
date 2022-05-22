package datasource;

import java.sql.*;
import java.util.ArrayList;

import dataDTO.FriendDTO;
import dataDTO.PlayerDTO;
import datatypes.FriendStatusEnum;

public class FriendTableDataGatewayRDS implements FriendTableDataGateway
{

	static ArrayList<PlayerDTO> allPlayer;
	private Connection connect;
	private String playerName, friendName;

	static TableDataGateway getGateway() throws DatabaseException
	{
		return new FriendTableDataGatewayRDS();
	}

	private FriendTableDataGatewayRDS() throws DatabaseException
	{
		PlayerTableDataGateway playerGateway =
				(PlayerTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway("Player");
		allPlayer = playerGateway.retrieveAllPlayers();

	}
	@Override
	public void resetTableGateway()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList<FriendDTO> getAllFriends(int id) throws DatabaseException
	{
		this.connect = DatabaseManager.getSingleton().getConnection();
		int playerId, friendId;
		FriendStatusEnum status;
		ArrayList<FriendDTO> results;

		try
		{
			PreparedStatement stmt =  connect.prepareStatement("SELECT F.playerID as PLAYERID, F.FRIENDID as FRIENDID, F.status as status"
					+ " FROM  Friends as F, Players as P WHERE F.playerID = P.playerID AND P.playerID = ?;");
			stmt.setInt(1, id);

			ResultSet result = stmt.executeQuery();

			results = new ArrayList<>();
			while (result.next())
			{
				playerId = result.getInt("PLAYERID");
				friendId = result.getInt("FRIENDID");
				if (result.getString("status").equals("ACCEPTED"))
				{
					status = FriendStatusEnum.ACCEPTED;
				}
				else
				{
					status = FriendStatusEnum.PENDING;
				}
//				for(int i = 0; i < allPlayer.size(); i++)
//				{
//					if(allPlayer.get(i).getPlayerID() == playerId)
//					{
//						playerName = allPlayer.get(i).getPlayerName();
//					}
//					else if(allPlayer.get(i).getPlayerID() == friendId)
//					{
//						friendName = allPlayer.get(i).getPlayerName();
//					}
//				}
				getNameFromId(playerId, friendId);

				FriendDTO friend = new FriendDTO(playerId, friendId, status, playerName, friendName);
				results.add(friend);
			}

			//Get the records where the id passed in is the friend
			stmt =  connect.prepareStatement("SELECT F.playerID as PLAYERID, F.FRIENDID as FRIENDID, F.status as status"
					+ " FROM  Friends as F, Players as P WHERE F.playerID = P.playerID AND F.FRIENDID = ?;");
			stmt.setInt(1, id);

			result = stmt.executeQuery();

			while (result.next())
			{
				playerId = result.getInt("FRIENDID");
				friendId = result.getInt("PLAYERID");
				if (result.getString("status").equals("PENDING"))
				{
					status = FriendStatusEnum.REQUESTED;
				}
				else
				{
					status = FriendStatusEnum.ACCEPTED;
				}
				getNameFromId(playerId, friendId);
				FriendDTO friend = new FriendDTO(playerId, friendId, status, playerName, friendName);
				results.add(friend);
			}

			return results;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new DatabaseException("Unable to find player");
		}
	}

	/**
	 * create a new row in the table to add the friend
	 * @throws DatabaseException if the attempt to add gets an unexpected error
	 */
	@Override
	public void add(int playerID, String friendName, FriendStatusEnum status) throws DatabaseException
	{

		int friendID = -1;
		for(PlayerDTO playerDTO:allPlayer)
		{
			if (playerDTO.getPlayerName().equals(friendName))
			{
				friendID = playerDTO.getPlayerID();
				break;
			}
		}
		if (friendID == -1)
		{
			throw new DatabaseException("Player not found or updated");
		}
		else
		{
			createRow(playerID, friendID, status);
		}
	}

	/**
	 * update the row of the player who sent the request to accepted status
	 * Than call the add to add the player who accepted
	 * @throws DatabaseException if the attempt to update gets an unexpected error
	 */
	@Override
	public int accept(int playerID, String friendName) throws DatabaseException
	{

		Connection connect = DatabaseManager.getSingleton().getConnection();
		int friendId = -1;
		try
		{

			for (PlayerDTO playerDTO:allPlayer)
			{
				//System.out.println(allPlayer.get(i).getPlayerName() + " : " + friendName);
				if (playerDTO.getPlayerName().equals(friendName))
				{
					friendId = playerDTO.getPlayerId();
					break;
				}
			}

			if (friendId == -1)
			{
				throw new DatabaseException("Player not found or updated");
			}
			else
			{
				PreparedStatement stmt =  connect.prepareStatement("UPDATE Friends SET status = 'ACCEPTED' WHERE playerID = ? AND FRIENDID = ?;");
				stmt.setInt(1, friendId);
				stmt.setInt(2, playerID);
				stmt.executeUpdate();

				return friendId;
			}
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Player not found or updated", e);
		}
	}

	/**
	 * create the friend table
	 * @throws DatabaseException if the attempt to create the table gets an unexpected error
	 */
	public static void createTable() throws DatabaseException
	{
		Connection connect = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connect.prepareStatement("DROP TABLE IF EXISTS Friends;");
			stmt.executeUpdate();
			stmt.close();
			stmt =  connect.prepareStatement("CREATE TABLE Friends(playerID int NOT NULL,"
					+ "FRIENDID int NOT NULL, status varchar(10) NOT NULL);");
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create table");
		}
	}

	/**
	 * Used to create the row in the table
	 * @param id the player's id
	 * @param friendID the friends player id
	 * @param status the status of their relationship
	 * @throws DatabaseException if the attempt to add the relationship gets an unexpected error
	 */
	public void createRow(int id, int friendID, FriendStatusEnum status) throws DatabaseException
	{

		String query = "INSERT INTO Friends " + "SET playerID = ?, FRIENDID = ?, STATUS = ?";
		this.connect = DatabaseManager.getSingleton().getConnection();

		PreparedStatement stmt;
		try
		{
			stmt =  connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.setInt(2, friendID);
			stmt.setString(3, status.toString());

			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Row creation of friends was not successful", e);
		}
	}

	@Override
	public int getFriendCounter(int id) throws DatabaseException
	{
		this.connect = DatabaseManager.getSingleton().getConnection();
		int friendCount = 0;
		try
		{
			PreparedStatement stmt =  connect.prepareStatement("SELECT COUNT(*) AS total FROM Friends WHERE status = 'ACCEPTED' AND (friendId = ? OR playerId = ?);");
			stmt.setInt(1, id);
			stmt.setInt(2, id);

			ResultSet result = stmt.executeQuery();
			if (result.next())
			{
				friendCount = result.getInt("total");
			}
			return friendCount;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new DatabaseException("Unable to find player");
		}
	}

	private void getNameFromId(int playerId, int friendId)
	{
		for (PlayerDTO playerDTO: allPlayer)
		{
			if (playerDTO.getPlayerID() == playerId)
			{
				playerName = playerDTO.getPlayerName();
			}
			else if (playerDTO.getPlayerID() == friendId)
			{
				friendName = playerDTO.getPlayerName();
			}
		}
	}

	/**
	 * this is for finding the player for updating friends tab list
	 * @param playerId the id of the player we are interested in
	 * @return the name of the given player
	 */
	public String getSpecificNameFromId(int playerId)
	{
		String name = "";
		for (PlayerDTO playerDTO:allPlayer)
		{
			if (playerDTO.getPlayerID() == playerId)
			{
				name = playerDTO.getPlayerName();
			}
		}
		return name;
	}

	/**
	 * this is for finding the player for updating friends tab list
	 * @param playerName the name of a player
	 * @return the player's ID
	 */
	public int getSpecificIDFromName(String playerName)
	{
		int playerID = 0;
		for (PlayerDTO playerDTO:allPlayer)
		{
			if (playerDTO.getPlayerName().equals(playerName))
			{
				playerID = playerDTO.getPlayerID();
			}
		}
		return playerID;
	}
}