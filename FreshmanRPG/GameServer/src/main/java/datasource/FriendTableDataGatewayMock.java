package datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataDTO.FriendDTO;
import datatypes.FriendStatusEnum;

/**
 *
 * @author Christian C, Andrew M
 * description mock gateway for the friend command
 *
 */
public class FriendTableDataGatewayMock implements FriendTableDataGateway
{

	private static FriendTableDataGatewayMock singleton = null;
	FriendDBMock friend = FriendDBMock.getSingleton();

	/**
	 * constructor for the gateway
	 */
	private FriendTableDataGatewayMock()
	{
		resetData();
	}

	/**
	 * getter for the singleton
	 * @return singleton
	 */
	public static synchronized FriendTableDataGatewayMock getSingleton()
	{
		if (singleton == null)
		{
			singleton = new FriendTableDataGatewayMock();
		}
		return singleton;
	}


	/**
	 * 1 | playerID | friendID | Status
	 * 2 | playerID | friendID | Status
	 * 3 | playerID | friendID | Status
	 *
	 */
	@Override
	public void resetData()
	{
		friend.resetData();
	}

	/**
	 * add method to update hashmap when a player adds a new friend
	 * @param playerID The player's ID
	 * @param friendName The friend's name
	 * @param status The status of this relationship
	 */
	@Override
	public void add(int playerID, String friendName, FriendStatusEnum status)
	{
		//update singleton for the hashmaps
		friend.insert(playerID, friendName, status);
	}

	@Override
	public ArrayList<FriendDTO> getAllFriends(int id) throws DatabaseException
	{

		return friend.get(id);
	}

	@Override
	public int accept(int playerID, String friendName)
	{
		return friend.update(playerID, friendName);
	}

	@Override
	public int getFriendCounter(int id)
	{

		return friend.getFriendCount(id);
	}

	/**
	 * For updating the friends tab
	 */
	@Override
	public String getSpecificNameFromId(int playerId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Updating the friends tab
	 */
	@Override
	public int getSpecificIDFromName(String playerName)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
