package edu.ship.engr.shipsim.model.reports;


import edu.ship.engr.shipsim.model.Report;

/**
 * Carries information from a friend request
 *
 * @author Joshua Wood, Evan Reese
 */

public final class FriendConnectionReceivedReport implements Report
{


    private final int senderID;
    private final int receiverID;

    /**
     * @param senderID
     * @param receiverID
     */
    public FriendConnectionReceivedReport(int senderID, int receiverID)
    {
        this.senderID = senderID;
        this.receiverID = receiverID;
    }


    /**
     * @return the player who receives the text
     */
    public int getReceiverID()
    {
        return receiverID;
    }


    /**
     * @return The name of the player who sent the message
     */
    public int getSenderID()
    {
        return senderID;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + receiverID;
        result = prime * result + senderID;
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
        FriendConnectionReceivedReport other = (FriendConnectionReceivedReport) obj;
        if (receiverID != other.receiverID)
        {
            return false;
        }
        if (senderID != other.senderID)
        {
            return false;
        }
        return true;
    }


}
