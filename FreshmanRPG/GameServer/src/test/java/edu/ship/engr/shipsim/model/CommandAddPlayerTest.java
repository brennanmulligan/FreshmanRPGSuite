package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
public class CommandAddPlayerTest
{
    /**
     * If we add a player, the playermanager should know about it
     *
     * @throws PlayerNotFoundException shouldn't
     */
    @Test
    public void test() throws PlayerNotFoundException
    {
        CommandAddPlayer cmd = new CommandAddPlayer(PlayersForTest.JOHN.getPlayerID(), PlayersForTest.JOHN.getPin());
        cmd.execute();
        Player player = PlayerManager.getSingleton().getPlayerFromID(1);
        assertNotNull(player);
        assertEquals("John", player.getPlayerName());
    }

}
