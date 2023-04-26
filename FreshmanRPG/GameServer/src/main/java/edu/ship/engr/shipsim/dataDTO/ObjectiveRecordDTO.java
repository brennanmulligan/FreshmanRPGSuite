package edu.ship.engr.shipsim.dataDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ObjectiveRecordDTO implements Serializable
{
    @Serial
    private static final long serialVersionUId = 1L;
    private final int id;
    private final String description;
    private final int experiencePointsGained;
    private final int questID;
    private final int completionType;

    public ObjectiveRecordDTO(int id, String description, int experiencePointsGained, int questID, int completionType)
    {
        this.id = id;
        this.description = description;
        this.experiencePointsGained = experiencePointsGained;
        this.questID = questID;
        this.completionType = completionType;
    }

    public int getID()
    {
        return id;
    }

    public String getDescription()
    {
        return description;
    }

    public int getExperiencePointsGained()
    {
        return experiencePointsGained;
    }

    public int getQuestID()
    {
        return questID;
    }

    public int getCompletionType()
    {
        return completionType;
    }

    @Override
    public String toString()
    {
        return "ObjectiveRecordDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", experiencePointsGained=" + experiencePointsGained +
                ", questID=" + questID +
                ", completionType=" + completionType +
                '}';
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
        ObjectiveRecordDTO that = (ObjectiveRecordDTO) o;
        return id == that.id &&
                experiencePointsGained == that.experiencePointsGained &&
                questID == that.questID &&
                completionType == that.completionType &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, description, experiencePointsGained, questID,
                completionType);
    }
}
