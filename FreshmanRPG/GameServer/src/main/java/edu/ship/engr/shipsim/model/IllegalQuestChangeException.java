package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.QuestStateEnum;

/**
 * Thrown if you try to change quest states to an illegal state.
 *
 * @author nk3668
 */
public class IllegalQuestChangeException extends Exception
{
    /**
     * ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The message thrown if you try to change to a state with the wrong
     * parameters
     *
     * @param from the state the quest is currently in
     * @param to   the state we are trying to change to
     */
    public IllegalQuestChangeException(QuestStateEnum from, QuestStateEnum to)
    {
        super("You cannot change quest states from " + from + " to " + to);
    }
}
