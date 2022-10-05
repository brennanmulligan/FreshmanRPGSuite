package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
public class CommandRemovePlayerTest
{
    /**
     * If we remove a player, the player manager and quest manager should forget
     * about it
     *
     * @throws PlayerNotFoundException shouldn't
     */
    @Test
    public void test() throws PlayerNotFoundException
    {
        PlayerManager.getSingleton().addPlayer(1);
        CommandRemovePlayer cmd = new CommandRemovePlayer(1);
        cmd.execute();
        assertNull(PlayerManager.getSingleton().getPlayerFromID(1));
        assertNull(QuestManager.getSingleton().getQuestList(1));
    }

}
