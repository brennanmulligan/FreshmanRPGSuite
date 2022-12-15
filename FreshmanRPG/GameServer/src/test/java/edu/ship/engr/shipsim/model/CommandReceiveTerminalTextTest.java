package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests that the GetAllConnectedPlayers command gets executed properly.
 *
 * @author Nathaniel, Kanza
 */
@GameTest("GameServer")
public class CommandReceiveTerminalTextTest
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
