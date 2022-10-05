package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Matt and Andy
 */
@GameTest("GameClient")
@ResetClientPlayerManager
public class CommandLoginFailedTest
{
    /**
     * We just need to tell the player to initiate this
     */
    @Test
    public void shouldTellPlayer()
    {
        CommandLoginFailed cmd = new CommandLoginFailed();
        cmd.execute();
        ClientPlayerManager p = ClientPlayerManager.getSingleton();
        assertFalse(p.isLoginInProgress());

    }

}
