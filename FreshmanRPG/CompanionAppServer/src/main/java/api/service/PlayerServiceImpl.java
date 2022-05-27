package api.service;

import api.model.GameManagerPlayer;
import api.model.GameManagerPlayerManager;
import dataDTO.PlayerDTO;

import datasource.*;
import datatypes.ObjectiveStateEnum;
import datatypes.QuestStateEnum;
import model.ObjectiveRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class PlayerServiceImpl implements PlayerService
{
    private final GameManagerPlayerManager playerManager;

    public PlayerServiceImpl(GameManagerPlayerManager playerManager) {
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

//        CommandAddPlayerInManager command = new CommandAddPlayerInManager(player.getName(), player.getPassword(), player.getCrew(),player.getMajor(),player.getSection());
        try {
            //Add Player
//            PlayerRowDataGateway playerGateway =
//                    new PlayerRowDataGatewayRDS(player.getName(),
//                            player.getPassword(),
//                    player.getCrew(), player.getMajor(), player.getSection())
            manager = GameManagerPlayerManager.getInstance();
            PlayerDTO createdPlayer = manager.addPlayer(player.getName(), player.getPassword(),
                    player.getCrew(), player.getMajor(), player.getSection());
            playerID = createdPlayer.getPlayerID();

            //Add Starter Quest
            QuestStateTableDataGateway questStateTableDataGatewayRDS =
                    QuestStateTableDataGateway.getSingleton();
            questStateTableDataGatewayRDS.updateState(playerID, 100, QuestStateEnum.TRIGGERED, true);

            //Add Starter Objectives
            ObjectiveStateTableDataGateway objectiveStateTableDataGateway =
                    ObjectiveStateTableDataGateway.getSingleton();
            ObjectiveTableDataGateway objectiveTableDataGateway =
                    ObjectiveTableDataGateway.getSingleton();
            ArrayList<ObjectiveRecord> objectiveList = objectiveTableDataGateway.getObjectivesForQuest(100);
            for (ObjectiveRecord objective : objectiveList)
            {
                objectiveStateTableDataGateway.updateState(playerID, 100, objective.getObjectiveID(), ObjectiveStateEnum.TRIGGERED,
                        false);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
//            return 1;
        }

        return 0;
    }
}
