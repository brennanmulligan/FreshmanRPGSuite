package edu.ship.engr.shipsim.communication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Merlin
 */
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
