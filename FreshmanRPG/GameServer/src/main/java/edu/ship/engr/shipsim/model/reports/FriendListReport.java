package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.model.Report;

import java.util.ArrayList;

public class FriendListReport implements Report
{

    private int playerId;
    private ArrayList<FriendDTO> friendList;

    /**
     * Friend report constructor
     *
     * @param playerId
     * @param friendList
     */
    public FriendListReport(int playerId, ArrayList<FriendDTO> friendList)
    {
        this.playerId = playerId;
        this.friendList = friendList;
    }

    /**
     * get player id
     *
     * @return
     */
    public int getPlayerId()
    {
        return playerId;
    }

    /**
     * get friend list
     *
     * @return
     */
    public ArrayList<FriendDTO> getFriendList()
    {
        return friendList;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((friendList == null) ? 0 : friendList.hashCode());
        result = prime * result + playerId;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        FriendListReport other = (FriendListReport) obj;
        if (friendList == null)
        {
            if (other.friendList != null)
            {
                return false;
            }
        }
        else if (!friendList.equals(other.friendList))
        {
            return false;
        }
        if (playerId != other.playerId)
        {
            return false;
        }
        return true;
    }


}
