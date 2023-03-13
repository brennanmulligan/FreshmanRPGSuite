package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.TeleportationInitiationMessage;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.ChangeMapReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Matt
 * Make sure that the TeleportationInitiationMessagePacker behaves properly.
 */
@GameTest("GameClient")
public class TeleportationInitiationMessagePackerTest
{

    /**
     * Make sure that the report is properly translated into the message.
     */
    @Test
    public void testPacking()
    {
        int playerId = 7;
        String mapName = "Wally World";
        Position position = new Position(4, 8);

        ChangeMapReport report = new ChangeMapReport(playerId, position, mapName);
        TeleportationInitiationMessagePacker packer = new TeleportationInitiationMessagePacker();
        TeleportationInitiationMessage msg = (TeleportationInitiationMessage) packer.pack(report);

        assertEquals(playerId, msg.getRelevantPlayerID());
        assertEquals(mapName, msg.getMapName());
        assertEquals(position, msg.getPosition());
    }

}
