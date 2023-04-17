package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.QuestCompletionActionParameter;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datatypes.Position;

import java.util.ArrayList;
import java.util.Date;

/**
 * A DTO that encapsulates all of the information about a quest and its objectives
 *
 * @author Scott Lantz, LaVonne Diller
 */
public class QuestRecord
{
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((objectives == null) ? 0 : objectives.hashCode());
        result = prime * result + objectivesForFulfillment;
        result = prime * result + ((completionActionParameter == null) ? 0 : completionActionParameter.hashCode());
        result = prime * result + ((completionActionType == null) ? 0 : completionActionType.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + experiencePointsGained;
        result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + questID;
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        QuestRecord other = (QuestRecord) obj;
        if (objectives == null)
        {
            if (other.objectives != null)
            {
                return false;
            }
        }
        else if (!objectives.equals(other.objectives))
        {
            return false;
        }
        if (objectivesForFulfillment != other.objectivesForFulfillment)
        {
            return false;
        }
        if (completionActionParameter == null)
        {
            if (other.completionActionParameter != null)
            {
                return false;
            }
        }
        else if (!completionActionParameter.equals(other.completionActionParameter))
        {
            return false;
        }
        if (completionActionType != other.completionActionType)
        {
            return false;
        }
        if (description == null)
        {
            if (other.description != null)
            {
                return false;
            }
        }
        else if (!description.equals(other.description))
        {
            return false;
        }
        if (endDate == null)
        {
            if (other.endDate != null)
            {
                return false;
            }
        }
        else if (!endDate.equals(other.endDate))
        {
            return false;
        }
        if (experiencePointsGained != other.experiencePointsGained)
        {
            return false;
        }
        if (mapName == null)
        {
            if (other.mapName != null)
            {
                return false;
            }
        }
        else if (!mapName.equals(other.mapName))
        {
            return false;
        }
        if (position == null)
        {
            if (other.position != null)
            {
                return false;
            }
        }
        else if (!position.equals(other.position))
        {
            return false;
        }
        if (questID != other.questID)
        {
            return false;
        }
        if (startDate == null)
        {
            if (other.startDate != null)
            {
                return false;
            }
        }
        else if (!startDate.equals(other.startDate))
        {
            return false;
        }
        if (title == null)
        {
            if (other.title != null)
            {
                return false;
            }
        }
        else if (!title.equals(other.title))
        {
            return false;
        }
        return true;
    }

    private String title;
    private String description;
    private ArrayList<ObjectiveRecord> objectives;
    private int questID;
    private String mapName;

    private Position position;
    private int experiencePointsGained;
    private int objectivesForFulfillment;
    private QuestCompletionActionType completionActionType;
    private QuestCompletionActionParameter completionActionParameter;
    private Date startDate;
    private Date endDate;

    private boolean easterEgg;

    /**
     * Creates a Quest Object
     *
     * @param id                        the id
     * @param title                     The quest's title
     * @param desc                      the description
     * @param map                       the map that the quest is on
     * @param pos                       position of the quest
     * @param objectives                the list of objectives
     * @param experiencePointsGained    the number of points we get when we fulfill
     *                                  this quest
     * @param objectivesForFulfillment  the number of objectives we have to
     *                                  complete to fulfill this quest
     * @param completionActionType      the type of action to do on completing a
     *                                  quest
     * @param completionActionParameter parameter for the action type
     * @param startDate                 The first day the quest is available
     * @param endDate                   The last day the quest is available
     */

    public QuestRecord(int id, String title, String desc, String map, Position pos, ArrayList<ObjectiveRecord> objectives,
                       int experiencePointsGained, int objectivesForFulfillment, QuestCompletionActionType completionActionType,
                       QuestCompletionActionParameter completionActionParameter, Date startDate, Date endDate, boolean easterEgg)
    {
        this.questID = id;
        this.title = title;
        this.description = desc;
        this.mapName = map;
        this.position = pos;
        this.objectives = objectives;
        this.experiencePointsGained = experiencePointsGained;
        this.objectivesForFulfillment = objectivesForFulfillment;
        this.completionActionType = completionActionType;
        this.completionActionParameter = completionActionParameter;
        this.startDate = startDate;
        this.endDate = endDate;
        this.easterEgg = easterEgg;
    }

