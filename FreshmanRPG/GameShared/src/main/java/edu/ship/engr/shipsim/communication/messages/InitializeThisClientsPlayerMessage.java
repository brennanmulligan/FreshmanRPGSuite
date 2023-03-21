package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Merlin
 * @author Olivia
 * @author LaVonne
 */
public class InitializeThisClientsPlayerMessage extends Message implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<ClientPlayerQuestStateDTO> clientPlayerQuestList;
    private ArrayList<FriendDTO> friends;
    private int experiencePts;
    private LevelRecord level;
    private int doubloons;


    @Override
    public String toString()
    {
        return "InitializeThisClientsPlayerMessage{" +
                "clientPlayerQuestList=" + clientPlayerQuestList +
                ", friends=" + friends +
                ", experiencePts=" + experiencePts +
                ", level=" + level +
                ", doubloons=" + doubloons +
                '}';
    }

    /**
     * @param relevantPlayerID the player that has just connected
     * @param clientPlayerQuestList players quest list
     * @param friends               of this player
     * @param experiencePts         player's experience points
     * @param doubloons             for this player
     * @param level                 LevelRecord
     */
    public InitializeThisClientsPlayerMessage(int relevantPlayerID, boolean quietMessage, List<ClientPlayerQuestStateDTO> clientPlayerQuestList, ArrayList<FriendDTO> friends, int experiencePts,
                                              int doubloons, LevelRecord level)
    {
        super(relevantPlayerID, quietMessage);
        this.friends = friends;
        this.clientPlayerQuestList = clientPlayerQuestList;
        this.experiencePts = experiencePts;
        this.doubloons = doubloons;
        this.level = level;
    }

    /**
     * Return current players quest List
     *
     * @return quest list
     */
    public List<ClientPlayerQuestStateDTO> getClientPlayerQuestList()
    {
        return clientPlayerQuestList;
    }


    /**
     * Return current players friend list
     *
     * @return friend list
     */
    public ArrayList<FriendDTO> getFriends()
    {
        return friends;
    }

    /**
     * Get experience points of this client's player
     *
     * @return experience pts
     */
    public int getExperiencePts()
    {
        return experiencePts;
    }

    /**
     * Get level of this client's player
     *
     * @return level
     */
    public LevelRecord getLevel()
    {
        return level;
    }

    /**
     * @return the doubloons of this player
     */
    public int getDoubloons()
    {
        return doubloons;
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientPlayerQuestList == null) ? 0 : clientPlayerQuestList.hashCode());
        result = prime * result + experiencePts;
        result = prime * result + ((friends == null) ? 0 : friends.hashCode());
        result = prime * result + doubloons;
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
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
        if (!(obj instanceof InitializeThisClientsPlayerMessage))
        {
            return false;
        }
        InitializeThisClientsPlayerMessage other = (InitializeThisClientsPlayerMessage) obj;
        if (clientPlayerQuestList == null)
        {
            if (other.clientPlayerQuestList != null)
            {
                return false;
            }
        }
        else if (!clientPlayerQuestList.equals(other.clientPlayerQuestList))
        {
            return false;
        }
        if (experiencePts != other.experiencePts)
        {
            return false;
        }
        if (friends == null)
        {
            if (other.friends != null)
            {
                return false;
            }
        }
        else if (!friends.equals(other.friends))
        {
            return false;
        }
        if (doubloons != other.doubloons)
        {
            return false;
        }
        if (level == null)
        {
            if (other.level != null)
            {
                return false;
            }
        }
        else if (!level.equals(other.level))
        {
            return false;
        }
        return true;
    }
}
