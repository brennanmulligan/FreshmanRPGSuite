package edu.ship.engr.shipsim.dataENUM;

import edu.ship.engr.shipsim.criteria.*;

/**
 * The list of ways objectives can be completed
 *
 * @author Merlin
 */
public enum ObjectiveCompletionType
{
    /**
     *
     */
    REAL_LIFE(CriteriaStringDTO.class),

    /**
     *
     */
    MOVEMENT(GameLocationDTO.class),

    /**
     *
     */
    CHAT(CriteriaStringDTO.class),

    /**
     *
     */
    CHAT_RECEIVED(NPCResponseDTO.class),

    /**
     *
     */
    DOUBLOONS(CriteriaIntegerDTO.class),

    /**
     *
     */
    KEYSTROKE(CriteriaStringDTO.class),

    /**
     *
     */
    INTERACT(CriteriaIntegerDTO.class),


    /**
     *
     */
    TERMINAL(CriteriaStringDTO.class),


    /**
     *
     */
    FRIENDS(CriteriaIntegerDTO.class),


    /**
     *
     */
    TIMED(CriteriaTimerDTO.class);

    /**
     * Get the completion type with a given ID
     *
     * @param id the ID
     * @return the appropriate completion type
     */
    public static ObjectiveCompletionType findByID(int id)
    {
        return ObjectiveCompletionType.values()[id];
    }

    private Class<? extends ObjectiveCompletionCriteria> completionCriteriaType;

    ObjectiveCompletionType(Class<? extends ObjectiveCompletionCriteria> completionCriteriaType)
    {
        this.completionCriteriaType = completionCriteriaType;
    }

    /**
     * @return the class of the objective completion criteria
     */
    public Class<? extends ObjectiveCompletionCriteria> getCompletionCriteriaType()
    {
        return completionCriteriaType;
    }

    /**
     * @return the unique ID of the completion type
     */
    public int getID()
    {
        return this.ordinal();
    }
}
