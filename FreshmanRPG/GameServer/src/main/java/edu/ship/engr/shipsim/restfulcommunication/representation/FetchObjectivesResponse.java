package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;

@Getter
public final class FetchObjectivesResponse
{
    private final List<ObjectiveResponse> objectives = Lists.newArrayList();

    public void addObjective(int questID, int objectiveID, String description)
    {
        objectives.add(new ObjectiveResponse(questID, objectiveID, description));
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }

    @Data
    private static class ObjectiveResponse
    {
        private final int questID;
        private final int objectiveID;
        private final String description;
    }
}
