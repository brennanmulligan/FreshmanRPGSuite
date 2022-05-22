package datasource;

import java.util.ArrayList;

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

	FriendDBMock friend = FriendDBMock.getSingleton();

	/**
	 * constructor for the gateway
	 */
	private FriendTableDataGatewayMock()
	{
		resetTableGateway();
	}

	static TableDataGateway getGateway()
	{
		return new FriendTableDataGatewayMock();
	}

	@Override
	public int accept(int playerID, String friendName)
	{
		return friend.update(playerID, friendName);
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
	public ArrayList<FriendDTO> getAllFriends(int id)
	{

		return friend.get(id);
	}

	@Override
	public int getFriendCounter(int id)
	{

		return friend.getFriendCount(id);
	}

	/**
	 * Updating the friends tab
	 */
	@Override
	public int getSpecificIDFromName(String playerName)
	{
		return 0;
	}

	/**
	 * For updating the friends tab
	 */
	@Override
	public String getSpecificNameFromId(int playerId)
	{
		return null;
	}

	/**
	 * 1 | playerID | friendID | Status
	 * 2 | playerID | friendID | Status
	 * 3 | playerID | friendID | Status
	 *
	 */
	@Override
	public void resetTableGateway()
	{
		friend.resetData();
	}

}
