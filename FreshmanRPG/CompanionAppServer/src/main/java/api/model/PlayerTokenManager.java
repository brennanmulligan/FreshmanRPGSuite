package api.model;

import edu.ship.engr.shipsim.model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class PlayerTokenManager
{

    private static PlayerTokenManager instance;
    private Map<Integer, Player> tokenMap;

    /**
     *
     */
    private PlayerTokenManager()
    {
        tokenMap = new HashMap<Integer, Player>();
    }

    /**
     * @return
     */
    private int generateToken()
    {
        long base = (System.currentTimeMillis() * 2) + 60;
        return (int) (base / 5000);
    }

    /**
     * @return
     */
    public static PlayerTokenManager getInstance()
    {
        if (instance == null)
        {
            instance = new PlayerTokenManager();
        }
        return instance;
    }

    /**
     * @param player
     * @return
     */
    public int addPlayer(Player player)
    {
        if (tokenMap.containsValue(player))
        {
            for (int key : tokenMap.keySet())
            {
                if (tokenMap.get(key).getPlayerID() == player.getPlayerID())
                {
                    return key;
                }
            }
            return -1;
        }
        else
        {
            int token = generateToken();
            tokenMap.put(token, player);
            return token;
        }
    }

    /**
     * @param token
     * @return
     */
    public boolean removePlayer(int token)
    {
        if (tokenMap.containsKey(token))
        {
            tokenMap.remove(token);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Gets a player from the token map using a token
     *
     * @param token
     * @return
     */
    public Player getPlayer(int token)
    {
        if (tokenMap.containsKey(token))
        {
            return tokenMap.get(token);
        }
        else
        {
            return null;
        }
    }
}
