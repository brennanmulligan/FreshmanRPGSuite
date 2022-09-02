package edu.ship.engr.shipsim.criteria;

import java.io.Serializable;

/**
 * A marker interface for classes that can be used as parameters for quest
 * completion actions
 *
 * @author Merlin
 */
public interface QuestCompletionActionParameter extends Serializable
{
    /**
     * @return string representation of the criteria
     */
    @Override
    public String toString();
}
