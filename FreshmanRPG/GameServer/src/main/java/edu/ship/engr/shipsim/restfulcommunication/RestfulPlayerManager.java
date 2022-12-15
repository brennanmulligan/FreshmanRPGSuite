package edu.ship.engr.shipsim.restfulcommunication;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import org.apache.commons.compress.utils.Lists;
import org.javatuples.Pair;

import java.util.Base64;
import java.util.List;

/**
 * @author Derek
 */
public final class RestfulPlayerManager
{
    private static volatile RestfulPlayerManager singleton;
    private final List<Pair<Integer, String>> authKeys;

    private RestfulPlayerManager()
    {
        authKeys = Lists.newArrayList();
    }

    public static synchronized RestfulPlayerManager getSingleton()
    {
        if (singleton == null)
        {
            singleton = new RestfulPlayerManager();
        }

        return singleton;
    }

    /**
     * Generates a Base64 encoded string consisting of the player's ID and name
     *
     * @param player the player to initialize
     * @return an authentication token to be sent with each restful request
     */
    public String initializePlayer(Player player)
    {
        String pString = String.format("%s%s", player.getPlayerID(), player.getPlayerName());

        String authKey = Base64.getEncoder().encodeToString(pString.getBytes());

        authKeys.add(Pair.with(player.getPlayerID(), authKey));

        return authKey;
    }

    /**
     * @param authKey the key to search for
     * @return the playerId paired with the authKey
     */
    public Integer findPlayerID(String authKey)
    {
        return authKeys.stream().filter(key -> key.getValue1().equals(authKey)).findFirst().map(Pair::getValue0).orElse(null);
    }

    /**
     * Remove the player matching the authKey from this manager and the server's PlayerManager
     *
     * @param authKey the key to search for
     * @throws DatabaseException Shouldn't
     */
    public void removePlayer(String authKey) throws DatabaseException
    {
        Integer playerID = findPlayerID(authKey);

        PlayerManager.getSingleton().removePlayerSilently(playerID);

        authKeys.removeIf(p -> p.getValue0().equals(authKey));

        LoggerManager.getSingleton().getLogger().info("Removed Player from RestfulPlayerManager");
    }
}
