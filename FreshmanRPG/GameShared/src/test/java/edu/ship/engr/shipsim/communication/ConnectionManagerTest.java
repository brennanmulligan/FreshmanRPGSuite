package edu.ship.engr.shipsim.communication;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Merlin
 */
@GameTest("GameShared")
public class ConnectionManagerTest
{

    /**
     * We should really figure out something we can test
     */
    @Test
    public void settingPlayerIDTellsStateAccumulator()
    {
        ConnectionManager cm = new ConnectionManager();
        cm.stateAccumulator = new StateAccumulator(null);
        cm.setPlayerID(33);
        assertEquals(33, cm.getPlayerID());
        assertEquals(33, cm.stateAccumulator.getPlayerID());
    }

}
