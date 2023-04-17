package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.*;
import edu.ship.engr.shipsim.datasource.*;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;

import java.util.ArrayList;

public class DataGatheringUtilities
{
    public static ClientPlayerQuestStateDTO createClientPlayerQuestFor(
            QuestStateRecordDTO q) throws DatabaseException
    {
        QuestRowDataGateway qGateway = new QuestRowDataGateway(q.getQuestID());
        ClientPlayerQuestStateDTO cpq =
                new ClientPlayerQuestStateDTO(q.getQuestID(), qGateway.getQuestTitle(),
                        qGateway.getQuestDescription(), q.getState(),
                        qGateway.getExperiencePointsGained(),
                        qGateway.getObjectivesForFulfillment(), q.isNeedingNotification(),
                        qGateway.getEndDate(), qGateway.isEasterEgg());
        ObjectiveStateTableDataGateway asGateway =
                ObjectiveStateTableDataGateway.getSingleton();
        ArrayList<ObjectiveStateRecordDTO> objectivesForPlayer =
                asGateway.getObjectiveStates(q.getPlayerID(), q.getQuestID());
        ObjectiveTableDataGateway aGateway =
                ObjectiveTableDataGateway.getSingleton();
        for (ObjectiveStateRecordDTO adv : objectivesForPlayer)
        {
            ObjectiveRecord objectiveRecord =
                    aGateway.getObjective(q.getQuestID(), adv.getObjectiveID());

            cpq.addObjective(new ClientPlayerObjectiveStateDTO(adv.getObjectiveID(),
                    objectiveRecord.getObjectiveDescription(),
                    objectiveRecord.getExperiencePointsGained(), adv.getState(),
                    adv.isNeedingNotification(), objectiveRecord.isRealLifeObjective(),
                    objectiveRecord.getCompletionCriteria().toString(),
                    QuestStateEnum.AVAILABLE));

        }
        return cpq;
    }

    public static ArrayList<DoubloonPrizeDTO> getDoubloonPrizeList()
    {
        try
        {
            DoubloonPrizesTableDataGateway gateway =
                    DoubloonPrizesTableDataGateway.getSingleton();
            return gateway.getAllDoubloonPrizes();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<FriendDTO> getPlayersFriends(int playerID)
    {
        ArrayList<FriendDTO> result = new ArrayList<>();
        try
        {
            FriendTableDataGateway friendGateway =
                    FriendTableDataGateway.getSingleton();
            result = friendGateway.getAllFriends(playerID);
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<ClientPlayerQuestStateDTO> getPlayersQuest(int playerID)
    {
        ArrayList<ClientPlayerQuestStateDTO> result = new ArrayList<>();
        QuestStateTableDataGateway qsGateway =
                QuestStateTableDataGateway.getSingleton();
        try
        {
            for (QuestStateRecordDTO q : qsGateway.getQuestStates(playerID))
            {
                result.add(createClientPlayerQuestFor(q));
            }
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
