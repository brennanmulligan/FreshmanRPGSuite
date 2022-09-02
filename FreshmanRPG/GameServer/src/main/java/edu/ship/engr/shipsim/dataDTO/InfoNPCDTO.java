package edu.ship.engr.shipsim.dataDTO;

/**
 * DTO resembling Information sequences between a player and info npc.
 *
 * @author John Lang and Noah Macminn
 */
public class InfoNPCDTO
{

    private String response;
    private String information;
    private int npcID;

    public InfoNPCDTO(String response, String information, int npcID)
    {
        this.response = response;
        this.information = information;
        this.npcID = npcID;
    }

    public String getResponse()
    {
        return this.response;
    }

    public String getInformation()
    {
        return this.information;
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
        InfoNPCDTO other = (InfoNPCDTO) obj;
        if (response == null)
        {
            if (other.response != null)
            {
                return false;
            }
        }
        else if (!response.equals(other.response))
        {
            return false;
        }
        else if (!information.equals(other.information))
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
        result = prime * result + ((response == null) ? 0 : response.hashCode());
        result = prime * result + ((information == null) ? 0 : information.hashCode());
        result = prime * result + npcID;

        return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String s = "Response And Info Pairing(User Response Triggering Information: " + this.getResponse() +
                ", Information provided to User: " + this.getInformation() + ", NCP ID: " + this.getNpcID();

        return s;
    }
}
