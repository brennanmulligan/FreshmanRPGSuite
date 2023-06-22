package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ObjectiveStateRecordDTO;
import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.dataDTO.QuestStateRecordDTO;
import edu.ship.engr.shipsim.datasource.*;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;

import java.util.ArrayList;

/**
 * Manages retrieving and persisting all of the data associated with Players
 * (basically, connector between the Player class and the gateways in the data
 * source)
 *
 * @author Merlin, Aaron W., Jake H.
 */
public class PlayerMapper
{
    private final PlayerRowDataGateway playerGateway;
    private final PlayerConnectionRowDataGateway playerConnectionGateway;
    private final QuestStateTableDataGateway questStateGateway;
    private final ObjectiveStateTableDataGateway objectiveStateGateway;
    private final ObjectiveTableDataGateway objectiveTableDataGateway;
    private final VisitedMapsGateway visitedMapsGateway;
    private final VanityInventoryTableDataGateway vanityTableDataGateway;
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
        objectiveStateGateway = ObjectiveStateTableDataGateway.getSingleton();
        objectiveTableDataGateway = ObjectiveTableDataGateway.getSingleton();
        questStateGateway = QuestStateTableDataGateway.getSingleton();

        this.playerGateway = new PlayerRowDataGateway(playerID);

        this.visitedMapsGateway = new VisitedMapsGateway(playerID);
        this.vanityTableDataGateway = VanityInventoryTableDataGateway.getSingleton();

        this.playerConnectionGateway = new PlayerConnectionRowDataGateway(playerID);
        this.player = createPlayerObject(playerID);
        player.setPlayerLogin(new PlayerLogin(playerID));
        player.setAppearanceType(playerGateway.getAppearanceType());
        player.setPositionWithoutNotifying(playerConnectionGateway.getPosition());
        player.setDoubloons(playerGateway.getQuizScore());
        player.setCrew(playerGateway.getCrew());
        player.setMajor(playerGateway.getMajor());
        player.setSection(playerGateway.getSection());
        player.setBuffPool(playerGateway.getBuffPool());
        player.setExperiencePoints(playerGateway.getExperiencePoints());
        player.setDataMapper(this);
        player.setMapName(playerConnectionGateway.getMapName());
        player.setPlayerOnline(playerGateway.isOnline());
        player.setPlayerVisitedMaps(visitedMapsGateway.getMaps());
        player.setVanityItems(vanityTableDataGateway.getWearing(playerID));
        player.setOwnedItems((vanityTableDataGateway.getAllOwnedItems(playerID)));

