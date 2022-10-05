package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Merlin
 */
@GameTest("GameClient")
@ResetClientPlayerManager
public class CommandLoginTest
{
    /**
     * We just need to tell the player to initiate this
     */
    @Test
    public void shouldTellPlayer()
    {
        CommandLogin cmd = new CommandLogin("Fred", "pw");
        cmd.execute();
        ClientPlayerManager p = ClientPlayerManager.getSingleton();
        assertTrue(p.isLoginInProgress());

    }
}
