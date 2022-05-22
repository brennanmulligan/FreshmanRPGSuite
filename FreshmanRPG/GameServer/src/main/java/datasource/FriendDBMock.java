package datasource;

import dataDTO.FriendDTO;
import datatypes.FriendEnum;
import datatypes.FriendStatusEnum;
import datatypes.PlayersForTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class FriendDBMock
{

    private static FriendDBMock singleton = null;
    FriendEnum friends;
    private HashMap<Integer, ArrayList<FriendDTO>> friendMap = new HashMap<>();

    /**
     * friend mapper constructor
     */
    public FriendDBMock()
    {
        resetData();
    }

    /**
     * get instance
     *
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
     * gets all of the friends for a specific player
     *
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
                        FriendDTO flipped = new FriendDTO(friendTemp.getFriendID(),
                                friendTemp.getPlayerID(), friendTemp.getStatus(),
                                friendTemp.getFriendName(), friendTemp.getPlayerName());
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
            System.out.println(
                    "Player name: " + print.getPlayerName() + "  Friend name: " +
                            print.getFriendName() + " Status" + print.getStatus());
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
     *
     */
    public int getFriendCount(int id)
    {
        int friendCounter = 0;
        ArrayList<FriendDTO> friends = get(id);

        //if the person has no friends or isnt in the DB in this case so null return 0
        if (friends == null)
        {
            return friendCounter;
        }

        for (FriendDTO fd : friends)
        {
            if ((fd.getPlayerID() == id) &&
                    (fd.getStatus().equals(FriendStatusEnum.ACCEPTED)))
            {
                friendCounter++;
            }
            if ((fd.getFriendID() == id) &&
                    (fd.getStatus().equals(FriendStatusEnum.ACCEPTED)))
            {
                friendCounter++;
            }
        }
        return friendCounter;

    }

    /**
     * getter for the friends
     *
     */
    public HashMap<Integer,ArrayList<FriendDTO>> getFriendMapper()
    {
        return friendMap;
    }

    /**
     * Insert new friend into the Mock database
     *
     */
    public void insert(int playerID, String friendName, FriendStatusEnum status)
    {
        ArrayList<FriendDTO> friends = friendMap.get(playerID);
        if (friends == null)
        {
            friends = new ArrayList<>();
            //friendMap.put(playerID, null);
        }
        friends.add(new FriendDTO(playerID,
                PlayersForTest.grabPlayerName(friendName).getPlayerID(), status,
                PlayersForTest.grabPlayerId(playerID).getPlayerName(), friendName));
        friendMap.put(playerID, friends);
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
            FriendDTO added = new FriendDTO(friends.getId(), friends.getFriendID(),
                    friends.getStatus(),
                    PlayersForTest.grabPlayerId(friends.getId()).getPlayerName(),
                    PlayersForTest.grabPlayerId(friends.getFriendID()).getPlayerName());
            tempFriends.add(added);
        }

        for (int i = 0; i < tempFriends.size(); i++)
        {
            int id = tempFriends.get(i).getPlayerID();
            for (FriendDTO tempFriend : tempFriends)
            {
                if (tempFriend.getPlayerID() == id)
                {
                    tempFriends2.add(tempFriend);
                }
            }

            friendMap.put(id, tempFriends2);
            tempFriends2 = new ArrayList<>();
        }
    }

    /**
     * Updating the player data to accept the friend request
     *
     * @param playerID   - friend that is accepting
     * @param friendName - friend that sent request
     */
    public int update(int playerID, String friendName)
    {
        int friendIDint = PlayersForTest.grabPlayerName(friendName).getPlayerID();
        accept(playerID, friendIDint);
        accept(friendIDint, playerID);
        return friendIDint;
    }

    private void accept(int playerID, int friendIDint)
    {
        ArrayList<FriendDTO> friends = friendMap.get(friendIDint);
        if (friends != null)
        {
            for (FriendDTO fd : friends)
            {
                if (fd.getFriendID() == playerID)
                {
                    fd.setStatus(FriendStatusEnum.ACCEPTED); //updating john
                    break;
                }
            }
        }
    }
}