    /**
     * Get objetive by specific objective id
     *
     * @param objectiveID id of the objective
     * @return objective record
     */
    public ObjectiveRecord getObjectiveID(int objectiveID)
    {
        for (ObjectiveRecord objective : objectives)
        {
            if (objective.getObjectiveID() == objectiveID)
            {
                return objective;
            }
        }

        return null;

    }

    /**
     * Get objective description by specific objective id
     *
     * @param objectiveID id of the objective
     * @return objective description
     */
    public String getObjectiveDescription(int objectiveID)
    {
        for (ObjectiveRecord a : objectives)
        {
            if (a.getObjectiveID() == objectiveID)
            {
                return a.getObjectiveDescription();
            }
        }

        return null;

    }

    /**
     * @return list_objectives the quest's objectives
     */
    public ArrayList<ObjectiveRecord> getObjectives()
    {
        return objectives;
    }

    /**
     * @return the number of objectives necessary to fulfill this quest
     */
    public int getObjectivesForFulfillment()
    {
        return objectivesForFulfillment;
    }

    /**
     * Get objective description by specific objective id
     *
     * @param objectiveID id of the objective
     * @return objective description
     */
    public int getObjectiveXP(int objectiveID)
    {
        for (ObjectiveRecord a : objectives)
        {
            if (a.getObjectiveID() == objectiveID)
            {
                return a.getExperiencePointsGained();
            }
        }

        return 0;

    }

    /**
     * @return parameter for the action type
     */
    public QuestCompletionActionParameter getCompletionActionParameter()
    {
        return this.completionActionParameter;

    }

    /**
     * @return the type of action to do on completing a quest
     */
    public QuestCompletionActionType getCompletionActionType()
    {
        return completionActionType;
    }

    /**
     * @return q_description the quest's description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * @return the number of experience points gained when we fulfill this quest
     */
    public int getExperiencePointsGained()
    {
        return experiencePointsGained;
    }

    /**
     * Return the map name the quest is on
     *
     * @return map name
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * Return the position of the quest
     *
     * @return position of quest
     */
    public Position getPos()
    {
        return position;
    }

    /**
     * @return q_id the quest id
     */
    public int getQuestID()
    {
        return this.questID;
    }

    /**
     * @return this quest's title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @return The first day the quest is available
     */
    public Date getStartDate()
    {
        return startDate;
    }

    /**
     * @return The end day the quest is available
     */
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * @return easterEgg status of quest
     */
    public boolean isEasterEgg()
    {
        return easterEgg;
    }

    /**
     * Sets the quests objective list
     *
     * @param objectives the new objective list
     */
    public void setObjectives(ArrayList<ObjectiveRecord> objectives)
    {
        this.objectives = objectives;
    }

    /**
     * Sets the quests description
     *
     * @param newDesc the new description
     */
    public void setDescription(String newDesc)
    {
        this.description = newDesc;
    }

    /**
     * Set the quest's map name
     *
     * @param mapName the map that the quest is on
     */
    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    /**
     * Set the position of the quest
     *
     * @param pos position of the quest
     */
    public void setPos(Position pos)
    {
        this.position = pos;
    }

    /**
     * Sets the quests id
     *
     * @param newId the new id
     */
    public void setQuestID(int newId)
    {
        this.questID = newId;
    }

    /**
     * @param title the new title for this quest
     */
    public void setTitle(String title)
    {
        this.title = title;
    }


    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public void setExperiencePointsGained(int experiencePointsGained)
    {
        this.experiencePointsGained = experiencePointsGained;
    }

    public void setObjectivesForFulfillment(int objectivesForFulfillment)
    {
        this.objectivesForFulfillment = objectivesForFulfillment;
    }

    public void setCompletionActionType(
            QuestCompletionActionType completionActionType)
    {
        this.completionActionType = completionActionType;
    }

    public void setCompletionActionParameter(
            QuestCompletionActionParameter completionActionParameter)
    {
        this.completionActionParameter = completionActionParameter;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public void setEasterEgg(boolean easterEgg)
    {
        this.easterEgg = easterEgg;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "Quest " + questID + ": " + description;
    }
}
