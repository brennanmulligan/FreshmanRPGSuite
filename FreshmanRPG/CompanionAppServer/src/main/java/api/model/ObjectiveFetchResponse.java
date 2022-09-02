package api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ship.engr.shipsim.model.ObjectiveRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectiveFetchResponse
{
    private List<ObjectiveInformation> objectives;

    public ObjectiveFetchResponse(List<ObjectiveRecord> records)
    {
        objectives = new ArrayList<>();
        records.forEach(record ->
        {
            objectives.add(new ObjectiveInformation(record.getObjectiveDescription(), record.getQuestID(), record.getObjectiveID()));
        });
    }

    void setObjectives(List<ObjectiveInformation> objectives)
    {
        this.objectives = objectives;
    }

    List<ObjectiveInformation> getObjectives()
    {
        return objectives;
    }

    public String toJSON() throws JsonProcessingException
    {
        String temp = "";
        for (ObjectiveInformation objective : objectives)
        {
            if (objectives.size() - 1 == objectives.indexOf(objective))
            {
                temp = temp + objective.toJSON();
            }
            else
            {
                temp = temp + objective.toJSON() + ",";
            }

        }
        return "{\"objectives\": [" + temp + "]}";
    }

    @Override
    public String toString()
    {
        return "ObjectiveFetchResponse{" +
                "objectives=" + objectives +
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
        ObjectiveFetchResponse that = (ObjectiveFetchResponse) o;
        return Objects.equals(objectives, that.objectives);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(objectives);
    }
}
