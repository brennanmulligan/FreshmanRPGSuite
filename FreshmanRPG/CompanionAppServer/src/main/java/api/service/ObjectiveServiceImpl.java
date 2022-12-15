package api.service;

import api.datasource.ExternalObjectiveManager;
import api.model.ObjectiveRequest;
import api.model.PlayerTokenManager;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.datasource.ObjectiveStateTableDataGateway;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.model.*;
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
    public int completeObjective(ObjectiveRequest request)
    {
        //        PlayerRowDataGateway playerGateway = new PlayerRowDataGateway(request.getPlayerID());
        //        Player player = PlayerManager.getSingleton().addPlayer(playerGateway.getPlayerID());
        //        PlayerTokenManager ptm = PlayerTokenManager.getInstance();
        //        ptm.addPlayer(player);
        PlayerTokenManager ptm = PlayerTokenManager.getInstance();
        int playerID = ptm.getPlayer(request.getPlayerID()).getPlayerID();
        int questID = request.getQuestID();
        int objectiveID = request.getObjectiveID();
        LoggerManager.getSingleton().getLogger()
                .info("Trying to complete objective " + questID + ":" + objectiveID +
                        ": playerID = " + playerID);
        try
        {
            // PlayerManager.getSingleton().addPlayerSilently(playerID);
            QuestManager.getSingleton().completeObjective(playerID, questID, objectiveID);
            PlayerManager.getSingleton().removePlayerSilently(playerID);
            return 0;
        }
        catch (Exception e)
        {
            LoggerManager.getSingleton().getLogger().info("Exception Completing " +
                    "Objective " + e.getMessage());
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
                                    &&
                                    objective.getState() == ObjectiveStateEnum.TRIGGERED
                    ).collect(Collectors.toList())));
        });
        return new ArrayList<>(new HashSet<>(externalObjectives));
    }

}