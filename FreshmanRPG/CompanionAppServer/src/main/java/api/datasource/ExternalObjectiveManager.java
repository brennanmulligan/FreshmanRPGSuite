package api.datasource;

import datasource.DatabaseException;
import datasource.ObjectiveTableDataGateway;
import model.ObjectiveRecord;

import java.util.ArrayList;
import java.util.List;

public class ExternalObjectiveManager
{

    private static ExternalObjectiveManager instance;
    private final ObjectiveTableDataGateway gateway;

    private ExternalObjectiveManager()
    {
        gateway =
                ObjectiveTableDataGateway.getSingleton();
    }

    public static ExternalObjectiveManager getSingleton()
    {
        if (instance == null)
        {
            instance = new ExternalObjectiveManager();
        }
        return instance;
    }

    public List<ObjectiveRecord> getExternalObjectsForQuest(int questID)
            throws DatabaseException
    {
        List<ObjectiveRecord> questObjectives = gateway.getObjectivesForQuest(questID);
        List<ObjectiveRecord> externalObjectives = new ArrayList<>();
        for (ObjectiveRecord objective : questObjectives)
        {
            if (objective.isRealLifeObjective())
            {
                externalObjectives.add(objective);
            }
        }
        return externalObjectives;
    }

}


