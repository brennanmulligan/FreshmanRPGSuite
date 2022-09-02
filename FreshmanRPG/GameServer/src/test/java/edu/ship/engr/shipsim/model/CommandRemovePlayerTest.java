package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author Merlin
 */
public class CommandRemovePlayerTest extends ServerSideTest
{

    /**
     *
     */
    @Before
    public void localSetup()
    {
        PlayerManager.resetSingleton();
    }

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
