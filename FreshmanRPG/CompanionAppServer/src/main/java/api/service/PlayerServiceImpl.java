package api.service;

import api.model.GameManagerPlayer;
import api.model.GameManagerPlayerManager;
import api.model.GameManagerPlayer;
import dataDTO.PlayerDTO;

import datasource.*;
import datatypes.ObjectiveStateEnum;
import datasource.PlayerRowDataGateway;
import datatypes.QuestStateEnum;
import api.model.GameManagerPlayerManager;
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
                    (QuestStateTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
                            "QuestState");
            questStateTableDataGatewayRDS.udpateState(playerID, 100, QuestStateEnum.TRIGGERED, true);

            //Add Starter Objectives
            ObjectiveStateTableDataGateway objectiveStateTableDataGateway =
                    (ObjectiveStateTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
                            "ObjectiveState");
            ObjectiveTableDataGateway objectiveTableDataGateway =
                    (ObjectiveTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
                            "Objective");
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
