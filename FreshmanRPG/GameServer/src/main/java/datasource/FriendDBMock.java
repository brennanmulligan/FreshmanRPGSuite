package datasource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import dataDTO.FriendDTO;
import datatypes.FriendEnum;
import datatypes.FriendStatusEnum;
import datatypes.PlayersForTest;

public class FriendDBMock
{

	private static FriendDBMock singleton = null;
	private HashMap<Integer, ArrayList<FriendDTO>> friendMap = new HashMap<>();
	FriendEnum friends;

	/**
	 * friend mapper constructor
	 */
	public FriendDBMock()
	{
		resetData();
	}

	/**
	 * get instance
	 * @return
	 */
	public static synchronized FriendDBMock getSingleton()
	{
		if (singleton == null)
		{
			singleton = new FriendDBMock();
		}
		return singleton;
	}

	/**
	 * resets the hashmap
	 */
	public void resetData()
	{

		ArrayList<FriendDTO> tempFriends = new ArrayList<>();
		ArrayList<FriendDTO> tempFriends2 = new ArrayList<>();
		friendMap = new HashMap<>();
		//creates DTOs from the enum
		for (FriendEnum friends : FriendEnum.values())
		{
			//System.out.println("Test: " +PlayersForTest.grabPlayerId(friends.getId()).getPlayerName());
			FriendDTO added = new FriendDTO(friends.getId(), friends.getFriendID(), friends.getStatus(),
					PlayersForTest.grabPlayerId(friends.getId()).getPlayerName(), PlayersForTest.grabPlayerId(friends.getFriendID()).getPlayerName());
			tempFriends.add(added);
		}

		for (int i = 0; i < tempFriends.size(); i++)
		{
			int id = tempFriends.get(i).getPlayerID();
			for (int j = 0; j < tempFriends.size(); j++)
			{
				if (tempFriends.get(j).getPlayerID() == id)
				{
					tempFriends2.add(tempFriends.get(j));
				}
			}

			friendMap.put(id, tempFriends2);
			tempFriends2 = new ArrayList<>();
		}
	}

	/**
	 * getter for the friends
	 * @return
	 */
	public HashMap getFriendMapper()
	{
		return friendMap;
	}

	/**
	 * Insert new friend into the Mock database
	 * @param playerID
	 * @param friendName
	 * @param status
	 */
	public void insert(int playerID, String friendName, FriendStatusEnum status)
	{
		ArrayList<FriendDTO> friends = friendMap.get(playerID);
		if (friends == null)
		{
			friends = new ArrayList<>();
			//friendMap.put(playerID, null);
		}
		friends.add(new FriendDTO(playerID, PlayersForTest.grabPlayerName(friendName).getPlayerID(), status,
				PlayersForTest.grabPlayerId(playerID).getPlayerName(), friendName));
		friendMap.put(playerID, friends);
	}

	/**
	 * Updating the player data to accept the friend request
	 * @param playerID - friend that is accepting
	 * @param friendID - friend that sent request
	 */
	public int update(int playerID, String friendName)
	{
		int friendIDint = PlayersForTest.grabPlayerName(friendName).getPlayerID();
		ArrayList<FriendDTO> friends = friendMap.get(friendIDint);
		for (int i = 0; i < friends.size(); i++)
		{
			FriendDTO fd = friends.get(i);
			if (fd.getFriendID() == playerID)
			{
				fd.setStatus(FriendStatusEnum.ACCEPTED); //updating john
				break;
			}
		}
		return friendIDint;
	}

	/**
	 * gets all of the friends for a specific player
	 * @param id
	 * @return
	 */
	public ArrayList<FriendDTO> get(int id)
	{
		//	ArrayList<FriendDTO> friends = (ArrayList<FriendDTO>) friendMap.get(id);
		ArrayList<FriendDTO> friends = friendMap.get(id);
		if (friends == null)
		{
			friends = new ArrayList<>();
		}

		ArrayList<FriendDTO> friends2 = new ArrayList<>();
		for (Entry<Integer, ArrayList<FriendDTO>> entry : friendMap.entrySet())
		{
			if (entry.getKey() != id)
			{
				ArrayList<FriendDTO> temp = entry.getValue();
				for (FriendDTO friendTemp : temp)
				{
					if (friendTemp.getFriendID() == id)
					{
						FriendDTO flipped = new FriendDTO(friendTemp.getFriendID(), friendTemp.getPlayerID(), friendTemp.getStatus(), friendTemp.getFriendName(), friendTemp.getPlayerName());
						if (flipped.getStatus() == FriendStatusEnum.PENDING)
						{
							flipped.setStatus(FriendStatusEnum.REQUESTED);
						}
						friends2.add(flipped);
					}
				}
			}
		}

		if (!friends2.isEmpty())
		{
			friends.addAll(friends2);
		}
		System.out.println("Printing friends after additions");
		System.out.println("Size of list " + friends.size());
		for (FriendDTO print : friends)
		{
			System.out.println("Player name: " + print.getPlayerName() + "  Friend name: " + print.getFriendName() + " Status" + print.getStatus());
		}

		//removing duplicate
		for (int i = 0; i < friends.size(); i++)
		{
			for (int j = i + 1; j < friends.size(); j++)
			{
				if (friends.get(i).getFriendID() == friends.get(j).getFriendID())
				{
					friends.remove(j);
					//break;
				}
			}
		}
		return friends;
	}

	/**
	 * get the number of friends someone has
	 * @param id
	 * @return
	 */
	public int getFriendCount(int id)
	{
		int friendCounter = 0;
		ArrayList<FriendDTO> friends = friendMap.get(id);

		//if the person has no friends or isnt in the DB in this case so null return 0
		if (friends == null)
		{
			return friendCounter;
		}

		for (int i = 0; i < friends.size(); i++)
		{
			FriendDTO fd = friends.get(i);
			if ((fd.getPlayerID() == id) && (fd.getStatus().equals(FriendStatusEnum.ACCEPTED)))
			{
				friendCounter++;
			}
			if ((fd.getFriendID() == id) && (fd.getStatus().equals(FriendStatusEnum.ACCEPTED)))
			{
				friendCounter++;
			}
		}
		return friendCounter;
	}
}