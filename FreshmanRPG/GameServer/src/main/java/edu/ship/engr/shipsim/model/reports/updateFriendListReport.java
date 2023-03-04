package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;

/*
 * @Author Christian Crouthamel, Andrew McCoy
 */
public class updateFriendListReport extends SendMessageReport
{

    FriendDTO friend;

    /*
     * constructor for the repeort that hold player ID and friend name
     */
    public updateFriendListReport(FriendDTO friend)
    {
        super(friend.getPlayerID(), true);
        this.friend = friend;
    }

    /**
     * get player id
     *
     * @return The ID of the player making the request
     */
    public int getPlayerID()
    {
        return friend.getPlayerID();
    }

    /**
     * getter for friend name
     *
     * @return The names of the friends of the requesting player
     */
    public String getFriends()
    {
        return friend.getFriendName();
    }

    /*
     * getter for status
     */
    public FriendStatusEnum getStatus()
    {
        return friend.getStatus();
    }

    /*
     * getter for friend ID
     */
    public int getFriendID()
    {
        return friend.getFriendID();
    }

    /**
     * return the friend DTO
     *
     * @return The details of one friend
     */
    public FriendDTO getFriendDTO()
    {
        return friend;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((friend == null) ? 0 : friend.hashCode());
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
        updateFriendListReport other = (updateFriendListReport) obj;
        if (friend == null)
        {
            if (other.friend != null)
            {
                return false;
            }
        }
        else if (!friend.equals(other.friend))
        {
            return false;
        }
        return true;
    }
}