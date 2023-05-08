package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.criteria.QuestCompletionActionParameter;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.ObjectiveRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class QuestRecordDTO
{
    private String title;
    private String description;
    private ArrayList<ObjectiveRecordDTO> objectives;
    private int questID;
    private String mapName;
    private Position position;
    private int experiencePointsGained;
    private int objectivesForFulfillment;
    private ActionTypeDTO actionType;
    private String startDate;
    private String endDate;
    private boolean easterEgg;

    public QuestRecordDTO(String title, String description,
                          ArrayList<ObjectiveRecordDTO> objectives, int questID,
                          String mapName, Position position,
                          int experiencePointsGained,
                          int objectivesForFulfillment,
                          ActionTypeDTO actionType, Date startDate,
                          Date endDate,
                          boolean easterEgg)
    {
        this.title = title;
        this.description = description;
        this.objectives = objectives;
        this.questID = questID;
        this.mapName = mapName;
        this.position = position;
        this.experiencePointsGained = experiencePointsGained;
        this.objectivesForFulfillment = objectivesForFulfillment;
        this.actionType = actionType;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        this.startDate = dateFormat.format(startDate);
        this.endDate = dateFormat.format(endDate);
        this.easterEgg = easterEgg;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ArrayList<ObjectiveRecordDTO> getObjectives()
    {
        return objectives;
    }

    public void setObjectives(
            ArrayList<ObjectiveRecordDTO> objectives)
    {
        this.objectives = objectives;
    }

    public int getQuestID()
    {
        return questID;
    }

    public void setQuestID(int questID)
    {
        this.questID = questID;
    }

    public String getMapName()
    {
        return mapName;
    }

    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public int getExperiencePointsGained()
    {
        return experiencePointsGained;
    }

    public void setExperiencePointsGained(int experiencePointsGained)
    {
        this.experiencePointsGained = experiencePointsGained;
    }

    public int getObjectivesForFulfillment()
    {
        return objectivesForFulfillment;
    }

    public void setObjectivesForFulfillment(int objectivesForFulfillment)
    {
        this.objectivesForFulfillment = objectivesForFulfillment;
    }

    public ActionTypeDTO getActionType()
    {
        return actionType;
    }

    public void setActionType(ActionTypeDTO actionType)
    {
        this.actionType = actionType;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public boolean isEasterEgg()
    {
        return easterEgg;
    }

    public void setEasterEgg(boolean easterEgg)
    {
        this.easterEgg = easterEgg;
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
        QuestRecordDTO that = (QuestRecordDTO) o;
        return questID == that.questID &&
                experiencePointsGained == that.experiencePointsGained &&
                objectivesForFulfillment == that.objectivesForFulfillment &&
                easterEgg == that.easterEgg && title.equals(that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(objectives, that.objectives) &&
                Objects.equals(mapName, that.mapName) &&
                Objects.equals(position, that.position) &&
                Objects.equals(actionType, that.actionType) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(title, description, objectives, questID, mapName,
                position, experiencePointsGained, objectivesForFulfillment,
                actionType, startDate, endDate, easterEgg);
    }
}
