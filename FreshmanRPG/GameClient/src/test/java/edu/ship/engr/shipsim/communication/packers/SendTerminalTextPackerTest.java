package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.SendTerminalTextMessage;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.reports.SendTerminalTextReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author bl5922 and np5756
 */
@GameTest("GameClient")
public class SendTerminalTextPackerTest
{

    /**
     * Tests the SendTerminalTextPacker by creating a report and making a message by packing the report
     */
    @Test
    public void testingOnlinePlayerPackerToHaveID()
    {
        int playerID = ClientPlayerManager.getSingleton().getThisClientsPlayer().getID();
        String terminalText = "who";
        SendTerminalTextReport cpr = new SendTerminalTextReport(playerID, terminalText);

        SendTerminalTextPacker packer = new SendTerminalTextPacker();
        SendTerminalTextMessage msg = (SendTerminalTextMessage) packer.pack(cpr);
        assertEquals(playerID, msg.getRelevantPlayerID());
        assertEquals(terminalText, msg.getTerminalText());


    }

}
