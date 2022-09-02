package edu.ship.engr.shipsim.criteria;

import java.io.Serializable;

/**
 * Used with CriteriaDTOs, so that we can code to this interface, and be able to
 * reuse the same DTOs
 *
 * @author Jake Moore, Adam Pine
 */
public interface InteractableItemActionParameter extends Serializable
{
    /**
     * @return string representation of the criteria
     */
    @Override
    public String toString();
}
