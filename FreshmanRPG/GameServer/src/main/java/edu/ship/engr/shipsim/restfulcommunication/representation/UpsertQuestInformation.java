package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ship.engr.shipsim.dataDTO.ObjectiveRecordDTO;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datatypes.Position;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpsertQuestInformation
{
    private final int id;
    private final String title;
    private final String description;
    private final ArrayList<ObjectiveRecordDTO> objectives;
    private final int experiencePointsGained;
    private final String triggerMapName;
    private final Position position;
    private final int objectivesForFulfillment;
    private final QuestCompletionActionType completionActionType;
    private final Date startDate;
    private final Date endDate;
    private final boolean easterEgg;

    @JsonCreator
    public UpsertQuestInformation(@JsonProperty("id") int id,
                                  @JsonProperty("title") String title,
                                  @JsonProperty("description") String description,
                                  @JsonProperty("objectives") ArrayList<ObjectiveRecordDTO> objectives,
                                  @JsonProperty("experiencePointsGained") int experiencePointsGained,
                                  @JsonProperty("triggerMapName") String triggerMapName,
                                  @JsonProperty("triggerRow") int triggerRow,
                                  @JsonProperty("triggerCol") int triggerCol,
                                  @JsonProperty("objectivesForFulfillment") int objectivesForFulfillment,
                                  @JsonProperty("completionActionType") int completionActionType,
                                  @JsonProperty("startDate") String startDate,
                                  @JsonProperty("endDate") String endDate,
                                  @JsonProperty("easterEgg") boolean easterEgg)
            throws ParseException
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.objectives = objectives;
        this.experiencePointsGained = experiencePointsGained;
        this.triggerMapName = triggerMapName;
        this.position = new Position(triggerRow, triggerCol);
        this.objectivesForFulfillment = objectivesForFulfillment;
        this.completionActionType = QuestCompletionActionType.findByID(completionActionType);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        this.startDate = dateFormat.parse(startDate);
        this.endDate = dateFormat.parse(endDate);
        this.easterEgg = easterEgg;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public ArrayList<ObjectiveRecordDTO> getObjectives()
    {
        return objectives;
    }

    public int getexperiencePointsGained()
    {
        return experiencePointsGained;
    }

    public String getTriggerMapName()
    {
        return triggerMapName;
    }

    public Position getPosition()
    {
        return position;
    }

    public int getObjectivesForFulfillment()
    {
        return objectivesForFulfillment;
    }

    public QuestCompletionActionType getCompletionActionType()
    {
        return completionActionType;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public boolean isEasterEgg()
    {
        return easterEgg;
    }
}
