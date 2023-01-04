package api.model;

import api.model.reports.AllPlayersReport;
import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.FindPlayerIDFromPlayerName;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerMapper;
import edu.ship.engr.shipsim.model.ReportObserverConnector;

import java.util.ArrayList;

/**
 * Manage a list of players for administration.
 */
public class GameManagerPlayerManager
{
    private ArrayList<PlayerDTO> players;
    private PlayerMapper mapper;

    private static GameManagerPlayerManager manager;

    /**
     * Initialize a new PlayerManager.
     */
    GameManagerPlayerManager()
    {
        players = getAllPlayers();
    }

    /**
     * Returns an instance of this PlayerManager.
     *
     * @return an instance of PlayerManager
     * @throws DatabaseException - shouldn't
     */
    public synchronized static GameManagerPlayerManager getInstance() throws DatabaseException
    {
        if (manager == null)
        {
            manager = new GameManagerPlayerManager();
        }
        return manager;
    }

    /**
     * Resets the singleton.
     */
    protected static void resetSingleton()
    {
        if (manager != null)
        {
            manager = null;
        }
    }

    /**
     * Used for testing
     *
     * @param instance The instance to which the singleton will be set.
     */
    protected static void setSingleton(GameManagerPlayerManager instance)
    {
        manager = instance;
    }

    /**
     * Add a player to the data source and store player info in list.
     *
     * @param name      the name of the player
     * @param password  the password for the player
     * @param crewID    the crew the player is in
     * @param majorID   the player's current major
     * @param sectionID the section the player is in
     * @return PlayerInfo DTO
     * @throws DatabaseException - shouldn't
     */
    public PlayerDTO addPlayer(String name, String password, int crewID, int majorID, int sectionID)
            throws DatabaseException
    {
        PlayerDTO player = addPlayerToDatabase(name, password, crewID, majorID, sectionID);
        refreshPlayerList();
        return player;
    }

    private PlayerDTO addPlayerToDatabase(String name, String password, int crewID, int majorID, int sectionID)
            throws DatabaseException
    {
        Major major = Major.getMajorForID(majorID);
        Crew crew = Crew.getCrewForID(crewID);
        Position position = new Position(11, 7);
        int doubloons = 0;
        int experiencePoints = 0;
        String appearanceType = "default_player";

        mapper = new PlayerMapper(position, appearanceType, doubloons, experiencePoints, crew, major, sectionID,
                name, password);

        PlayerDTO player = mapper.getPlayerInfo();
        players.add(player);
        return player;
    }

    /**
     * @param name      the name of the player
     * @param password  the password for the player
     * @param crewID    the crew the player is in
     * @param majorID   the player's current major
     * @param sectionID the section the player is in
     * @throws DatabaseException if we fail to complete the save
     */
    protected void savePlayer(String name, String password, int crewID, int majorID, int sectionID)
            throws DatabaseException
    {
        int playerID;
        try
        {
            playerID = FindPlayerIDFromPlayerName.getPlayerID(name);
            for (PlayerDTO player : players)
            {
                if (player.getPlayerID() == playerID)
                {
                    this.editPlayerInDatabase(player.getPlayerID(), player.getAppearanceType(),
                            player.getDoubloons(), player.getExperiencePoints(), Crew.getCrewForID(crewID),
                            Major.getMajorForID(majorID), sectionID, name, password);
                }
            }

        }
        catch (DatabaseException e)
        {
            this.addPlayerToDatabase(name, password, crewID, majorID, sectionID);
        }
    }

    /**
     * Return the number of players.
     *
     * @return the size of players
     */
    protected int getNumberOfPlayers()
    {
        return players.size();
    }

    /**
     * Return a list of all the players.
     *
     * @return a list of all the players
     */
    protected ArrayList<PlayerDTO> getAllPlayers()
    {
        return PlayerMapper.getAllPlayers();
    }

    /**
     * Return the list of players.
     *
     * @return the list of players
     */
    protected ArrayList<PlayerDTO> getPlayers()
    {
        return players;
    }

    /**
     * Remove a player and remove it from list.
     *
     * @param playerID - the ID of the player to be removed
     * @throws DatabaseException - shouldn't
     */
    protected void removePlayer(int playerID) throws DatabaseException
    {
        mapper = new PlayerMapper(playerID);
        mapper.remove();
        players.remove(mapper.getPlayerInfo());
        if (playerIndex(playerID) != -1)
        {
            int index = playerIndex(playerID);
            this.getPlayers().remove(index);
        }
        refreshPlayerList();
    }

    /**
     * Deletes all players currently in the player table.
     *
     * @throws DatabaseException Unable to find player.
     * @author Jordan Long
     */
    protected void removeAllPlayers() throws DatabaseException
    {
        ArrayList<PlayerDTO> players = getPlayers();
        while (getPlayers().size() != 0)
        {
            int id = players.get(0).getPlayerID();
            removePlayer(id);
        }
        refreshPlayerList();
    }

    /**
     * @param playerID         - id of the player
     * @param name             - The name of the Player
     * @param password         - The players password
     * @param appearanceType   - Appearance type of the player
     * @param quizScore        - Players Quiz Score
     * @param experiencePoints - Players Exp points
     * @param crew             - Crew of the player
     * @param major            - Players Major
     * @param section          - Players section number
     * @return true if successful
     * @throws DatabaseException shouldn't
     */
    public boolean editPlayer(int playerID, String appearanceType, int quizScore, int experiencePoints, Crew crew,
                              Major major, int section, String name, String password)
            throws DatabaseException
    {
        boolean result = editPlayerInDatabase(playerID, appearanceType, quizScore, experiencePoints, crew, major,
                section, name, password);
        this.refreshPlayerList();
        return result;
    }

    private boolean editPlayerInDatabase(int playerID, String appearanceType, int quizScore, int experiencePoints,
                                         Crew crew, Major major, int section, String name, String password) throws DatabaseException
    {
        PlayerMapper playerMap = new PlayerMapper(playerID);
        Player player = playerMap.getPlayer();

        if (player != null)
        {
            player.editPlayer(appearanceType, quizScore, experiencePoints, crew, major, section, name, password);
            refreshPlayerList();
            players = getAllPlayers();
            return true;
        }
        this.refreshPlayerList();
        players = getAllPlayers();
        return false;
    }

    /**
     * Generates and sends a report to all qualified observers
     */
    public void refreshPlayerList()
    {
        ArrayList<PlayerDTO> reportList = getAllPlayers();
        AllPlayersReport report = new AllPlayersReport(reportList);
        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     * Sends a player list report built from the in memory player list
     */
    public void sendPlayerReport()
    {
        ArrayList<PlayerDTO> reportList = getPlayers();
        AllPlayersReport report = new AllPlayersReport(reportList);
        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     * Triggers quests for all users
     *
     * @param questID - id of the quest
     * @throws DatabaseException shouldn't
     */
    public void triggerQuestForAllUsers(int questID) throws DatabaseException
    {
        ArrayList<PlayerDTO> playerList = getPlayers();
        GameManagerQuestManager questManager = GameManagerQuestManager.getInstance();
        for (PlayerDTO player : playerList)
        {
            questManager.triggerQuest(questID, player.getPlayerID());

        }

    }

    /**
     * Finds the index of the player in the player list
     *
     * @param id PlayerID
     * @return Index
     */
    protected int playerIndex(int id)
    {
        ArrayList<PlayerDTO> players = this.getPlayers();

        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).getPlayerID() == id)
            {
                return i;
            }
        }

        return -1;
    }

}
