package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;

import java.io.Serializable;

/*
 *  @Author Christian Crouthamel, Andrew McCoy
 */
public class updateFriendListMessage extends Message implements Serializable
{

    private static final long serialVersionUID = 1L;
    private FriendDTO friend;

    /*
     * constructor for message
     */
    public updateFriendListMessage(FriendDTO friend, boolean quietMessage)
    {
        super(0, quietMessage);
        this.friend = friend;
    }


    /*
     * get the friend DTO
     */
    public FriendDTO getFriend()
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
        updateFriendListMessage other = (updateFriendListMessage) obj;
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