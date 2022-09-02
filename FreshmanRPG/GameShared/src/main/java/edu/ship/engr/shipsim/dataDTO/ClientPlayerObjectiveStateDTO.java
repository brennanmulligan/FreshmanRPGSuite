package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;

import java.io.Serializable;

/**
 * Stores the objective for the GameClient which encapsulates the ObjectiveState
 * and Objective on the game server
 *
 * @author Nathaniel
 */
public class ClientPlayerObjectiveStateDTO implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int objectiveID, objectiveXP;

    private String objectiveDescription;
    private ObjectiveStateEnum objectiveState;
    private boolean needingNotification;
    private boolean realLifeObjective;
    private String witnessTitle;
    private QuestStateEnum questState;

    /**
     * Basic constructor for ClientPlayerObjective
     *
     * @param objectiveID          unique identifier for this objective
     * @param objectiveDescription description of the objective
     * @param objectiveXP          xp reward for objective
     * @param objectiveState       current state of this objective using the
     *                             ObjectiveStateList enum
     * @param needingNotification  true if the player needs to be told about the
     *                             state of this objective
     * @param realLifeObjective    true if the player completes this objective
     *                             outside of the game
     * @param witnessTitle         if this is a real life objective, the title of the
     *                             person who can witness completion
     * @param qs                   the state of the quest that the objective belongs to
     */
    public ClientPlayerObjectiveStateDTO(int objectiveID, String objectiveDescription, int objectiveXP,
                                         ObjectiveStateEnum objectiveState, boolean needingNotification, boolean realLifeObjective,
                                         String witnessTitle, QuestStateEnum qs)
    {
        this.objectiveID = objectiveID;
        this.objectiveXP = objectiveXP;
        this.objectiveDescription = objectiveDescription;
        this.objectiveState = objectiveState;
        this.needingNotification = needingNotification;
        this.realLifeObjective = realLifeObjective;
        this.witnessTitle = witnessTitle;
        this.questState = qs;
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
        ClientPlayerObjectiveStateDTO other = (ClientPlayerObjectiveStateDTO) obj;
        if (objectiveDescription == null)
        {
            if (other.objectiveDescription != null)
            {
                return false;
            }
        }
        else if (!objectiveDescription.equals(other.objectiveDescription))
        {
            return false;
        }
        if (objectiveID != other.objectiveID)
        {
            return false;
        }
        if (objectiveState != other.objectiveState)
        {
            return false;
        }
        if (objectiveXP != other.objectiveXP)
        {
            return false;
        }
        if (needingNotification != other.needingNotification)
        {
            return false;
        }
        return true;
    }

    /**
     * Retrieve the objective's description
     *
     * @return objectiveDescription ; The description of the objective
     */
    public String getObjectiveDescription()
    {
        return objectiveDescription;
    }

    /**
     * Retrieve the objective's ID
     *
     * @return objectiveID ; the objective's unique identifier
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }

    /**
     * Retrieve the objective's state
     *
     * @return objectiveState ; The current state of the objective. Uses the
     * enum ObjectiveStateList
     */
    public ObjectiveStateEnum getObjectiveState()
    {
        return objectiveState;
    }

    /**
     * @return the objectiveXP
     */
    public int getObjectiveXP()
    {
        return objectiveXP;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((objectiveDescription == null) ? 0 : objectiveDescription.hashCode());
        result = prime * result + objectiveID;
        result = prime * result + ((objectiveState == null) ? 0 : objectiveState.hashCode());
        result = prime * result + objectiveXP;
        result = prime * result + (needingNotification ? 1231 : 1237);
        return result;
    }

    /**
     * @return true if we should notify the player about this objective's state
     */
    public boolean isNeedingNotification()
    {
        return needingNotification;
    }

    /**
     * @param objectiveDescription the objectiveDescription to set
     */
    public void setObjectiveDescription(String objectiveDescription)
    {
        this.objectiveDescription = objectiveDescription;
    }

    /**
     * @param objectiveID the objectiveID to set
     */
    public void setObjectiveID(int objectiveID)
    {
        this.objectiveID = objectiveID;
    }

    /**
     * @param objectiveState the objectiveState to set
     */
    public void setObjectiveState(ObjectiveStateEnum objectiveState)
    {
        this.objectiveState = objectiveState;
    }

    /**
     * @param objectiveXP the objectiveXP to set
     */
    public void setObjectiveXP(int objectiveXP)
    {
        this.objectiveXP = objectiveXP;
    }

    /**
     * @return true if this objective must be completed in real life
     */
    public boolean isRealLifeObjective()
    {
        return realLifeObjective;
    }

    /**
     * @return if this is a real life objective, the title of the person who can
     * witness completion
     */
    public String getWitnessTitle()
    {
        return witnessTitle;
    }

    /**
     * @return the state of the quest that the objective belongs to
     */
    public QuestStateEnum getQuestState()
    {
        return questState;
    }

}
