package edu.ship.engr.shipsim.dataDTO;

import com.google.common.collect.Maps;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Player has a quest that will contain a description id, state, and list of
 * objectives.
 *
 * @author nk3668
 */
public class ClientPlayerQuestStateDTO implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int questID;
    private String questTitle;
    private String questDescription;
    private Date expireDate;
    private QuestStateEnum state;
    private List<ClientPlayerObjectiveStateDTO> objectives = new ArrayList<>();
    private int experiencePointsGained;
    private int objectivesToFulfillment;
    private boolean needingNotification;

    /**
     * Constructor for client player quest
     *
     * @param questID                 the quests id
     * @param questTitle              the quest's title
     * @param questDescription        the quests description
     * @param state                   the quests state
     * @param experiencePointsGained  the number of experience you get when you
     *                                fulfill this quest
     * @param objectivesToFulfillment the number of objectives required to
     *                                fulfill this quest
     * @param needingNotification     true if the player needs to be notified of
     *                                this quest state
     * @param expireDate              date the quest expires
     */
    public ClientPlayerQuestStateDTO(int questID, String questTitle, String questDescription, QuestStateEnum state,
                                     int experiencePointsGained, int objectivesToFulfillment, boolean needingNotification, Date expireDate)
    {
        this.questID = questID;
        this.questTitle = questTitle;
        this.questDescription = questDescription;
        this.state = state;
        this.experiencePointsGained = experiencePointsGained;
        this.objectivesToFulfillment = objectivesToFulfillment;
        this.needingNotification = needingNotification;
        this.expireDate = expireDate;
    }

    /**
     * Add objective to the list of ClientPlayerObjectives
     *
     * @param objective the objective being added
     */
    public void addObjective(ClientPlayerObjectiveStateDTO objective)
    {
        objectives.add(objective);
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
        ClientPlayerQuestStateDTO other = (ClientPlayerQuestStateDTO) obj;
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
        if (objectivesToFulfillment != other.objectivesToFulfillment)
        {
            return false;
        }
        if (experiencePointsGained != other.experiencePointsGained)
        {
            return false;
        }
        if (questDescription == null)
        {
            if (other.questDescription != null)
            {
                return false;
            }
        }
        else if (!questDescription.equals(other.questDescription))
        {
            return false;
        }
        if (questID != other.questID)
        {
            return false;
        }
        if (state != other.state)
        {
            return false;
        }
        return true;
    }

    /**
     * Return the ClientPlayerObjective array list
     *
     * @return objectives
     */
    public List<ClientPlayerObjectiveStateDTO> getObjectiveList()
    {
        return objectives;
    }

    /**
     * Getter for objective list
     *
     * @return the list of the quests objectives
     */
    public int getObjectiveListSize()
    {
        return objectives.size();
    }

    /**
     * @return the number of objectives required to fulfill this quest
     */
    public int getObjectivesToFulfillment()
    {
        return objectivesToFulfillment;
    }

    /**
     * @return the number of experience points you get when you fulfill this
     * quest
     */
    public int getExperiencePointsGained()
    {
        return experiencePointsGained;
    }

    /**
     * Getter for quest desc
     *
     * @return the quest description
     */
    public String getQuestDescription()
    {
        return questDescription;
    }

    /**
     * Getter for quest id
     *
     * @return the quests id
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * Getter for quest state
     *
     * @return the quests state
     */
    public QuestStateEnum getQuestState()
    {
        return state;
    }

    /**
     * @return this quest's title
     */
    public String getQuestTitle()
    {
        return questTitle;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((objectives == null) ? 0 : objectives.hashCode());
        result = prime * result + objectivesToFulfillment;
        result = prime * result + experiencePointsGained;
        result = prime * result + ((questDescription == null) ? 0 : questDescription.hashCode());
        result = prime * result + questID;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    /**
     * @return true if the player has not been notified about the state of this
     * quest
     */
    public boolean isNeedingNotification()
    {
        return needingNotification;
    }

    /**
     * Set the Client Player Objective array list to the given array list
     *
     * @param objectiveList ClientPlayerObjective ArrayList
     */
    public void setObjectives(List<ClientPlayerObjectiveStateDTO> objectiveList)
    {
        this.objectives = objectiveList;
    }

    /**
     * @param newState the new state of the quest
     */
    public void setState(QuestStateEnum newState)
    {
        this.state = newState;
    }

    /**
     * Returns the date the quest expires
     *
     * @return The date the quest expires
     */
    public Date getExpireDate()
    {
        return expireDate;
    }

    public Map<String, Object> toMap()
    {
        Map<String, Object> map = Maps.newLinkedHashMap();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        map.put("questID", getQuestID());
        map.put("questTitle", getQuestTitle());
        map.put("questDescription", getQuestDescription());
        map.put("expireDate", dateFormat.format(getExpireDate()));
        map.put("state", getQuestState());
        map.put("experiencePointsGained", getExperiencePointsGained());
        map.put("objectivesToFulfillment", getObjectivesToFulfillment());
        map.put("needingNotification", isNeedingNotification());
        map.put("objectives", getObjectiveList().stream().map(ClientPlayerObjectiveStateDTO::toMap).collect(Collectors.toList()));

        return map;
    }

}
