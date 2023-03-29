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
import java.util.Objects;
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

    private QuestInfo questInfo;

    @Override
    public String toString()
    {
        return "ClientPlayerQuestStateDTO{" +
                "questInfo=" + questInfo +
                ", questID=" + questID +
                ", state=" + state +
                ", objectives=" + objectives +
                ", needingNotification=" + needingNotification +
                '}';
    }

    private int questID;
    private QuestStateEnum state;
    private List<ClientPlayerObjectiveStateDTO> objectives = new ArrayList<>();
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
                                     int experiencePointsGained, int objectivesToFulfillment, boolean needingNotification, Date expireDate, boolean easterEgg)
    {
        questInfo = new QuestInfo(questTitle, questDescription, experiencePointsGained, objectivesToFulfillment, expireDate, easterEgg);
        this.questID = questID;
        this.state = state;
        this.needingNotification = needingNotification;
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
        return questInfo.objectivesToFulfillment;
    }

    /**
     * @return the number of experience points you get when you fulfill this
     * quest
     */
    public int getExperiencePointsGained()
    {
        return questInfo.experiencePointsGained;
    }

    /**
     * Getter for quest desc
     *
     * @return the quest description
     */
    public String getQuestDescription()
    {
        return questInfo.questDescription;
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
        return questInfo.questTitle;
    }

    /**
     * @return whether or not quest is an easter egg
     */
    public boolean isEasterEgg()
    {
        return questInfo.easterEgg;
    }

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

        ClientPlayerQuestStateDTO that = (ClientPlayerQuestStateDTO) o;

        if (questID != that.questID)
        {
            return false;
        }
        if (needingNotification != that.needingNotification)
        {
            return false;
        }
        if (!Objects.equals(questInfo, that.questInfo))
        {
            return false;
        }
        if (state != that.state)
        {
            return false;
        }
        return Objects.equals(objectives, that.objectives);
    }

    @Override
    public int hashCode()
    {
        int result = questInfo != null ? questInfo.hashCode() : 0;
        result = 31 * result + questID;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (objectives != null ? objectives.hashCode() : 0);
        result = 31 * result + (needingNotification ? 1 : 0);
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
        return questInfo.expireDate;
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

    /**
     * This class holds all information that is unique to a quest and not quest state
     */
    private class QuestInfo implements Serializable
    {
        private String questTitle;
        private String questDescription;
        private Date expireDate;
        private int experiencePointsGained;
        private int objectivesToFulfillment;
        private boolean easterEgg;

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

            QuestInfo questInfo = (QuestInfo) o;

            if (experiencePointsGained != questInfo.experiencePointsGained)
            {
                return false;
            }
            if (objectivesToFulfillment != questInfo.objectivesToFulfillment)
            {
                return false;
            }
            if (easterEgg != questInfo.easterEgg)
            {
                return false;
            }
            if (!Objects.equals(questTitle, questInfo.questTitle))
            {
                return false;
            }
            if (!Objects.equals(questDescription, questInfo.questDescription))
            {
                return false;
            }
            return Objects.equals(expireDate, questInfo.expireDate);
        }

        @Override
        public int hashCode()
        {
            int result = questTitle != null ? questTitle.hashCode() : 0;
            result = 31 * result +
                    (questDescription != null ? questDescription.hashCode() :
                            0);
            result = 31 * result +
                    (expireDate != null ? expireDate.hashCode() : 0);
            result = 31 * result + experiencePointsGained;
            result = 31 * result + objectivesToFulfillment;
            result = 31 * result + (easterEgg ? 1 : 0);
            return result;
        }

        public QuestInfo(String questTitle, String questDescription, int experiencePointsGained, int objectivesToFulfillment, Date expireDate, boolean easterEgg)
        {
            this.questTitle = questTitle;
            this.questDescription = questDescription;
            this.experiencePointsGained = experiencePointsGained;
            this.objectivesToFulfillment = objectivesToFulfillment;
            this.expireDate = expireDate;
            this.easterEgg = easterEgg;
        }
    }
}


