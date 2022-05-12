package model;

import java.util.ArrayList;

import dataDTO.ObjectiveStateRecordDTO;
import dataDTO.PlayerDTO;
import dataDTO.QuestStateRecordDTO;
import dataDTO.VanityDTO;
import datasource.*;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

/**
 * Manages retrieving and persisting all of the data associated with Players
 * (basically, connector between the Player class and the gateways in the data
 * source)
 *
 * @author Merlin, Aaron W., Jake H.
 *
 */
public class PlayerMapper
{
	private final PlayerRowDataGateway playerGateway;
	private final PlayerConnectionRowDataGateway playerConnectionGateway;
	private final QuestStateTableDataGateway questStateGateway;
	private final ObjectiveStateTableDataGateway objectiveStateGateway;
	private final ObjectiveTableDataGateway objectiveTableDataGateway;
	private final VisitedMapsGateway visitedMapsGateway;
	private final VanityInventoryTableDataGatewayInterface vanityTableDataGateway;

	//For testing in Mock Player Data Gateway
	private static PlayerDTO editedPlayerInfo;


	/**
	 * The player we are connecting to the gateways
	 */
	protected Player player;

	/**
	 * Finder constructor
	 *
	 * @param playerID the player's unique ID
	 * @throws DatabaseException if we can't find the given player
	 */
	public PlayerMapper(int playerID) throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			this.playerGateway = new PlayerRowDataGatewayMock(playerID);
			this.questStateGateway = QuestStateTableDataGatewayMock.getSingleton();
			this.objectiveStateGateway = ObjectiveStateTableDataGatewayMock.getSingleton();
			this.playerConnectionGateway = new PlayerConnectionRowDataGatewayMock(playerID);
			this.objectiveTableDataGateway = ObjectiveTableDataGatewayMock.getSingleton();
			this.visitedMapsGateway = new VisitedMapsGatewayMock(playerID);
			this.vanityTableDataGateway = VanityInventoryTableDataGatewayMock.getSingleton();
		}
		else
		{
			this.playerGateway = new PlayerRowDataGatewayRDS(playerID);
			this.questStateGateway = QuestStateTableDataGatewayRDS.getSingleton();
			this.objectiveStateGateway = ObjectiveStateTableDataGatewayRDS.getSingleton();
			this.playerConnectionGateway = new PlayerConnectionRowDataGatewayRDS(playerID);
			this.objectiveTableDataGateway = ObjectiveTableDataGatewayRDS.getSingleton();
			this.visitedMapsGateway = new VisitedMapsGatewayRDS(playerID);
			this.vanityTableDataGateway = VanityInventoryTableDataGatewayRDS.getSingleton();
		}
		this.player = createPlayerObject(playerID);
		player.setPlayerLogin(new PlayerLogin(playerID));
		player.setAppearanceType(playerGateway.getAppearanceType());
		player.setPlayerPositionWithoutNotifying(playerConnectionGateway.getPosition());
		player.setDoubloons(playerGateway.getQuizScore());
		player.setCrew(playerGateway.getCrew());
		player.setMajor(playerGateway.getMajor());
		player.setSection(playerGateway.getSection());
		player.setBuffPool(playerGateway.getBuffPool());
		player.setExperiencePoints(playerGateway.getExperiencePoints());
		player.setDataMapper(this);
		player.setMapName(playerConnectionGateway.getMapName());
		player.setPlayerOnline(playerGateway.getOnline());
		player.setPlayerVisitedMaps(visitedMapsGateway.getMaps());
		player.setVanityItems(vanityTableDataGateway.getWearing(playerID));
		player.setOwnedItems((vanityTableDataGateway.getAllOwnedItems(playerID)));

		loadQuestStates();
	}

	/**
	 * Creation constructor.
	 *
	 * @param position - position of the player
	 * @param appearanceType - the appearance type of the player
	 * @param doubloons - the doubloons of the player
	 * @param experiencePoints - the players experience points
	 * @param crew - the crew the player is apart
	 * @param major - the players major
	 * @param section - the section the player is in
	 * @param name - the name of the player
	 * @param password - the password for the player
	 *
	 * @throws DatabaseException - shouldn't
	 */
	protected PlayerMapper(Position position, String appearanceType, int doubloons, int experiencePoints, Crew crew,
						   Major major, int section, String name, String password) throws DatabaseException
	{
		//TODO: The defaults need to be configured properly
		int pin = 1111;
		String mapName = "sortingRoom.tmx";
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			this.playerGateway = new PlayerRowDataGatewayMock(appearanceType, doubloons, experiencePoints, crew,
					major, section, 0, false);
			this.questStateGateway = QuestStateTableDataGatewayMock.getSingleton();
			this.objectiveStateGateway = ObjectiveStateTableDataGatewayMock.getSingleton();
			this.objectiveTableDataGateway = ObjectiveTableDataGatewayMock.getSingleton();
			this.playerConnectionGateway = new PlayerConnectionRowDataGatewayMock(this.playerGateway.getPlayerID(), pin, mapName, position);
			new PlayerLoginRowDataGatewayMock(this.playerGateway.getPlayerID(), name, password);
			this.visitedMapsGateway = new VisitedMapsGatewayMock(this.playerGateway.getPlayerID());
			this.vanityTableDataGateway = VanityInventoryTableDataGatewayMock.getSingleton();
		}
		else
		{
			this.playerGateway = new PlayerRowDataGatewayRDS(appearanceType, doubloons, experiencePoints, crew,
					major, section, 0, false);
			this.questStateGateway = QuestStateTableDataGatewayRDS.getSingleton();
			this.objectiveStateGateway = ObjectiveStateTableDataGatewayRDS.getSingleton();
			this.objectiveTableDataGateway = ObjectiveTableDataGatewayRDS.getSingleton();
			this.playerConnectionGateway = new PlayerConnectionRowDataGatewayRDS(this.playerGateway.getPlayerID(), pin, mapName, position);
			new PlayerLoginRowDataGatewayRDS(this.playerGateway.getPlayerID(), name, password);
			this.visitedMapsGateway = new VisitedMapsGatewayRDS(this.playerGateway.getPlayerID());
			this.vanityTableDataGateway = VanityInventoryTableDataGatewayRDS.getSingleton();
		}
		this.player = createPlayerObject(this.playerGateway.getPlayerID());
		player.setPlayerLogin(new PlayerLogin(this.playerGateway.getPlayerID()));
		player.setAppearanceType(playerGateway.getAppearanceType());
		player.setPlayerPositionWithoutNotifying(playerConnectionGateway.getPosition());
		player.setDoubloons(playerGateway.getQuizScore());
		player.setCrew(playerGateway.getCrew());
		player.setMajor(playerGateway.getMajor());
		player.setSection(playerGateway.getSection());
		player.setExperiencePoints(playerGateway.getExperiencePoints());
		player.setDataMapper(this);
		player.setMapName(playerConnectionGateway.getMapName());
		player.setPlayerOnline(playerGateway.getOnline());
		player.setPlayerVisitedMaps(visitedMapsGateway.getMaps());
		player.setVanityItems(vanityTableDataGateway.getWearing(this.playerGateway.getPlayerID()));
		player.setOwnedItems((vanityTableDataGateway.getAllOwnedItems(this.playerGateway.getPlayerID())));
		loadQuestStates();

	}

	/**
	 * Returns a list of all the player information.
	 *
	 * @return list of all players
	 * @throws DatabaseException shouldn't
	 */
	protected static ArrayList<PlayerDTO> getAllPlayers() throws DatabaseException
	{

		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			PlayerTableDataGatewayMock gateway = (PlayerTableDataGatewayMock) PlayerTableDataGatewayMock.getSingleton();
			if (editedPlayerInfo != null)
			{
				gateway.saveEditedPlayer(
						editedPlayerInfo.getPlayerID(),
						editedPlayerInfo.getPlayerName(),
						editedPlayerInfo.getPlayerPassword(),
						editedPlayerInfo.getAppearanceType(),
						editedPlayerInfo.getDoubloons(),
						editedPlayerInfo.getPosition(),
						editedPlayerInfo.getMapName(),
						editedPlayerInfo.getExperiencePoints(),
						editedPlayerInfo.getCrew(),
						editedPlayerInfo.getMajor(),
						editedPlayerInfo.getSection(),
						editedPlayerInfo.getVisitedMaps(),
						editedPlayerInfo.getVanityItems());

			}
			return gateway.retrieveAllPlayers();

		}
		else
		{
			PlayerTableDataGateway gateway = PlayerTableDataGatewayRDS.getSingleton();
			return gateway.retrieveAllPlayers();
		}

	}

	/**
	 * For PlayerTableDataGatewayMock
	 * Keeps retrieve player from overwriting edits
	 * Set a player being edited
	 * @param playerInfo the information of the player
	 **/
	protected void setPlayerToEdit(PlayerDTO playerInfo)
	{
		editedPlayerInfo = playerInfo;
	}


	/**
	 * Return the player DTO
	 *
	 * @return - PlayerInfo DTO
	 */
	protected PlayerDTO getPlayerInfo()
	{
		return player.getPlayerInfo();
	}

	protected void loadQuestStates() throws DatabaseException
	{
		ArrayList<QuestStateRecordDTO> questStateRecords = questStateGateway.getQuestStates(player.getPlayerID());
		for (QuestStateRecordDTO qsRec : questStateRecords)
		{
			QuestState questState = new QuestState(player.getPlayerID(), qsRec.getQuestID(), qsRec.getState(),
					qsRec.isNeedingNotification());
			ArrayList<ObjectiveStateRecordDTO> objectiveStateRecords = objectiveStateGateway
					.getObjectiveStates(player.getPlayerID(), qsRec.getQuestID());
			ArrayList<ObjectiveState> objectiveStates = new ArrayList<>();
			for (ObjectiveStateRecordDTO asRec : objectiveStateRecords)
			{
				objectiveStates.add(
						new ObjectiveState(asRec.getObjectiveID(), asRec.getState(), asRec.isNeedingNotification()));
			}
			questState.addObjectives(objectiveStates);
			QuestManager.getSingleton().addQuestState(player.getPlayerID(), questState);
		}

	}

	/**
	 * @param playerID The player we want to create
	 * @return a new object of the type this mapper is managing
	 */
	protected Player createPlayerObject(int playerID)
	{
		return new Player(playerID);
	}

	/**
	 * Get the player that this Mapper is responsible for
	 *
	 * @return the player this mapper manages
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Sets the players appearance.
	 * @param appearanceType - The appearance to change to.
	 */
	protected void setPlayerAppearanceType(String appearanceType)
	{
		player.setAppearanceType(appearanceType);
		playerGateway.setAppearanceType(appearanceType);

	}

	/**
	 * Persist the current state of the player into the data source
	 *
	 * @throws DatabaseException if we can't complete the write
	 * @throws IllegalQuestChangeException shouldn't
	 */
	protected void persist() throws DatabaseException, IllegalQuestChangeException
	{

		playerGateway.setAppearanceType(player.getAppearanceType());
		playerGateway.setQuizScore(player.getQuizScore());
		playerGateway.setExperiencePoints(player.getExperiencePoints());
		playerGateway.setCrew(player.getCrew());
		playerGateway.setMajor(player.getMajor());
		playerGateway.setSection(player.getSection());
		playerGateway.setBuffPool(player.getBuffPool());
		playerGateway.setOnline(player.isPlayerOnline());
		playerGateway.persist();
		vanityTableDataGateway.updateInventory(player.getPlayerID(), player.getAllOwnedItems());
		vanityTableDataGateway.updateCurrentlyWearing(player.getPlayerID(), player.getVanityItems());

		//Ensures that we're only adding new maps to the player
		String mapTitle = player.getLastVisitedMap();
		VisitedMapsGateway gateway = getVisitedMapsGateway(player.getPlayerID());
		if (!gateway.getMaps().contains(mapTitle))
		{
			if (OptionsManager.getSingleton().isUsingMockDataSource())
			{
				new VisitedMapsGatewayMock(player.getPlayerID(), mapTitle);
			}
			else
			{
				new VisitedMapsGatewayRDS(player.getPlayerID(), mapTitle);
			}
		}

		playerConnectionGateway.storeMapName(player.getMapName());
		playerConnectionGateway.storePosition(player.getPlayerPosition());

		ArrayList<QuestState> questList = QuestManager.getSingleton().getQuestList(player.getPlayerID());
		if (questList != null)
		{
			for (QuestState quest : questList)
			{
				questStateGateway.udpateState(player.getPlayerID(), quest.getID(), quest.getStateValue(),
						quest.isNeedingNotification());
				for (ObjectiveState a : quest.getObjectiveList())
				{
					objectiveStateGateway.updateState(player.getPlayerID(), quest.getID(), a.getID(), a.getState(),
							a.isNeedingNotification());
				}
			}
		}
	}

	VisitedMapsGateway getVisitedMapsGateway(int playerID) throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return new VisitedMapsGatewayMock(player.getPlayerID());
		}
		else
		{
			return new VisitedMapsGatewayRDS(player.getPlayerID());
		}
	}
	/**
	 * Removes the player from data areas other than the PlayerManager
	 */
	protected void removeQuestStates()
	{
		QuestManager.getSingleton().removeQuestStatesForPlayer(player.getPlayerID());
	}

	/**
	 * Remove the player and all player data.
	 *
	 * @throws DatabaseException - shouldn't
	 */
	protected void remove() throws DatabaseException
	{
		PlayerLoginRowDataGateway loginGateway;

		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			loginGateway = new PlayerLoginRowDataGatewayMock(player.getPlayerID());
		}
		else
		{
			loginGateway = new PlayerLoginRowDataGatewayRDS(player.getPlayerID());
		}
		this.removeQuestStates();
		loginGateway.deleteRow();
		playerGateway.delete();
		playerConnectionGateway.deleteRow();

	}

	/**
	 * Returns a list of all incomplete objectives corresponding to a particular player
	 * @return an ArrayList of incomplete objectives
	 * @throws DatabaseException - shouldn't
	 */
	protected ArrayList<ObjectiveRecord> getIncompleteObjectives() throws DatabaseException
	{
		ArrayList<ObjectiveRecord> recordList = new ArrayList<>();

		ArrayList<ObjectiveStateRecordDTO> incompleteObjectives;
		incompleteObjectives = this.objectiveStateGateway.getUncompletedObjectivesForPlayer(this.player.getPlayerID());

		for (ObjectiveStateRecordDTO objective : incompleteObjectives)
		{
			int questID = objective.getQuestID();
			int objectiveID = objective.getObjectiveID();
			ObjectiveRecord record = this.objectiveTableDataGateway.getObjective(questID, objectiveID);
			recordList.add(record);
		}
		return recordList;
	}

	/**
	 * gets all maps a player has visited
	 * @throws DatabaseException if we can't talk to the db
	 */
	protected void addMapToPlayer(String mapTitle) throws DatabaseException
	{
		player.addNewVisitedMap(mapTitle);
	}
}
