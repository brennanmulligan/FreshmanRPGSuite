package edu.ship.engr.shipsim.dataENUM;

import edu.ship.engr.shipsim.criteria.CriteriaIntegerDTO;
import edu.ship.engr.shipsim.criteria.CriteriaStringDTO;
import edu.ship.engr.shipsim.criteria.InteractableItemActionParameter;
import edu.ship.engr.shipsim.criteria.InteractableNullAction;

/**
 * @author Jake Moore
 */
public enum InteractableItemActionType
{
    /**
     * item should have no action
     */
    NO_ACTION(InteractableNullAction.class),
    /**
     * Item's parameter should contain CriteriaStringDTO type blob data
     */
    MESSAGE(CriteriaStringDTO.class),
    /**
     * This is for message boards. Item's parameter should contain CriteriaStringDTO type blob data
     */
    BOARD(CriteriaStringDTO.class),
    /**
     * Item's parameter should contain CriteriaIntegerDTO type blob data
     */
    BUFF(CriteriaIntegerDTO.class),

    QUEST_TRIGGER(CriteriaStringDTO.class);

    private Class<? extends InteractableItemActionParameter> actionParam;

    InteractableItemActionType(Class<? extends InteractableItemActionParameter> actionParam)
    {
        this.actionParam = actionParam;
    }

    /**
     * @return the ActionParameter
     */
    public Class<? extends InteractableItemActionParameter> getActionParam()
    {
        return this.actionParam;
    }

    /**
     * @return the index of the ActionType in this Enum.
     */
    public int getId()
    {
        return this.ordinal();
    }

    /**
     * @param id - the ID of the ActionType
     * @return the corresponding ActionType
     */
    public static InteractableItemActionType findById(int id)
    {
        return InteractableItemActionType.values()[id];
    }

}
