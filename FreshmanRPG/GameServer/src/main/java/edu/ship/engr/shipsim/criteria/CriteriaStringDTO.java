package edu.ship.engr.shipsim.criteria;

/**
 * Wraps a string so it can be the criteria for completing an objective
 *
 * @author Merlin
 */
public class CriteriaStringDTO implements ObjectiveCompletionCriteria, InteractableItemActionParameter
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String string;

    /**
     * @param string the string that is the criteria
     */
    public CriteriaStringDTO(String string)
    {
        this.string = string;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((string == null) ? 0 : string.hashCode());
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
        CriteriaStringDTO other = (CriteriaStringDTO) obj;
        if (string == null)
        {
            if (other.string != null)
            {
                return false;
            }
        }
        else if (!string.equals(other.string))
        {
            return false;
        }
        return true;
    }

    /**
     * Also acts as our getString for some reason.
     *
     * @return the contained string
     * @see java.lang.Object#toString()
     */
    public String getString()
    {
        return string;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "Criteria String: " + this.string;
    }
}
