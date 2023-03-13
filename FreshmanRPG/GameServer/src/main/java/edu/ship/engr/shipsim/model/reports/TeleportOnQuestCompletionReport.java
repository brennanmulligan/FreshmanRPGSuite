package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.criteria.GameLocationDTO;

/**
 * Report that the client needs to be teleported somewhere
 *
 * @author Chris Hersh, Zach Thompson
 */
public class TeleportOnQuestCompletionReport extends SendMessageReport
{

    private final int playerID;
    private final int questID;

    private final GameLocationDTO location;
    private final String hostName;
    private final int portNumber;

    /**
     * @param id       id of the player
     * @param questID  id of the quest
     * @param gl       location to teleport
     * @param hostName name of the host
     * @param port     port the client should connect to
     */
    public TeleportOnQuestCompletionReport(int id, int questID, GameLocationDTO gl, String hostName, int port)
    {
        super(id, true);
        this.playerID = id;
        this.questID = questID;
        this.location = gl;
        this.hostName = hostName;
        this.portNumber = port;
    }

    /**
     * @return player ID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return quest ID
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return map name
     */
    public GameLocationDTO getLocation()
    {
        return location;
    }

    /**
     * @return host name
     */
    public String getHostName()
    {
        return hostName;
    }

    /**
     * @return port number
     */
    public int getPortNumber()
    {
        return portNumber;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
        result = prime * result + portNumber;
        result = prime * result + playerID;
        result = prime * result + questID;
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
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
        if (getClass() != obj.getClass())
        {
            return false;
        }
        TeleportOnQuestCompletionReport other = (TeleportOnQuestCompletionReport) obj;
        if (playerID != other.getPlayerID())
        {
            return false;
        }
        if (questID != other.getQuestID())
        {
            return false;
        }
        if (portNumber != other.getPortNumber())
        {
            return false;
        }
        if (!location.equals(other.getLocation()))
        {
            return false;
        }
        if (!hostName.equals(other.getHostName()))
        {
            return false;
        }
        return true;
    }

}
