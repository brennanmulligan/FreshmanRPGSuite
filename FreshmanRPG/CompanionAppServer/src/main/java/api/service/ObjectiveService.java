package api.service;


import api.model.ObjectiveRequest;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.IllegalObjectiveChangeException;
import edu.ship.engr.shipsim.model.IllegalQuestChangeException;
import edu.ship.engr.shipsim.model.ObjectiveRecord;

import java.util.List;

public interface ObjectiveService
{
    int completeObjective(ObjectiveRequest obj) throws IllegalObjectiveChangeException, DatabaseException, IllegalQuestChangeException;

    List<ObjectiveRecord> fetchAllObjectives(int playerToken) throws DatabaseException;
}
