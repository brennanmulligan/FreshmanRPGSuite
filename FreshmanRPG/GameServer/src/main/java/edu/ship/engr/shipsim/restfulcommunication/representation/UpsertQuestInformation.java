package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datatypes.Position;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpsertQuestInformation
{
    private final int id;
    private final String title;
    private final String description;
    private final int xpGained;
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
                                  @JsonProperty("xpGained") int xpGained,
                                  @JsonProperty("triggerMapName") String triggerMapName,
                                  @JsonProperty("triggerRow") int triggerRow,
                                  @JsonProperty("triggerCol") int triggerCol,
                                  @JsonProperty("objectivesForFulfillment") int objectivesForFulfillment,
                                  @JsonProperty("completionActionType") int completionActionType,
                                  @JsonProperty("startDate") String startDate,
                                  @JsonProperty("endDate") String endDate,
                                  @JsonProperty("easterEgg") boolean easterEgg)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.id = id;
        this.title = title;
        this.description = description;
        this.xpGained = xpGained;
        this.triggerMapName = triggerMapName;
        this.position = new Position(triggerRow, triggerCol);
        this.objectivesForFulfillment = objectivesForFulfillment;
        this.completionActionType = QuestCompletionActionType.findByID(completionActionType);
        try
        {
            this.startDate = dateFormat.parse(startDate);
            this.endDate = dateFormat.parse(endDate);
        }
        catch (ParseException e)
        {
            // TODO: figure out how to throw error back to front end from here
            throw new RuntimeException(e);
        }
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

    public int getXpGained()
    {
        return xpGained;
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