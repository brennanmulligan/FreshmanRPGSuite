package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;

import java.io.Serializable;

/**
 * A message from an area server to a client telling the client to notify the
 * player that they has fulfilled an objective
 *
 * @author Ryan
 */
public class ObjectiveStateChangeMessage extends Message implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int questID;
    private int objectiveID;
    private String objectiveDescription;
    private ObjectiveStateEnum newState;
    private boolean realLifeObjective;
    private String witnessTitle;

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

        ObjectiveStateChangeMessage that = (ObjectiveStateChangeMessage) o;

        if (relevantPlayerID != that.relevantPlayerID)
        {
            return false;
        }
        if (questID != that.questID)
        {
            return false;
        }
        if (objectiveID != that.objectiveID)
        {
            return false;
        }
        if (realLifeObjective != that.realLifeObjective)
        {
            return false;
        }
        if (!objectiveDescription.equals(that.objectiveDescription))
        {
            return false;
        }
        if (newState != that.newState)
        {
            return false;
        }
        return witnessTitle != null ? witnessTitle.equals(that.witnessTitle) :
                that.witnessTitle == null;
    }

    @Override
    public int hashCode()
    {
        int result = relevantPlayerID;
        result = 31 * result + questID;
        result = 31 * result + objectiveID;
        result = 31 * result + objectiveDescription.hashCode();
        result = 31 * result + newState.hashCode();
        result = 31 * result + (realLifeObjective ? 1 : 0);
        result = 31 * result + (witnessTitle != null ? witnessTitle.hashCode() : 0);
        return result;
    }

    /**
     * @param playerID             the current player's id
     * @param questID              the quest id
     * @param objectiveID          the id of the objective
     * @param objectiveDescription the description of the objective
     * @param newState             the new state the objective will be in
     * @param realLifeObjective    true if the player must complete this objective
     *                             in real life
     * @param witnessTitle         if this is a real life objective, the title of the
     *                             person who can witness completion
     */
    public ObjectiveStateChangeMessage(int playerID, boolean quietMessage, int questID, int objectiveID, String objectiveDescription,
                                       ObjectiveStateEnum newState, boolean realLifeObjective, String witnessTitle)
    {
        super(playerID, quietMessage);
        this.questID = questID;
        this.objectiveID = objectiveID;
        this.objectiveDescription = objectiveDescription;
        this.newState = newState;
        this.realLifeObjective = realLifeObjective;
        this.witnessTitle = witnessTitle;
    }

    /**
     * @return the objective's descrption
     */
    public String getObjectiveDescription()
    {
        return objectiveDescription;
    }

    /**
     * @return the objective's ID
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }

    /**
     * @return the state the quest has moved to
     */
    public ObjectiveStateEnum getNewState()
    {
        return newState;
    }


    /**
     * Get the quest's ID
     *
     * @return questID
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return true if this objective must be completed in real life
     */
    public boolean isRealLifeObjective()
    {
        return realLifeObjective;
    }

    /**
     * @return the title of the person who can witness completion if this is a
     * real life objective
     */
    public String getWitnessTitle()
    {
        return witnessTitle;
    }
}
