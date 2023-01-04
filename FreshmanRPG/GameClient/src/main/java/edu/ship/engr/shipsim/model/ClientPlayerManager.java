package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Maintains the active set of players on the area server to which this client
 * is attached
 *
 * @author merlin
 */
public class ClientPlayerManager
{

    private static ClientPlayerManager singleton;
    private final HashMap<Integer, ClientPlayer> playerList;
    private ThisClientsPlayer thisClientsPlayer;
    private boolean loginInProgress;

    private ClientPlayerManager()
    {
        thisClientsPlayer = null;
        playerList = new HashMap<>();

    }

    /**
     * There should be only one
     *
     * @return the only player
     */
    public static synchronized ClientPlayerManager getSingleton()
    {
        if (singleton == null)
        {
            singleton = new ClientPlayerManager();
        }
        return singleton;
    }


    /**
     * Used only in testing to re-initialize the singleton
     */
    public static void resetSingleton()
    {
        OptionsManager.getSingleton().assertTestMode();
        singleton = null;
    }

    /**
     * Get the player that is playing on this client
     *
     * @return that player
     */
    public ThisClientsPlayer getThisClientsPlayer()
    {
        return thisClientsPlayer;
    }

    /**
     * Get a player with the given player id
     *
     * @param playerID the unique player id of the player in which we are interested
     * @return the appropriate Player object or null if no such player exists
     */
    public ClientPlayer getPlayerFromID(int playerID)
    {
        return playerList.get(playerID);
    }

    /**
     * Sets the player controlled by this client
     *
     * @param playerID ths unique identifier of the player
     * @return the player object we created
     * @throws AlreadyBoundException when a login has occurred and the player is set
     * @throws NotBoundException     when the player has not yet been set because login has not be
     *                               called
     */
    public ThisClientsPlayer finishLogin(int playerID) throws AlreadyBoundException, NotBoundException
    {
        if (this.loginInProgress)
        {
            ThisClientsPlayer p = new ThisClientsPlayer(playerID);
            playerList.put(playerID, p);
            this.thisClientsPlayer = p;

            // prevent replacing the player after logging in
            loginInProgress = false;
            return p;
        }
        else
        {
            if (this.thisClientsPlayer == null)
            {
                throw new NotBoundException("Login has not yet been called, player has not been set.");
            }
            else
            {
                throw new AlreadyBoundException(
                        "Login has already been called, you canDave,  not reset the player until logging out.");
            }
        }
    }

    /**
     * @return true if we are in the process of trying to log into the server
     */
    public boolean isLoginInProgress()
    {
        return loginInProgress;
    }

    /**
     * Attempt to login with a given name and password
     *
     * @param password the password
     * @param name     the player's name
     */
    public void initiateLogin(String name, String password)
    {
        loginInProgress = true;
        ReportObserverConnector.getSingleton().sendReport(new LoginInitiatedReport(name, password));
    }

    /**
     * Either create a new player with the attributes given, or update an
     * existing player with the given playerID to have these attributes
     *
     * @param playerID   The id of the player
     * @param playerName The name of the player
     * @param vanities   The list of all vanity objects the player is wearing
     * @param position   The position of this player
     * @param crew       The crew to which this player belongs
     * @param major      of the player
     * @param section    number of the player
     * @return Player The player updated
     */
    public ClientPlayer initializePlayer(int playerID, String playerName, List<VanityDTO> vanities, Position position,
                                         Crew crew, Major major, int section)
    {
        return initializePlayer(playerID, playerName, vanities, position, crew, major, section, new ArrayList<>());
    }

    public ClientPlayer initializePlayer(int playerID, String playerName, List<VanityDTO> vanities, Position position,
                                         Crew crew, Major major, int section, List<VanityDTO> ownedItems)
    {
        ClientPlayer player;
        if (playerList.containsKey(playerID))
        {
            player = playerList.get(playerID);
            player.setName(playerName);
            player.setVanityNoReport(vanities);
            player.setPosition(position);
            player.setCrew(crew);
            player.setMajor(major);
            player.setSection(section);
            player.setOwnedItems(ownedItems);
            this.tellObserversToRemoveThePlayer(playerID);
        }
        else
        {
            player = new ClientPlayer(playerID);
            player.setName(playerName);
            player.setVanityNoReport(vanities);
            player.setPosition(position);
            player.setCrew(crew);
            player.setMajor(major);
            player.setSection(section);
            player.setOwnedItems(ownedItems);
        }
        playerList.put(playerID, player);

        boolean isThisClientsPlayer = false;
        if (thisClientsPlayer != null)
        {
            isThisClientsPlayer = playerID == thisClientsPlayer.getID();
        }

        PlayerConnectedToAreaServerReport report = new PlayerConnectedToAreaServerReport(playerID, playerName,
                position, crew, major, isThisClientsPlayer, vanities);
        ReportObserverConnector.getSingleton().sendReport(report);

        if (thisClientsPlayer != null)
        {
            for (FriendDTO friendDTO : thisClientsPlayer.friendList)
            {
                if (friendDTO.getFriendID() == playerID)
                {
                    FriendChangedStateReport friendReport = new FriendChangedStateReport(playerID);
                    ReportObserverConnector.getSingleton().sendReport(friendReport);
                }
            }
        }

        return player;
    }

    /**
     * Removes a player from being managed by this manager
     *
     * @param playerID The id of the player
     */
    public void removePlayer(int playerID)
    {
        ClientPlayer player = this.playerList.remove(playerID);
        if (player != null)
        {
            tellObserversToRemoveThePlayer(playerID);
        }
    }

    private void tellObserversToRemoveThePlayer(int playerID)
    {
        PlayerDisconnectedFromAreaServerReport report = new PlayerDisconnectedFromAreaServerReport(playerID);
        ReportObserverConnector.getSingleton().sendReport(report);

        if (thisClientsPlayer != null)
        {
            for (FriendDTO friendDTO : thisClientsPlayer.friendList)
            {
                if (friendDTO.getFriendID() == playerID)
                {
                    FriendChangedStateReport friendReport = new FriendChangedStateReport(playerID);
                    ReportObserverConnector.getSingleton().sendReport(friendReport);
                }
            }
        }
    }

    /**
     * Report when a login has failed
     */
    public void loginFailed(String message)
    {
        LoginFailedReport report = new LoginFailedReport(message);
        loginInProgress = false;
        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     * Report when a pin has failed
     *
     * @param err is the error message from a pin failure
     */
    public void pinFailed(String err)
    {
        PinFailedReport report = new PinFailedReport(err);
        loginInProgress = false;
        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     * Remove the other players from the display
     */
    public void removeOtherPlayers()
    {
        ArrayList<Integer> playersToRemove = new ArrayList<>();
        for (java.util.Map.Entry<Integer, ClientPlayer> x : playerList.entrySet())
        {

            if (x.getValue().getID() != thisClientsPlayer.getID())
            {
                playersToRemove.add(x.getValue().getID());
            }
        }
        for (int id : playersToRemove)
        {
            removePlayer(id);
        }
    }
}
