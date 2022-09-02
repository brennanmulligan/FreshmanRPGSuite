package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.InteractionDeniedMessage;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.model.reports.InteractionDeniedReport;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author ag0612
 * @author jk1964
 * Tests the functionality of the ObjectInRangeMessagePacker
 */
public class InteractionDeniedMessagePackerTest extends ServerSideTest
{

    /**
     * Tests that the message packer packs the correct report type
     */
    @Test
    public void testReportTypeWePack()
    {
        ObjectInRangeMessagePacker packer = new ObjectInRangeMessagePacker();
        assertEquals(InteractionDeniedReport.class, packer.getReportTypesWePack().get(0));
    }

    /**
     * Tests that the message packed is of the correct type
     */
    @Test
    public void testMessageisCorrect()
    {
        int pID = 1392;
        ObjectInRangeMessagePacker packer = new ObjectInRangeMessagePacker();
        InteractionDeniedReport report = new InteractionDeniedReport(pID);
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(pID);
        packer.setAccumulator(stateAccumulator);

        assertEquals(InteractionDeniedMessage.class, packer.pack(report).getClass());
    }

    /**
     * Tests that the player ID is properly packed
     */
    @Test
    public void testPIDisCorrect()
    {
        int pID = 1392;
        ObjectInRangeMessagePacker packer = new ObjectInRangeMessagePacker();
        InteractionDeniedReport report = new InteractionDeniedReport(pID);
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(pID);
        packer.setAccumulator(stateAccumulator);

        InteractionDeniedMessage message = (InteractionDeniedMessage) packer.pack(report);

        assertEquals(message.getPlayerID(), report.getPlayerID());
    }

    /**
     * test doesn't work for player not connected
     */
    @Test
    public void testIncorrectPlayerPacker()
    {
        int pID = 1392;
        ObjectInRangeMessagePacker packer = new ObjectInRangeMessagePacker();
        InteractionDeniedReport report = new InteractionDeniedReport(400);
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(pID);
        packer.setAccumulator(stateAccumulator);

        packer.pack(report);

        assertNull(packer.pack(report));
    }
}
