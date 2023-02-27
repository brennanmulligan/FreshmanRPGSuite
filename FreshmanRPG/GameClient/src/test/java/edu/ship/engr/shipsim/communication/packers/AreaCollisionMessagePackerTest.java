package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.AreaCollisionMessage;
import edu.ship.engr.shipsim.model.reports.AreaCollisionReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Dave
 * <p>
 * Make sure that the ChatMessagePacker behaves properly.
 */
@GameTest("GameClient")
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

        assertEquals(sender, msg.getRelevantPlayerID());
        assertEquals(region, msg.getAreaName());
    }

}
