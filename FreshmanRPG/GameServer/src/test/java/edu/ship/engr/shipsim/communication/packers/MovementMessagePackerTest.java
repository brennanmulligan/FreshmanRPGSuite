package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.OtherPlayerMovedMessage;
import edu.ship.engr.shipsim.communication.messages.PlayerMovedMessage;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.reports.PlayerMovedReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Andrew
 * @author Steve
 * @author Matt
 */
@GameTest("GameServer")
@ResetPlayerManager
public class MovementMessagePackerTest
{
    private StateAccumulator stateAccumulator;

    /**
     * reset the necessary singletons
     */
    @BeforeEach
    public void localSetUp()
    {
        PlayerManager.getSingleton().addPlayer(1);
        stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(1);
    }

    /**
     * Test that we pack a PlayerMovedReport
     */
    @Test
    public void testReportTypeWePack()
    {
        OtherPlayerMovedMessagePacker packer = new OtherPlayerMovedMessagePacker();
        assertEquals(PlayerMovedReport.class, packer.getReportTypesWePack().get(0));
    }

    /**
     * Given a PlayerMovedReport for the current player, the message should be
     * null
     */
    @Test
    public void testPackedObjectIsCurrentPlayer()
    {
        Position position = new Position(1, 2);
        PlayerMovedReport report = new PlayerMovedReport(stateAccumulator.getPlayerID(), "fred", position, "mapName");
        OtherPlayerMovedMessagePacker packer = new OtherPlayerMovedMessagePacker();
        packer.setAccumulator(stateAccumulator);

        PlayerMovedMessage message = (PlayerMovedMessage) packer.pack(report);
        assertNull(message);
    }

    /**
     * Given a PlayerMovedReport for a player other than the client, ensure that
     * the MovementMessage is what we expect
     */
    @Test
    public void testPackedObjectNotCurrentPlayer()
    {
        Position position = new Position(1, 2);
        PlayerMovedReport report = new PlayerMovedReport(-1, "fred", position, "mapName");
        OtherPlayerMovedMessagePacker packer = new OtherPlayerMovedMessagePacker();
        packer.setAccumulator(stateAccumulator);

        OtherPlayerMovedMessage message = (OtherPlayerMovedMessage) packer.pack(report);
        assertEquals(-1, message.getRelevantPlayerID());
        assertEquals(position, message.getPosition());
    }
}
