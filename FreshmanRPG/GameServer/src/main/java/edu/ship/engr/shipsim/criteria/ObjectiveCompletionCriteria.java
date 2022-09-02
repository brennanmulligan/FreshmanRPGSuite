package edu.ship.engr.shipsim.criteria;

import java.io.Serializable;

/**
 * A marker interface for things that can be used as criteria for completing an
 * objective
 *
 * @author Merlin
 */
public interface ObjectiveCompletionCriteria extends Serializable
{
    /**
     * @return string representation of the criteria
     */
    @Override
    public String toString();
}
