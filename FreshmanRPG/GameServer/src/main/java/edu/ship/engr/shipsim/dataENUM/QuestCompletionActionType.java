package edu.ship.engr.shipsim.dataENUM;

import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.criteria.QuestCompletionActionParameter;
import edu.ship.engr.shipsim.criteria.QuestListCompletionParameter;

/**
 * @author Merlin
 */
public enum QuestCompletionActionType
{
    /**
     *
     */
    TELEPORT(GameLocationDTO.class),

    /**
     *
     */
    NO_ACTION(null),

    /**
     *
     */
    TRIGGER_QUESTS(QuestListCompletionParameter.class),

    /**
     *
     */
    ADD_VANITY_TO_INVENTORY(null);

    private Class<? extends QuestCompletionActionParameter>
            completionActionParameterType;

    /**
     * @return the type of information for parameters of this quest completion
     * action.
     */
    public Class<? extends QuestCompletionActionParameter> getCompletionActionParameterType()
    {
        return completionActionParameterType;
    }

    QuestCompletionActionType(
            Class<? extends QuestCompletionActionParameter> paramType)
    {
        this.completionActionParameterType = paramType;
    }

    /**
     * @return the unique id for one element of the enum
     */
    public int getID()
    {
        return this.ordinal();
    }

    /**
     * @param int1 the unique ID of the element we are searching for
     * @return the member of the enum that matches the given ID
     */
    public static QuestCompletionActionType findByID(int int1)
    {
        return QuestCompletionActionType.values()[int1];
    }
}
