package criteria;

import java.util.Objects;

/**
 * Contains information about an NPCs response
 *
 * @author Ryan, Ktyal
 */
public class NPCResponseDTO implements ObjectiveCompletionCriteria, InteractableItemActionParameter
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String response;
    private String NPCName;

    /**
     * @param response the string that is the criteria
     * @param NPCName the name of the NPC
     */
    public NPCResponseDTO(String response, String NPCName)
    {
        this.response = response;
        this.NPCName = NPCName;
    }

    /**
     *
     * @return the npc's response to the player
     */
    public String getResponse()
    {
        return response;
    }

    /**
     *
     * @return the name of the NPC
     */
    public String getNPCName()
    {
        return NPCName;
    }

    /**
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "NPCResponseDTO{" +
                "response='" + response + '\'' +
                ", NPCName='" + NPCName + '\'' +
                '}';
    }

    /**
     * Checks if the objects are equal
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        NPCResponseDTO that = (NPCResponseDTO) o;
        return response.equals(that.response) && NPCName.equals(that.NPCName);
    }

    /**
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(response, NPCName);
    }
}
