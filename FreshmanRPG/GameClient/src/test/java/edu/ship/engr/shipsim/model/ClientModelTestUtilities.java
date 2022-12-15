package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Utilities that tests can use to set up state in the model or retrieve
 * information from the model This allows tests of packages that do not have
 * visibility into the model access that production code should not have
 *
 * @author merlin
 */
public class ClientModelTestUtilities
{

    /**
     * Forces the model to think a player has officially logged onto the system
     *
     * @param player the player who should be this client's player
     */
    public static void setUpThisClientsPlayerForTest(PlayersForTest player)
    {
        // setup initial player for testing
        ClientPlayerManager pm = ClientPlayerManager.getSingleton();
        pm.initiateLogin(player.getPlayerName(), player.getPlayerPassword());
        try
        {
            pm.finishLogin(player.getPlayerID());
        }
        catch (AlreadyBoundException | NotBoundException e)
        {
            e.printStackTrace();
            fail("We have had trouble setting up this client's player for testing");
        }

    }

    /**
     * @return the position of this client's player
     */
    public static Position getThisPlayersPosition()
    {
        return ClientPlayerManager.getSingleton().getThisClientsPlayer().getPosition();
    }

    /**
     * Forces the model to believe that there is another player connected to the
     * same server that this client is connected to
     *
     * @param player the other player
     */
    public static void setUpOtherPlayerForTest(PlayersForTest player)
    {
        ClientPlayerManager.getSingleton().initializePlayer(player.getPlayerID(), player.getPlayerName(),
                player.getVanityItems(), player.getPosition(), player.getCrew(),
                player.getMajor(), player.getSection());
    }
}
