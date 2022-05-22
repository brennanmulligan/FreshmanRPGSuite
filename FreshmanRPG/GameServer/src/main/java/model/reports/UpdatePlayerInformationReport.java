package model.reports;

import dataDTO.ClientPlayerObjectiveStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import dataDTO.FriendDTO;
import dataDTO.LevelManagerDTO;
import datasource.DatabaseException;
import datasource.FriendTableDataGateway;
import datasource.LevelRecord;
import datasource.TableDataGatewayManager;
import model.*;

import java.util.ArrayList;

/**
 * Report that combines Quest descriptions and Quest states
 *
 * @author Ryan, LaVonne, Olivia
 */
public class UpdatePlayerInformationReport implements QualifiedObservableReport
{

    private final ArrayList<ClientPlayerQuestStateDTO> clientPlayerQuestList =
            new ArrayList<>();
    private final ArrayList<FriendDTO> friendList;
    private final int experiencePoints;
    private final int doubloons;
    private final LevelRecord level;
    private final int playerID;

    /**
     * Combine the player's quest state and quest descriptions Sets local
     * experience points equal to player's experience points
     *
     * @param player the player
     * @throws DatabaseException shouldn't
     */
    public UpdatePlayerInformationReport(Player player) throws DatabaseException
    {
        combineQuest(QuestManager.getSingleton().getQuestList(player.getPlayerID()));
        this.friendList = getFriendList(player.getPlayerID());
        this.experiencePoints = player.getExperiencePoints();
        this.doubloons = player.getDoubloons();
        this.level = LevelManagerDTO.getSingleton().getLevelForPoints(experiencePoints);
        this.playerID = player.getPlayerID();
    }

    /**
     * Return ArrayList of Client Player Quests
     *
     * @return clientPlayerQuestList
     */
    public ArrayList<ClientPlayerQuestStateDTO> getClientPlayerQuestList()
    {
        return clientPlayerQuestList;
    }

    /**
     * Return int of Player's doubloons
     *
     * @return doubloons
     */
    public int getDoubloons()
    {
        return doubloons;
    }

    /**
     * Return int of Player's experience points
     *
     * @return experiencePoints
     */
    public int getExperiencePts()
    {
        return experiencePoints;
    }

    /**
     * @return this player's friend list
     */
    public ArrayList<FriendDTO> getFriendsList()
    {
        return friendList;
    }

    /**
     * Returns the Player's level
     *
     * @return level
     */
    public LevelRecord getLevel()
    {
        return level;
    }

    /**
     * @return this player's playerID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    private ArrayList<ClientPlayerObjectiveStateDTO> combineObjective(QuestRecord quest,
                                                                      QuestState qs)
    {
        ArrayList<ClientPlayerObjectiveStateDTO> ca = new ArrayList<>();
        for (ObjectiveState a : qs.getObjectiveList())
        {
            int objectiveID = a.getID();
            ObjectiveRecord objective = quest.getObjectiveID(objectiveID);
            ca.add(new ClientPlayerObjectiveStateDTO(a.getID(),
                    objective.getObjectiveDescription(),
                    objective.getExperiencePointsGained(), a.getState(),
                    a.isNeedingNotification(),
                    objective.isRealLifeObjective(),
                    objective.getCompletionCriteria().toString(), qs.getStateValue()));
        }

        return ca;
    }

    /**
     * Combines a quest description and state and adds them to
     * clientPlayerQuestList
     *
     * @throws DatabaseException if we can't connect to or read from the db
     */
    private void combineQuest(ArrayList<QuestState> questStateList)
            throws DatabaseException
    {
        if (questStateList != null)
        {
            for (QuestState qs : questStateList)
            {
                // if (qs.getStateValue() != QuestStateEnum.AVAILABLE
                // && qs.getStateValue() != QuestStateEnum.HIDDEN)
                // {
                int questID = qs.getID();
                QuestRecord quest = QuestManager.getSingleton().getQuest(questID);

                ClientPlayerQuestStateDTO clientQuest =
                        new ClientPlayerQuestStateDTO(quest.getQuestID(),
                                quest.getTitle(),
                                quest.getDescription(), qs.getStateValue(),
                                quest.getExperiencePointsGained(),
                                quest.getObjectivesForFulfillment(),
                                qs.isNeedingNotification(), quest.getEndDate());
                clientQuest.setObjectives(combineObjective(quest, qs));

                clientPlayerQuestList.add(clientQuest);
            }
            // }
        }
    }

    private ArrayList<FriendDTO> getFriendList(int playerID) throws DatabaseException
    {
        FriendTableDataGateway friendGateway =
                (FriendTableDataGateway) TableDataGatewayManager.getSingleton()
                        .getTableGateway(
                                "Friend");
        return friendGateway.getAllFriends(playerID);

    }
}
