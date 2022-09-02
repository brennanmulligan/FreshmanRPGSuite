package edu.ship.engr.shipsim.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Merlin
 */
public class CommandLoginTest
{

    /**
     * reset the player to make sure the login in cleat
     */
    @Before
    public void setup()
    {
        ClientPlayerManager.resetSingleton();
    }

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
