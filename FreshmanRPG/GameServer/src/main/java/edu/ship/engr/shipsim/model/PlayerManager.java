package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.LevelManagerDTO;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.*;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.model.reports.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Merlin
 */
public class PlayerManager implements ReportObserver
{
    /**
     * @return the only PlayerManger in the system
     */
    public synchronized static PlayerManager getSingleton()
    {
        if (singleton == null)
        {
            try
            {
                singleton = new PlayerManager();
            }
            catch (DatabaseException e)
            {
                e.printStackTrace();
            }
        }
        return singleton;
    }

    /**
     * reset the singleton for testing purposes.
     */
    public static void resetSingleton()
    {
        OptionsManager.getSingleton().assertTestMode();
        if (singleton != null)
        {
            singleton.stopNpcs();
            singleton = null;
        }
    }

    private static PlayerManager singleton;

    private final HashMap<Integer, Player> players;
    private final ArrayList<NPC> npcs;

    /**
     * @return a list of the NPCs
     */
    public ArrayList<NPC> getNpcs()
    {
        return npcs;
    }

    private PlayerManager() throws DatabaseException
    {
        players = new HashMap<>();
        npcs = new ArrayList<>();
        ReportObserverConnector.getSingleton().registerObserver(this, PlayerDisconnectedReport.class);
    }

    /**
     * Adds a player to the list of active players on this server without checking
     * its pin - only for testing purposes
     *
     * @param playerID the players id number
     * @return the player object for the added player
     */
    public Player addPlayer(int playerID)
    {
        Player player = addPlayerSilently(playerID);

        try
        {
            TimerManager.getSingleton().loadUserTimers(playerID);
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }

        ReportObserverConnector.getSingleton().sendReport(new PlayerConnectionReport(playerID, player.getPlayerName(), player.getAppearanceType(), player.getPosition(),
                player.getCrew(), player.getMajor(), player.getSection(), player.getVanityItems()));
        return player;
    }

