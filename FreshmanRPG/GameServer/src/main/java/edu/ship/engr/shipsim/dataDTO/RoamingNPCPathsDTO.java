package edu.ship.engr.shipsim.dataDTO;

/**
 * @author John Lang and Noah Macminn
 */
public class RoamingNPCPathsDTO
{
    private String path;
    private int npcID;

    public RoamingNPCPathsDTO(String path, int npcID)
    {
        this.path = path;
        this.npcID = npcID;
    }

    public String getPath()
    {
        return this.path;
    }

    public int getNpcID()
    {
        return this.npcID;
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
        RoamingNPCPathsDTO other = (RoamingNPCPathsDTO) obj;
        if (path == null)
        {
            if (other.path != null)
            {
                return false;
            }
        }
        else if (!path.equals(other.path))
        {
            return false;
        }
        if (npcID != other.npcID)
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + npcID;

        return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String s = "Path: " + this.getPath() + " used by NPC ID: " + this.getNpcID();

        return s;
    }


}
