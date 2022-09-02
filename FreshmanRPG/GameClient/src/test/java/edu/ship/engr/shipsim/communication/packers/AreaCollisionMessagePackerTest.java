package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.AreaCollisionMessage;
import edu.ship.engr.shipsim.model.reports.AreaCollisionReport;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Dave
 * <p>
 * Make sure that the ChatMessagePacker behaves properly.
 */
public class AreaCollisionMessagePackerTest
{

    /**
     * Make sure that the report is properly translated into the message.
     */
    @Test
    public void testPacking()
    {
        int sender = 1;
        String region = "test";

        AreaCollisionReport report = new AreaCollisionReport(sender, region);
        AreaCollisionMessagePacker packer = new AreaCollisionMessagePacker();
        AreaCollisionMessage msg = (AreaCollisionMessage) packer.pack(report);

        assertEquals(sender, msg.getPlayerID());
        assertEquals(region, msg.getAreaName());
    }

}
