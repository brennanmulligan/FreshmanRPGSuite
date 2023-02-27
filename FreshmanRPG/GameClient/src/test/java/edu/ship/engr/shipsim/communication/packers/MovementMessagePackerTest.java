package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.PlayerMovedMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.ClientModelTestUtilities;
import edu.ship.engr.shipsim.model.reports.ClientPlayerMovedReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientPlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author merlin
 */
@GameTest("GameClient")
@ResetClientPlayerManager
public class MovementMessagePackerTest
{
    /**
     * Set up this client's player to be player number 1
     */
    @BeforeEach
    public void setup()
    {
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JOHN);
    }

    /**
     * Just make sure the right stuff goes into the message when we pack it
     */
    @Test
    public void test()
    {
        PlayerMovedMessagePacker packer = new PlayerMovedMessagePacker();
        PlayerMovedMessage msg = (PlayerMovedMessage) packer.pack(new ClientPlayerMovedReport(1,
                new Position(4, 3), true));
        assertEquals(1, msg.getRelevantPlayerID());
        assertEquals(new Position(4, 3), msg.getPosition());
    }

    /**
     * If the packer sees a report that a different player moved, it shouldn't
     * pack anything (just returning null)
     */
    @Test
    public void onlySendIfThisPlayer()
    {
        PlayerMovedMessagePacker packer = new PlayerMovedMessagePacker();
        PlayerMovedMessage msg = (PlayerMovedMessage) packer.pack(new ClientPlayerMovedReport(2,
                new Position(4, 3), false));
        assertNull(msg);
    }
}
