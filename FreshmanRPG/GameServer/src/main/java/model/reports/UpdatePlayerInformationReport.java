package model.reports;

import java.util.ArrayList;

import dataDTO.ClientPlayerAdventureStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import dataDTO.FriendDTO;
import dataDTO.LevelManagerDTO;
import datasource.DatabaseException;
import datasource.FriendTableDataGatewayMock;
import datasource.FriendTableDataGatewayRDS;
import datasource.LevelRecord;
import model.*;

/**
 * Report that combines Quest descriptions and Quest states
 *
 * @author Ryan, LaVonne, Olivia
 *
 */
public class UpdatePlayerInformationReport implements QualifiedObservableReport
{

	private ArrayList<ClientPlayerQuestStateDTO> clientPlayerQuestList = new ArrayList<>();
	private ArrayList<FriendDTO> friendList;
	private int experiencePoints;
	private int knowledgePoints;
	private LevelRecord level;
	private int playerID;

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
		this.knowledgePoints = player.getKnowledgePoints();
		this.level = LevelManagerDTO.getSingleton().getLevelForPoints(experiencePoints);
		this.playerID = player.getPlayerID();
	}

	private ArrayList<FriendDTO> getFriendList(int playerID) throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return FriendTableDataGatewayMock.getSingleton().getAllFriends(playerID);
		}
		else
		{
			return FriendTableDataGatewayRDS.getInstance().getAllFriends(playerID);
		}
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

				ClientPlayerQuestStateDTO clientQuest = new ClientPlayerQuestStateDTO(quest.getQuestID(), quest.getTitle(),
						quest.getDescription(), qs.getStateValue(), quest.getExperiencePointsGained(),
						quest.getAdventuresForFulfillment(), qs.isNeedingNotification(), quest.getEndDate());
				clientQuest.setAdventures(combineAdventure(quest, qs));

				clientPlayerQuestList.add(clientQuest);
			}
			// }
		}
	}

	private ArrayList<ClientPlayerAdventureStateDTO> combineAdventure(QuestRecord quest, QuestState qs)
	{
		ArrayList<ClientPlayerAdventureStateDTO> ca = new ArrayList<>();
		for (AdventureState a : qs.getAdventureList())
		{
			int adventureID = a.getID();
			AdventureRecord adventure = quest.getAdventureD(adventureID);
			ca.add(new ClientPlayerAdventureStateDTO(a.getID(), adventure.getAdventureDescription(),
					adventure.getExperiencePointsGained(), a.getState(), a.isNeedingNotification(),
					adventure.isRealLifeAdventure(), adventure.getCompletionCriteria().toString(), qs.getStateValue()));
		}

		return ca;
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
	 * Return int of Player's experience points
	 *
	 * @return experiencePoints
	 */
	public int getExperiencePts()
	{
		return experiencePoints;
	}

	/**
	 * Return int of Player's knowledge points 
	 *
	 * @return knowledgePoints
	 */
	public int getKnowledgePts()
	{
		return knowledgePoints;
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

	/**
	 * @return this player's friend list
	 */
	public ArrayList<FriendDTO> getFriendsList()
	{
		return friendList;
	}
}
