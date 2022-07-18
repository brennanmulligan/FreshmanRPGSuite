package api.service;

import api.datasource.ExternalObjectiveManager;
import api.model.ObjectiveRequest;
import api.model.PlayerTokenManager;
import datasource.DatabaseException;
import datasource.ObjectiveStateTableDataGateway;
import datasource.PlayerRowDataGateway;
import datatypes.ObjectiveStateEnum;
import model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObjectiveServiceImpl implements ObjectiveService
{

    private final ObjectiveStateTableDataGateway objectiveStateTableDataGateway;

    public ObjectiveServiceImpl(
            ObjectiveStateTableDataGateway objectiveStateTableDataGateway)
    {
        super();
        this.objectiveStateTableDataGateway = objectiveStateTableDataGateway;
    }

    public ObjectiveServiceImpl()
    {
        objectiveStateTableDataGateway =
                ObjectiveStateTableDataGateway.getSingleton();
    }

    @Override
    public int completeObjective(ObjectiveRequest request) throws DatabaseException
    {
//        PlayerRowDataGateway playerGateway = new PlayerRowDataGateway(request.getPlayerID());
//        Player player = PlayerManager.getSingleton().addPlayer(playerGateway.getPlayerID());
//        PlayerTokenManager ptm = PlayerTokenManager.getInstance();
//        ptm.addPlayer(player);
        int playerID = request.getPlayerID();
        int questID = request.getQuestID();
        int objectiveID = request.getObjectiveID();
        try
        {
            QuestManager.getSingleton().completeObjective(playerID, questID, objectiveID);
            PlayerManager.getSingleton().getPlayerFromID(playerID).persist();
            return 0;
        }
        catch (Exception e)
        {
            return 1;
        }
    }

    @Override
    public List<ObjectiveRecord> fetchAllObjectives(int playerToken)
    {
        PlayerTokenManager ptm = PlayerTokenManager.getInstance();
        int playerID = ptm.getPlayer(playerToken).getPlayerID();
        List<QuestState> openQuests = QuestManager.getSingleton().getQuestList(playerID);
        List<ObjectiveRecord> externalObjectives = new ArrayList<>();
        openQuests.forEach(quest ->
        {
            List<ObjectiveState> objectives = quest.getObjectiveList();
            List<ObjectiveRecord> records = new ArrayList<>();
            try
            {
                records = ExternalObjectiveManager.getSingleton()
                        .getExternalObjectsForQuest(quest.getID());
            }
            catch (DatabaseException e)
            {
                e.printStackTrace();
            }

            List<ObjectiveRecord> finalRecords = records;
            objectives.forEach(objective ->
                    externalObjectives.addAll(finalRecords.stream().filter(
                            r -> r.getObjectiveID() == objective.getID()
                                    && objective.getState() == ObjectiveStateEnum.TRIGGERED
                    ).collect(Collectors.toList())));
        });
        return new ArrayList<>(new HashSet<>(externalObjectives));
    }

}