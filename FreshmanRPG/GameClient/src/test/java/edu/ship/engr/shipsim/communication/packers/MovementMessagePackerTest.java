package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.PlayerMovedMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.ClientModelTestUtilities;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.reports.ClientPlayerMovedReport;
import org.junit.Before;
import org.junit.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author merlin
 */
public class MovementMessagePackerTest
{

    /**
     * Just make sure the right stuff goes into the message when we pack it
     */
    @Test
    public void test()
    {
        PlayerMovedMessagePacker packer = new PlayerMovedMessagePacker();
        PlayerMovedMessage msg = (PlayerMovedMessage) packer.pack(new ClientPlayerMovedReport(1,
                new Position(4, 3), true));
        assertEquals(1, msg.getPlayerID());
        assertEquals(new Position(4, 3), msg.getPosition());
    }

    /**
     * If the packer sees a report that a different player moved, it shouldn't
     * pack anything (just returning null)
     *
     * @throws AlreadyBoundException shouldn't
     * @throws NotBoundException     shouldn't
     */
    @Test
    public void onlySendIfThisPlayer() throws AlreadyBoundException, NotBoundException
    {
        setup();
        PlayerMovedMessagePacker packer = new PlayerMovedMessagePacker();
        PlayerMovedMessage msg = (PlayerMovedMessage) packer.pack(new ClientPlayerMovedReport(2,
                new Position(4, 3), false));
        assertNull(msg);
    }

    /**
     * Set up this client's player to be player number 1
     *
     * @throws AlreadyBoundException shouldn't
     * @throws NotBoundException     shouldn't
     */
    @Before
    public void setup() throws AlreadyBoundException, NotBoundException
    {
        ClientPlayerManager.resetSingleton();
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JOHN);
    }

}
