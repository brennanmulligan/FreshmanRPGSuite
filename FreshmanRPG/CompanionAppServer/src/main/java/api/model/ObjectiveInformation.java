package api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

public class ObjectiveInformation
{
    private String description;
    private int questID;
    private int objectiveID;

    public ObjectiveInformation(String description, int questID, int objectiveID)
    {
        this.description = description;
        this.questID = questID;
        this.objectiveID = objectiveID;
    }

    public String getDescription()
    {
        return description;
    }

    public int getQuestID()
    {
        return questID;
    }

    public int getObjectiveID()
    {
        return objectiveID;
    }


    void setDescription(String description)
    {
        this.description = description;
    }

    void setQuestID(int questID)
    {
        this.questID = questID;
    }

    void setObjectiveID(int objectiveID)
    {
        this.objectiveID = objectiveID;
    }

    @Override
    public String toString()
    {
        return "ObjectiveInformation{" +
                "description='" + description + '\'' +
                ", questID=" + questID +
                ", objectiveID=" + objectiveID +
                '}';
    }

    public String toJSON() throws JsonProcessingException
    {
        return new ObjectMapper().writeValueAsString(this);
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
        ObjectiveInformation that = (ObjectiveInformation) o;
        return questID == that.questID && objectiveID == that.objectiveID && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(description, questID, objectiveID);
    }
}