    /**
     * @param playerID the id of the player
     * @return the player object that was added
     */
    public Player addPlayerSilently(int playerID)
    {
//		if (!OptionsManager.getSingleton().isUsingMockDataSource())
//		{
//			throw new IllegalStateException("Trying to add a player without giving a PIN when not in test mode");
//		}
        try
        {
            PlayerMapper mapper = new PlayerMapper(playerID);
            Player player = mapper.getPlayer();
            mapper.addNewVisitedMap(player.getMapName());
            player.setPlayerLogin(new PlayerLogin(playerID));
            players.put(playerID, player);
            return player;
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a player to the list of active players on this server
     *
     * @param playerID the players id number
     * @param pin      the pin we gave the client to connect to this server
     * @return the player object that we added
     * @throws DatabaseException           if the player's pin was not correct
     * @throws IllegalQuestChangeException the state changed illegally
     */
    public Player addPlayer(int playerID, double pin) throws DatabaseException, IllegalQuestChangeException
    {
        PlayerMapper pm = new PlayerMapper(playerID);
        Player player = pm.getPlayer();
        pm.addNewVisitedMap(player.getMapName());
        player.setPlayerOnline(true);
        player.persist();

        if (player.isPinValid(pin))
        {
            players.put(playerID, player);

            ReportObserverConnector.getSingleton().sendReport(new PlayerConnectionReport(player.getPlayerID(), player.getPlayerName(), player.getAppearanceType(), player
                    .getPosition(), player.getCrew(), player.getMajor(), player.getSection(), player.getVanityItems(), player.getAllOwnedItems()));

            ReportObserverConnector.getSingleton().sendReport(new UpdatePlayerInformationReport(player));
            tellNewPlayerAboutEveryoneElse(player);

            LevelRecord currentLevel = LevelManagerDTO.getSingleton().getLevelForPoints(player.getExperiencePoints());
            LevelRecord nextLevel = LevelManagerDTO.getSingleton().getNextLevelForPoints(player.getExperiencePoints());
            ReportObserverConnector.getSingleton().sendReport(new TimeToLevelUpDeadlineReport(player.getPlayerID(), currentLevel.getDeadlineDate(), nextLevel
                    .getDescription()));

            ReportObserverConnector.getSingleton().sendReport(new PlayerFinishedInitializingReport(player.getPlayerID(), player.getPlayerName(), player.getAppearanceType()));
            DoubloonPrizesTableDataGateway doubloonPrizesGateway =
                    DoubloonPrizesTableDataGateway.getSingleton();
            FriendTableDataGateway friendTableDataGateway =
                    FriendTableDataGateway.getSingleton();

            ReportObserverConnector.getSingleton().sendReport(new DoubloonPrizeReport(player.getPlayerID(), doubloonPrizesGateway.getAllDoubloonPrizes()));
            ReportObserverConnector.getSingleton().sendReport(new FriendListReport(player.getPlayerID(), friendTableDataGateway.getAllFriends(player.getPlayerID())));
            return player;
        }
        else
        {
            pm.removeQuestStates();
            System.err.println("Failed adding player " + playerID + " with pin " + pin);
            PinFailedReport report = new PinFailedReport(playerID);
            ReportObserverConnector.getSingleton().sendReport(report);
        }
        return null;
    }

    /**
     * @return a collection of all the players currently connected to the player
     * manager
     */
    public Collection<Player> getConnectedPlayers()
    {
        return this.players.values();
    }

    /**
     * Get a new PIN for a player so they can connect to a different area server
     *
     * @param playerID the player ID
     * @return the pin they should use for their next connection
     * @throws DatabaseException shouldn't
     */
    public int getNewPinFor(int playerID) throws DatabaseException
    {
        PlayerConnection pin = new PlayerConnection(playerID);
        return pin.generatePin();
    }

    /**
     * @param playerID the playerID of the player we are looking for
     * @return the player we were looking for
     */
    public Player getPlayerFromID(int playerID)
    {
        return players.get(playerID);
    }

    /**
     * @param playerName the player name of the player we are searching for
     * @return the player ID of the player we are searching for
     * @throws PlayerNotFoundException if no player with that player name is found
     */
    public int getPlayerIDFromPlayerName(String playerName) throws PlayerNotFoundException
    {
        System.out.println();
        for (Player p : players.values())
        {
            if (p.getPlayerName().equalsIgnoreCase(playerName))
            {
                return p.getPlayerID();
            }
        }
        throw new PlayerNotFoundException("Failed searching for player named " + playerName);
    }

    /**
     * @return the players with the top ten scores sorted by score
     * @throws DatabaseException if the data source can't get the data for us
     */
    public ArrayList<PlayerScoreRecord> getTopTenPlayers() throws DatabaseException
    {
        PlayerTableDataGateway gateway =
                PlayerTableDataGateway.getSingleton();
        return gateway.getTopTenList();
    }

    /**
     * Load the npcs that belong on this map, add them to player manager, and start
     * them
     *
     * @param quietMode If true, the NPCs won't be started and therefore, won't
     *                  generate chat messages.  Good for sequence testing!
     * @throws DatabaseException when database goes wrong
     */
    public void loadNpcs(boolean quietMode) throws DatabaseException
    {
        ArrayList<NPCMapper> pendingNPCs = NPCMapper.findNPCsOnMap(OptionsManager.getSingleton().getMapFileTitle());
        for (NPCMapper m : pendingNPCs)
        {
            LoggerManager.getSingleton().getLogger().info("Adding NPC: " + m.getPlayer().getPlayerName());
            NPC nextNPC = (NPC) m.getPlayer();
            players.put(nextNPC.getPlayerID(), nextNPC);
            if (!quietMode)
            {
                nextNPC.start();
            }
            npcs.add(nextNPC);
        }
    }

    /**
     * @param npc the npc that should be added
     */
    public void addNpc(NPC npc)
    {
        players.put(npc.getPlayerID(), npc);
        npc.start();
    }

    /**
     * @return the number of players currently on this system
     */
    public int numberOfPlayers()
    {
        return players.size();
    }

    /**
     * Persist the player with a given ID
     *
     * @param playerID The player id of the player to persist
     * @return Success status of persistence
     * @throws DatabaseException IF we have trouble persisting to the data source
     */
    public boolean persistPlayer(int playerID) throws DatabaseException
    {

        Player player = this.getPlayerFromID(playerID);
        if (player != null)
        {
            player.persist();
            TimerManager.getSingleton().persistPlayerTimers(playerID);
        }
        return true;

    }

    /**
     * Remove a player from this server's player manager and inform all connected
     * clients of the disconnection
     *
     * @param playerID the ID of the player we should remove
     * @throws DatabaseException if we can't persist the player to the data source
     */
    public void removePlayer(int playerID) throws DatabaseException
    {
        removePlayerSilently(playerID);
        Player p = PlayerManager.getSingleton().getPlayerFromID(playerID);
        if (p != null)
        {
            // send the disconnect message to clients
            PlayerLeaveReport report = new PlayerLeaveReport(playerID);
            ReportObserverConnector.getSingleton().sendReport(report);
        }
    }

    public void removePlayerSilently(int playerID) throws DatabaseException
    {
        Player p = PlayerManager.getSingleton().getPlayerFromID(playerID);

        if (p != null)
        {
            p.setPlayerOnline(false);
            persistPlayer(playerID);
            this.players.remove(playerID);
        }
    }

    /**
     * Stop all of the npcs. This is necessary when the PlayerManager is reset so
     * that the npcs do not have runaway timers that may be re-created.
     */
    public void stopNpcs()
    {
        if (npcs != null)
        {
            for (NPC npc : npcs)
            {
                npc.stop();
            }
        }
    }

    private void tellNewPlayerAboutEveryoneElse(Player player)
    {
        Collection<Player> currentPlayers = players.values();
        for (Player existingPlayer : currentPlayers)
        {
            if (existingPlayer.getPlayerID() != player.getPlayerID())
            {
                AddExistingPlayerReport report;
                if (existingPlayer.getVanityItems().isEmpty())
                {
                    report = new AddExistingPlayerReport(player.getPlayerID(), existingPlayer.getPlayerID(), existingPlayer.getPlayerName(), existingPlayer
                            .getAppearanceType(), existingPlayer.getPosition(), existingPlayer.getCrew(), existingPlayer.getMajor(), existingPlayer.getSection(), player.getVanityItems());
                }
                else
                {
                    report = new AddExistingPlayerReport(player.getPlayerID(), existingPlayer.getPlayerID(), existingPlayer.getPlayerName(), existingPlayer
                            .getAppearanceType(), existingPlayer.getPosition(), existingPlayer.getCrew(), existingPlayer.getMajor(), existingPlayer.getSection(), existingPlayer.getVanityItems());
                }
                ReportObserverConnector.getSingleton().sendReport(report);
            }
        }
    }

    /**
     * @see ReportObserver#receiveReport(Report)
     */
    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass().equals(PlayerDisconnectedReport.class))
        {
            PlayerDisconnectedReport detailedReport = (PlayerDisconnectedReport) report;
            try
            {
                removePlayer(detailedReport.getPlayerID());
            }
            catch (DatabaseException e)
            {
                e.printStackTrace();
            }

        }
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
    public boolean editPlayer(int playerID, String appearanceType, int quizScore, int experiencePoints, Crew crew, Major major, int section, String name, String password)
            throws DatabaseException
    {
        return editPlayerInDatabase(playerID, appearanceType, quizScore, experiencePoints,
                crew, major, section, name, password);
    }

    private boolean editPlayerInDatabase(int playerID, String appearanceType, int quizScore, int experiencePoints, Crew crew, Major major, int section, String name, String password)
            throws DatabaseException
    {
        PlayerMapper playerMap = new PlayerMapper(playerID);
        Player player = playerMap.getPlayer();

        if (player != null)
        {
            player.editPlayer(appearanceType, quizScore, experiencePoints, crew, major, section, name, password);
            return true;
        }
        return false;
    }

    /**
     * @return list of the vanity shops inventory
     */
    public ArrayList<VanityDTO> getVanityShopInventory() throws DatabaseException
    {
        return VanityShopTableDataGateway.getSingleton().getVanityShopInventory();
    }

    /**
     * Method that checks the type of a Player to see if it is an NPC or not
     * @param relevantID the ID to be checked to if it is a player or NPC
     * @return true if it is an NPC, false otherwise.
     */
    public boolean isNPC(int relevantID)
    {
        int index = 0;

        while(index < npcs.size() && npcs.get(index).getPlayerID() != relevantID)
        {
            index = index + 1;
        }

        if(index == npcs.size())
        {
            return false;
        }

        return true;

    }
}