        loadQuestStates();
    }

    /**
     * Creation constructor.
     *
     * @param position         - position of the player
     * @param appearanceType   - the appearance type of the player
     * @param doubloons        - the doubloons of the player
     * @param experiencePoints - the players experience points
     * @param crew             - the crew the player is apart
     * @param major            - the players major
     * @param section          - the section the player is in
     * @param playerName             - the playerName of the player
     * @param password         - the password for the player
     * @throws DatabaseException - shouldn't
     */
    public PlayerMapper(Position position, String appearanceType, int doubloons,
                        int experiencePoints, Crew crew, Major major, int section,
                        String playerName, String password)
            throws DatabaseException
    {
        int pin = 1111;
        String mapName = "sortingRoom.tmx";
        objectiveStateGateway = ObjectiveStateTableDataGateway.getSingleton();
        objectiveTableDataGateway = ObjectiveTableDataGateway.getSingleton();
        questStateGateway = QuestStateTableDataGateway.getSingleton();
        playerGateway =
                new PlayerRowDataGateway(appearanceType, doubloons, experiencePoints,
                        crew, major, section, 0, false);
        visitedMapsGateway = new VisitedMapsGateway(this.playerGateway.getPlayerID());
        vanityTableDataGateway = VanityInventoryTableDataGateway.getSingleton();

        playerConnectionGateway =
                new PlayerConnectionRowDataGateway(this.playerGateway.getPlayerID(), pin,
                        mapName, position);
        new PlayerLoginRowDataGateway(this.playerGateway.getPlayerID(), playerName, password);
        this.player = createPlayerObject(this.playerGateway.getPlayerID());
        player.setPlayerLogin(new PlayerLogin(this.playerGateway.getPlayerID()));
        player.setAppearanceType(playerGateway.getAppearanceType());
        player.setPositionWithoutNotifying(playerConnectionGateway.getPosition());
        player.setDoubloons(playerGateway.getQuizScore());
        player.setCrew(playerGateway.getCrew());
        player.setMajor(playerGateway.getMajor());
        player.setSection(playerGateway.getSection());
        player.setExperiencePoints(playerGateway.getExperiencePoints());
        player.setDataMapper(this);
        player.setMapName(playerConnectionGateway.getMapName());
        player.setPlayerOnline(playerGateway.isOnline());
        player.setPlayerVisitedMaps(visitedMapsGateway.getMaps());
        player.setVanityItems(
                vanityTableDataGateway.getWearing(this.playerGateway.getPlayerID()));
        player.setOwnedItems((vanityTableDataGateway.getAllOwnedItems(
                this.playerGateway.getPlayerID())));
        loadQuestStates();

    }

    /**
     * Returns a list of all the player information.
     *
     * @return list of all players
     */
    public static ArrayList<PlayerDTO> getAllPlayers()
    {
        PlayerTableDataGateway gateway = PlayerTableDataGateway.getSingleton();

        try
        {
            return gateway.getAllPlayers();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a list of all incomplete objectives corresponding to a particular player
     *
     * @return an ArrayList of incomplete objectives
     * @throws DatabaseException - shouldn't
     */
    public ArrayList<ObjectiveRecord> getIncompleteObjectives() throws DatabaseException
    {
        ArrayList<ObjectiveRecord> recordList = new ArrayList<>();

        ArrayList<ObjectiveStateRecordDTO> incompleteObjectives;
        incompleteObjectives =
                this.objectiveStateGateway.getUncompletedObjectivesForPlayer(
                        this.player.getPlayerID());

        for (ObjectiveStateRecordDTO objective : incompleteObjectives)
        {
            int questID = objective.getQuestID();
            int objectiveID = objective.getObjectiveID();
            ObjectiveRecord record =
                    this.objectiveTableDataGateway.getObjective(questID, objectiveID);
            recordList.add(record);
        }
        return recordList;
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
     * Return the player DTO
     *
     * @return - PlayerInfo DTO
     */
    public PlayerDTO getPlayerDTO()
    {
        return player.getPlayerDTO();
    }

    /**
     * Remove the player and all player data.
     *
     * @throws DatabaseException - shouldn't
     */
    public void remove() throws DatabaseException
    {
        PlayerLoginRowDataGateway loginGateway =
                new PlayerLoginRowDataGateway(player.getPlayerID());

        this.removeQuestStates();
        loginGateway.deleteRow();
        playerGateway.delete();
        playerConnectionGateway.deleteRow();

    }

    protected void loadQuestStates() throws DatabaseException
    {
        // if the player did not log out properly, their quests aren't removed.
        // reset them here
        removeQuestStates();

        ArrayList<QuestStateRecordDTO> questStateRecordDTOs =
                questStateGateway.getQuestStates(player.getPlayerID());
        for (QuestStateRecordDTO qsRec : questStateRecordDTOs)
        {
            QuestState questState =
                    new QuestState(player.getPlayerID(), qsRec.getQuestID(),
                            qsRec.getState(), qsRec.isNeedingNotification());
            ArrayList<ObjectiveStateRecordDTO> objectiveStateRecords =
                    objectiveStateGateway.getObjectiveStates(player.getPlayerID(),
                            qsRec.getQuestID());
            ArrayList<ObjectiveState> objectiveStates = new ArrayList<>();
            for (ObjectiveStateRecordDTO asRec : objectiveStateRecords)
            {
                objectiveStates.add(
                        new ObjectiveState(asRec.getObjectiveID(), asRec.getState(),
                                asRec.isNeedingNotification()));
            }
            questState.addObjectives(objectiveStates);

            QuestManager.getSingleton().addQuestState(player.getPlayerID(), questState);
        }

    }

    /**
     * @param playerID The player we want to create
     * @return a new object of the type this mapper is managing
     */
    protected Player createPlayerObject(int playerID) throws DatabaseException
    {
        return new Player(playerID);
    }

    /**
     * Sets the player's appearance.
     *
     * @param appearanceType - The appearance to change to.
     */
    protected void setAppearanceType(String appearanceType)
    {
        player.setAppearanceType(appearanceType);
        playerGateway.setAppearanceType(appearanceType);

    }

    /**
     * Persist the current state of the player into the data source
     *
     * @throws DatabaseException if we can't complete the write
     */
    protected void persist() throws DatabaseException
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
        vanityTableDataGateway.updateInventory(player.getPlayerID(),
                player.getAllOwnedItems());
        vanityTableDataGateway.updateCurrentlyWearing(player.getPlayerID(),
                player.getVanityItems());

        //Ensures that we're only adding new maps to the player
        String mapTitle = player.getLastVisitedMap();
        if (mapTitle != null)
        {
            VisitedMapsGateway gateway = getVisitedMapsGateway();
            if (!gateway.getMaps().contains(mapTitle))
            {
                // insert the most recent map into the list of visited maps
                new VisitedMapsGateway(player.getPlayerID(), mapTitle);
            }
        }

        playerConnectionGateway.storeMapName(player.getMapName());
        playerConnectionGateway.storePosition(player.getPosition());

        ArrayList<QuestState> questList =
                QuestManager.getSingleton().getQuestList(player.getPlayerID());
        if (questList != null)
        {
            //noinspection unchecked
            questList = (ArrayList<QuestState>) questList.clone();
            for (QuestState questState : questList)
            {
                questStateGateway.updateState(player.getPlayerID(), questState.getID(),
                        questState.getStateValue(), questState.isNeedingNotification());
                for (ObjectiveState a : questState.getObjectiveList())
                {
                    objectiveStateGateway.updateState(player.getPlayerID(), questState.getID(),
                            a.getID(), a.getState(), a.isNeedingNotification());
                }
            }
        }
    }

    VisitedMapsGateway getVisitedMapsGateway() throws DatabaseException
    {
        return new VisitedMapsGateway(player.getPlayerID());
    }

    /**
     * Removes the player from data areas other than the PlayerManager
     */
    protected void removeQuestStates()
    {
        QuestManager.getSingleton().removeQuestStates(player.getPlayerID());
    }

    /**
     * gets all maps a player has visited
     *
     * @throws DatabaseException if we can't talk to the db
     */
    protected void addNewVisitedMap(String mapTitle) throws DatabaseException
    {
        player.addNewVisitedMap(mapTitle);
    }
}
