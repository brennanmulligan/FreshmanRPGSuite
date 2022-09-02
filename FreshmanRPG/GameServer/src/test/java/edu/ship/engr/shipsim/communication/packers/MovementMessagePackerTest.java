package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.OtherPlayerMovedMessage;
import edu.ship.engr.shipsim.communication.messages.PlayerMovedMessage;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.reports.PlayerMovedReport;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Andrew
 * @author Steve
 * @author Matt
 */
public class MovementMessagePackerTest extends ServerSideTest
{
    private StateAccumulator stateAccumulator;

    /**
     * reset the necessary singletons
     */
    @Before
    public void localSetUp()
    {
        PlayerManager.resetSingleton();
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
        assertEquals(-1, message.getPlayerID());
        assertEquals(position, message.getPosition());
    }
}
