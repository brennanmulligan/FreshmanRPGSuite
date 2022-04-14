package api.service;


import api.model.ObjectiveRequest;
import datasource.DatabaseException;
import model.IllegalObjectiveChangeException;
import model.IllegalQuestChangeException;
import model.ObjectiveRecord;

import java.util.List;

public interface ObjectiveService {
    int completeObjective(ObjectiveRequest obj) throws IllegalObjectiveChangeException, DatabaseException, IllegalQuestChangeException;
    List<ObjectiveRecord> fetchAllObjectives(int playerToken) throws DatabaseException;
}
