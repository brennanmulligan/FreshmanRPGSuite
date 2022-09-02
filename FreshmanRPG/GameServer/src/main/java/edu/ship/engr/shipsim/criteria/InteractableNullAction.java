package edu.ship.engr.shipsim.criteria;

/**
 * The null-action type for interactableItems, rather than setting it to a null
 * value.
 *
 * @author Jake Moore and Adam Pine
 */
public class InteractableNullAction implements InteractableItemActionParameter
{
    private static final long serialVersionUID = 1L;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "No Action";
    }
}
