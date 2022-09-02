package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests that the GetAllConnectedPlayers command gets executed properly.
 *
 * @author Nathaniel, Kanza
 */
public class CommandReceiveTerminalTextTest extends ServerSideTest
{
    /**
     * If it executes, we're golden -Nathaniel
     */
    @Test
    public void testExecute()
    {
        int playerID = PlayersForTest.MERLIN.getPlayerID();

        PlayerManager.getSingleton().getConnectedPlayers();
        CommandReceiveTerminalText cgaop = new CommandReceiveTerminalText(playerID, "who");

        assertTrue(cgaop.execute());


    }
}
