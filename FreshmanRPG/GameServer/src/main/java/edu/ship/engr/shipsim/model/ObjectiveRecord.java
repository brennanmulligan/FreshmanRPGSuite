package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.ObjectiveCompletionCriteria;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;

/**
 * Data Transfer Object for the Objective Data Gateway to deliver records.
 *
 * @author merlin
 */
public final class ObjectiveRecord
{
    private int objectiveID;
    private String objectiveDescription;
    private int questID;
    private int experiencePointsGained;
    private ObjectiveCompletionCriteria completionCriteria;
    private ObjectiveCompletionType completionType;

    /**
     * Create it
     *
     * @param questID                the unique ID of the quest that contains the objective
     * @param objectiveID            the objective's unique ID
     * @param objectiveDescription   the objective's description
     * @param experiencePointsGained the number of points earned by completing
     *                               this objective
     * @param completionType         the type of action the player must do to complete
     *                               this objective
     * @param completionCriteria     the criteria for satisfying this objective
     */
    public ObjectiveRecord(int questID, int objectiveID, String objectiveDescription, int experiencePointsGained,
                           ObjectiveCompletionType completionType, ObjectiveCompletionCriteria completionCriteria)
    {
        this.objectiveID = objectiveID;
        this.objectiveDescription = objectiveDescription;
        this.questID = questID;
        this.experiencePointsGained = experiencePointsGained;
        this.completionType = completionType;
        this.completionCriteria = completionCriteria;
    }

    /**
     * @return the type of action the player must do to complete this objective
     */
    public ObjectiveCompletionType getCompletionType()
    {
        return completionType;
    }

    /**
     * retrieve the objective's ID
     *
     * @return objectiveID the objective's unique ID
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }

    /**
     * Added For Support of JavaFX TableView
     *
     * @return objectiveID
     */
    public int getObjectiveId()
    {
        return this.getObjectiveID();
    }


    /**
     * retrieve the objective's description
     *
     * @return objectiveDescription
     */
    public String getObjectiveDescription()
    {
        return objectiveDescription;
    }

    /**
     * retrieve the quest's ID
     *
     * @return questID the unique ID for the objective's quest
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * Added For Support of JavaFX TableView
     *
     * @return questID;
     */
    public int getQuestId()
    {
        return this.getQuestID();
    }

    /**
     * Sets objective Id
     *
     * @param objectiveID id
     */
    public void setObjectiveID(int objectiveID)
    {
        this.objectiveID = objectiveID;
    }

    /**
     * Sets objective description
     *
     * @param objectiveDescription description
     */
    public void setObjectiveDescription(String objectiveDescription)
    {
        this.objectiveDescription = objectiveDescription;
    }

    /**
     * Sets questID
     *
     * @param questID id
     */
    public void setQuestID(int questID)
    {
        this.questID = questID;
    }

    /**
     * Sets the experiencePointsGained variable
     *
     * @param experiencePointsGained xp gained
     */
    public void setExperiencePointsGained(int experiencePointsGained)
    {
        this.experiencePointsGained = experiencePointsGained;
    }

    /**
     * Sets the completion criteria
     *
     * @param completionCriteria criteria
     */
    public void setCompletionCriteria(ObjectiveCompletionCriteria completionCriteria)
    {
        this.completionCriteria = completionCriteria;
    }

    /**
     * Sets the completion type
     *
     * @param completionType type
     */
    public void setCompletionType(ObjectiveCompletionType completionType)
    {
        this.completionType = completionType;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "Quest " + questID + ":     " + "Objective  " + objectiveID + "      " + objectiveDescription;
    }


    /**
     * @return the number of points you get when you complete this objective
     */
    public int getExperiencePointsGained()
    {
        return experiencePointsGained;
    }

    /**
     * @return the criteria for completing this objective
     */
    public ObjectiveCompletionCriteria getCompletionCriteria()
    {
        return completionCriteria;
    }

    /**
     * @return true if the objective must be completed in real life
     */
    public boolean isRealLifeObjective()
    {
        return completionType == ObjectiveCompletionType.REAL_LIFE;
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
        result = prime * result + ((completionCriteria == null) ? 0 : completionCriteria.hashCode());
        result = prime * result + ((completionType == null) ? 0 : completionType.hashCode());
        result = prime * result + experiencePointsGained;
        result = prime * result + questID;
        return result;
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
        ObjectiveRecord other = (ObjectiveRecord) obj;
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
        if (completionCriteria == null)
        {
            if (other.completionCriteria != null)
            {
                return false;
            }
        }
        else if (!completionCriteria.equals(other.completionCriteria))
        {
            return false;
        }
        if (completionType != other.completionType)
        {
            return false;
        }
        if (experiencePointsGained != other.experiencePointsGained)
        {
            return false;
        }
        if (questID != other.questID)
        {
            return false;
        }
        return true;
    }
}
