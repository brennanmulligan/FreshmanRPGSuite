package api.service;

import api.model.GameManagerPlayer;
import api.model.GameManagerPlayerManager;
import edu.ship.engr.shipsim.datatypes.QuestsForProduction;
import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ObjectiveStateTableDataGateway;
import edu.ship.engr.shipsim.datasource.ObjectiveTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestStateTableDataGateway;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.ObjectiveRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class PlayerServiceImpl implements PlayerService
{
    private static final QuestsForProduction[] questsToTrigger =
            {QuestsForProduction.ONRAMPING_QUEST,
                    QuestsForProduction.SCAVENGER_HUNT, QuestsForProduction.EVENTS};
    private final GameManagerPlayerManager playerManager;

    public PlayerServiceImpl(GameManagerPlayerManager playerManager)
    {
        this.playerManager = playerManager;
    }

    public PlayerServiceImpl() throws DatabaseException
    {
        this.playerManager = GameManagerPlayerManager.getInstance();
    }

    @Override
    public int addPlayer(GameManagerPlayer player)
    {
        GameManagerPlayerManager manager;
        int playerID;

        try
        {
            manager = GameManagerPlayerManager.getInstance();
            PlayerDTO createdPlayer = manager.addPlayer(player.getName(), player.getPassword(),
                    player.getCrew(), player.getMajor(), player.getSection());
            playerID = createdPlayer.getPlayerID();
            triggerInitialQuests(playerID);
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
//            return 1;
        }

        return 0;
    }

    private void triggerInitialQuests(int playerID) throws DatabaseException
    {
        QuestStateTableDataGateway questStateTableDataGatewayRDS =
                QuestStateTableDataGateway.getSingleton();
        ObjectiveStateTableDataGateway objectiveStateTableDataGateway =
                ObjectiveStateTableDataGateway.getSingleton();
        ObjectiveTableDataGateway objectiveTableDataGateway =
                ObjectiveTableDataGateway.getSingleton();

        for (QuestsForProduction q : questsToTrigger)
        {
            questStateTableDataGatewayRDS.updateState(playerID, q.getQuestID(),
                    QuestStateEnum.TRIGGERED, true);

            //Add relevant Objectives
            ArrayList<ObjectiveRecord> objectiveList =
                    objectiveTableDataGateway.getObjectivesForQuest(q.getQuestID());
            for (ObjectiveRecord objective : objectiveList)
            {
                objectiveStateTableDataGateway.updateState(playerID, q.getQuestID(),
                        objective.getObjectiveID(), ObjectiveStateEnum.TRIGGERED,
                        false);
            }
        }
    }
}
